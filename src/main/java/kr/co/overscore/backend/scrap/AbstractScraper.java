package kr.co.overscore.backend.scrap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.co.overscore.backend.model.ApiCode;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public abstract class AbstractScraper {
	protected static final int SCRAP_COUNT = 10;
	
	protected Connection connection;
	public ApiCode apiCode;
	
	public AbstractScraper(){
		
	}
	
	public AbstractScraper(ApiCode apiCode){
		super();
		this.apiCode = apiCode;	
	}
	
	// Header Generator
	@SuppressWarnings("serial")
	private static List<String> browsers = new ArrayList<String>(){
		{
			add("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31");
			add("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:20.0) Gecko/20100101 Firefox/20.0");
			add("Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)");
		}
	};
	
	public Document scrap(String url) throws IOException {
		return scrap(url, true);
	}
		

	public Document scrap(String url, boolean followRedirects)
			throws IOException {
		return scrap(url, followRedirects, "UTF-8");
	}
	
	public Document scrapWithParams(String url, boolean followRedirects, Map<String,String> params, int maxBodySize) throws IOException {
		this.connection = Jsoup
				.connect(url)
				.method(Method.GET)
				.data(params)				
				.header("Accept", "text/html, */*; q=0.01")
				.header("Accept-Charset", "windows-949,utf-8;q=0.7,*;q=0.3")
				.header("Accept-Encoding", "gzip, deflate")
				.header("Accept-Language", "ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4")
				.userAgent(browsers.get((int) (Math.random() * browsers.size())))				
				.ignoreContentType(true).timeout(100000)
				.followRedirects(followRedirects).ignoreHttpErrors(true);
		
		if(maxBodySize >= 0) this.connection.maxBodySize(maxBodySize);
		
		return connection.post();
		
	}
	

	/**
	 * bodydsize = -1
	 * @param url
	 * @param followRedirects
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	public Document scrap(String url, boolean followRedirects, String encoding) throws IOException {
		return this.scrap(url, followRedirects, encoding, 0);
	}
	
	public Document scrap(String url, boolean followRedirects, String encoding, int maxBodySize)
			throws IOException {
		this.connection = Jsoup
				.connect(url)
				.header("Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
				.header("Accept-Charset", "windows-949,utf-8;q=0.7,*;q=0.3")
				.header("Accept-Encoding", "gzip,deflate,sdch")
				.header("Accept-Language",
						"ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4")
				.userAgent(browsers.get((int) (Math.random() * browsers.size())))
				.ignoreContentType(true).timeout(50000)
				.followRedirects(followRedirects).ignoreHttpErrors(true);
		
		if(maxBodySize >= 0) this.connection.maxBodySize(maxBodySize);
		
		Response response = connection.execute();
		
		return Jsoup.parse(new String(response.bodyAsBytes(), encoding));
	}
	
	// �ӽ�.
	public Document scrapWithReferrer(String url, boolean followRedirects, String encoding, int maxBodySize, String referrer) throws IOException {
		this.connection = Jsoup
				.connect(url)
				.header("Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
				.header("Accept-Charset", "windows-949,utf-8;q=0.7,*;q=0.3")
				.header("Accept-Encoding", "gzip,deflate,sdch")
				.header("Accept-Language",
						"ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4")
				.userAgent(browsers.get((int) (Math.random() * browsers.size())))
				.referrer(referrer)
				.ignoreContentType(true).timeout(10000)
				.followRedirects(followRedirects).ignoreHttpErrors(true);
		
		if(maxBodySize >= 0) this.connection.maxBodySize(maxBodySize);
		
		return Jsoup.parse(new String(connection.execute().bodyAsBytes(),
				encoding));
	}

	public String scrapJson(String url) throws IOException {
			return scrapJson(url, null);
	}
	
	public String scrapJsonRefTest(String url, String ref) throws IOException {
		return scrapJson(url, ref);
}
	
	public String scrapJson(String url, String ref) throws IOException {
		
		String returnJson = "";
		URL urlConn = new URL(url);  
		
		URLConnection urlConnection = urlConn.openConnection();
		urlConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
		if(ref != null)
			urlConnection.addRequestProperty("Referer", ref);
		
		urlConnection.setConnectTimeout(10000);
		urlConnection.setReadTimeout(10000);

		BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));  
					
		String inputLine;  
		  
		while ((inputLine = in.readLine()) != null) {   
			returnJson = inputLine;  				  
		}  
		in.close();  
		
		return returnJson;
}
	
	public Connection getConnection() {
		return this.connection;
	}
}