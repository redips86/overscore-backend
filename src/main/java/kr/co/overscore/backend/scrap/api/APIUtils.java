package kr.co.overscore.backend.scrap.api;

import java.util.HashMap;
import java.util.Map;

public class APIUtils {
	public static Map<String,String> PRESTIGE;
	private static void setPRESTIGE(){
		if(PRESTIGE != null) return;
		PRESTIGE = new HashMap<String,String>();
		// bronze 0-5
		PRESTIGE.put("0x0250000000000918","0");
		PRESTIGE.put("0x0250000000000919","0");
		PRESTIGE.put("0x025000000000091A","0");
		PRESTIGE.put("0x025000000000091B","0");
		PRESTIGE.put("0x025000000000091C","0");
		PRESTIGE.put("0x025000000000091D","0");
		PRESTIGE.put("0x025000000000091E","0");
		PRESTIGE.put("0x025000000000091F","0");
		PRESTIGE.put("0x0250000000000920","0");
		PRESTIGE.put("0x0250000000000921","0");
		PRESTIGE.put("0x0250000000000922","1");
		PRESTIGE.put("0x0250000000000924","1");
		PRESTIGE.put("0x0250000000000925","1");
		PRESTIGE.put("0x0250000000000926","1");
		PRESTIGE.put("0x025000000000094C","1");
		PRESTIGE.put("0x0250000000000927","1");
		PRESTIGE.put("0x0250000000000928","1");
		PRESTIGE.put("0x0250000000000929","1");
		PRESTIGE.put("0x025000000000092B","1");
		PRESTIGE.put("0x0250000000000950","1");
		PRESTIGE.put("0x025000000000092A","2");
		PRESTIGE.put("0x025000000000092C","2");
		PRESTIGE.put("0x0250000000000937","2");
		PRESTIGE.put("0x025000000000093B","2");
		PRESTIGE.put("0x0250000000000933","2");
		PRESTIGE.put("0x0250000000000923","2");
		PRESTIGE.put("0x0250000000000944","2");
		PRESTIGE.put("0x0250000000000948","2");
		PRESTIGE.put("0x025000000000093F","2");
		PRESTIGE.put("0x0250000000000951","2");
		PRESTIGE.put("0x025000000000092D","3");
		PRESTIGE.put("0x0250000000000930","3");
		PRESTIGE.put("0x0250000000000934","3");
		PRESTIGE.put("0x0250000000000938","3");
		PRESTIGE.put("0x0250000000000940","3");
		PRESTIGE.put("0x0250000000000949","3");
		PRESTIGE.put("0x0250000000000952","3");
		PRESTIGE.put("0x025000000000094D","3");
		PRESTIGE.put("0x0250000000000945","3");
		PRESTIGE.put("0x025000000000093C","3");
		PRESTIGE.put("0x025000000000092E","4");
		PRESTIGE.put("0x0250000000000931","4");
		PRESTIGE.put("0x0250000000000935","4");
		PRESTIGE.put("0x025000000000093D","4");
		PRESTIGE.put("0x0250000000000946","4");
		PRESTIGE.put("0x025000000000094A","4");
		PRESTIGE.put("0x0250000000000953","4");
		PRESTIGE.put("0x025000000000094E","4");
		PRESTIGE.put("0x0250000000000939","4");
		PRESTIGE.put("0x0250000000000941","4");
		PRESTIGE.put("0x025000000000092F","5");
		PRESTIGE.put("0x0250000000000932","5");
		PRESTIGE.put("0x025000000000093E","5");
		PRESTIGE.put("0x0250000000000936","5");
		PRESTIGE.put("0x025000000000093A","5");
		PRESTIGE.put("0x0250000000000942","5");
		PRESTIGE.put("0x0250000000000947","5");
		PRESTIGE.put("0x025000000000094F","5");
		PRESTIGE.put("0x025000000000094B","5");
		PRESTIGE.put("0x0250000000000954","5");
		// Silver 6 - 11
		PRESTIGE.put("0x0250000000000956","6");
		PRESTIGE.put("0x025000000000095C","6");
		PRESTIGE.put("0x025000000000095D","6");
		PRESTIGE.put("0x025000000000095E","6");
		PRESTIGE.put("0x025000000000095F","6");
		PRESTIGE.put("0x0250000000000960","6");
		PRESTIGE.put("0x0250000000000961","6");
		PRESTIGE.put("0x0250000000000962","6");
		PRESTIGE.put("0x0250000000000963","6");
		PRESTIGE.put("0x0250000000000964","6");
		PRESTIGE.put("0x0250000000000957","7");
		PRESTIGE.put("0x0250000000000965","7");
		PRESTIGE.put("0x0250000000000966","7");
		PRESTIGE.put("0x0250000000000967","7");
		PRESTIGE.put("0x0250000000000968","7");
		PRESTIGE.put("0x0250000000000969","7");
		PRESTIGE.put("0x025000000000096A","7");
		PRESTIGE.put("0x025000000000096B","7");
		PRESTIGE.put("0x025000000000096C","7");
		PRESTIGE.put("0x025000000000096D","7");
		PRESTIGE.put("0x0250000000000958","8");
		PRESTIGE.put("0x025000000000096E","8");
		PRESTIGE.put("0x025000000000096F","8");
		PRESTIGE.put("0x0250000000000970","8");
		PRESTIGE.put("0x0250000000000971","8");
		PRESTIGE.put("0x0250000000000972","8");
		PRESTIGE.put("0x0250000000000973","8");
		PRESTIGE.put("0x0250000000000974","8");
		PRESTIGE.put("0x0250000000000975","8");
		PRESTIGE.put("0x0250000000000976","8");
		PRESTIGE.put("0x0250000000000959","9");
		PRESTIGE.put("0x0250000000000977","9");
		PRESTIGE.put("0x0250000000000978","9");
		PRESTIGE.put("0x0250000000000979","9");
		PRESTIGE.put("0x025000000000097A","9");
		PRESTIGE.put("0x025000000000097B","9");
		PRESTIGE.put("0x025000000000097C","9");
		PRESTIGE.put("0x025000000000097D","9");
		PRESTIGE.put("0x025000000000097E","9");
		PRESTIGE.put("0x025000000000097F","9");
		PRESTIGE.put("0x025000000000095A","10");
		PRESTIGE.put("0x0250000000000980","10");
		PRESTIGE.put("0x0250000000000981","10");
		PRESTIGE.put("0x0250000000000982","10");
		PRESTIGE.put("0x0250000000000983","10");
		PRESTIGE.put("0x0250000000000984","10");
		PRESTIGE.put("0x0250000000000985","10");
		PRESTIGE.put("0x0250000000000986","10");
		PRESTIGE.put("0x0250000000000987","10");
		PRESTIGE.put("0x0250000000000988","10");
		PRESTIGE.put("0x025000000000095B","11");
		PRESTIGE.put("0x0250000000000989","11");
		PRESTIGE.put("0x025000000000098A","11");
		PRESTIGE.put("0x025000000000098B","11");
		PRESTIGE.put("0x025000000000098C","11");
		PRESTIGE.put("0x025000000000098D","11");
		PRESTIGE.put("0x025000000000098E","11");
		PRESTIGE.put("0x025000000000098F","11");
		PRESTIGE.put("0x0250000000000991","11");
		PRESTIGE.put("0x0250000000000990","11");
		// Gold 12 - 17
		PRESTIGE.put("0x0250000000000992","12");
		PRESTIGE.put("0x0250000000000993","12");
		PRESTIGE.put("0x0250000000000994","12");
		PRESTIGE.put("0x0250000000000995","12");
		PRESTIGE.put("0x0250000000000996","12");
		PRESTIGE.put("0x0250000000000997","12");
		PRESTIGE.put("0x0250000000000998","12");
		PRESTIGE.put("0x0250000000000999","12");
		PRESTIGE.put("0x025000000000099A","12");
		PRESTIGE.put("0x025000000000099B","12");
		PRESTIGE.put("0x025000000000099C","13");
		PRESTIGE.put("0x025000000000099D","13");
		PRESTIGE.put("0x025000000000099E","13");
		PRESTIGE.put("0x025000000000099F","13");
		PRESTIGE.put("0x02500000000009A0","13");
		PRESTIGE.put("0x02500000000009A1","13");
		PRESTIGE.put("0x02500000000009A2","13");
		PRESTIGE.put("0x02500000000009A3","13");
		PRESTIGE.put("0x02500000000009A4","13");
		PRESTIGE.put("0x02500000000009A5","13");
		PRESTIGE.put("0x02500000000009A6","14");
		PRESTIGE.put("0x02500000000009A7","14");
		PRESTIGE.put("0x02500000000009A8","14");
		PRESTIGE.put("0x02500000000009A9","14");
		PRESTIGE.put("0x02500000000009AA","14");
		PRESTIGE.put("0x02500000000009AB","14");
		PRESTIGE.put("0x02500000000009AC","14");
		PRESTIGE.put("0x02500000000009AD","14");
		PRESTIGE.put("0x02500000000009AE","14");
		PRESTIGE.put("0x02500000000009AF","14");
		PRESTIGE.put("0x02500000000009B0","15");
		PRESTIGE.put("0x02500000000009B1","15");
		PRESTIGE.put("0x02500000000009B2","15");
		PRESTIGE.put("0x02500000000009B3","15");
		PRESTIGE.put("0x02500000000009B4","15");
		PRESTIGE.put("0x02500000000009B5","15");
		PRESTIGE.put("0x02500000000009B6","15");
		PRESTIGE.put("0x02500000000009B7","15");
		PRESTIGE.put("0x02500000000009B8","15");
		PRESTIGE.put("0x02500000000009B9","15");
		PRESTIGE.put("0x02500000000009BA","16");
		PRESTIGE.put("0x02500000000009BB","16");
		PRESTIGE.put("0x02500000000009BC","16");
		PRESTIGE.put("0x02500000000009BD","16");
		PRESTIGE.put("0x02500000000009BE","16");
		PRESTIGE.put("0x02500000000009BF","16");
		PRESTIGE.put("0x02500000000009C0","16");
		PRESTIGE.put("0x02500000000009C1","16");
		PRESTIGE.put("0x02500000000009C2","16");
		PRESTIGE.put("0x02500000000009C3","16");
		PRESTIGE.put("0x02500000000009C4","17");
		PRESTIGE.put("0x02500000000009C5","17");
		PRESTIGE.put("0x02500000000009C6","17");
		PRESTIGE.put("0x02500000000009C7","17");
		PRESTIGE.put("0x02500000000009C8","17");
		PRESTIGE.put("0x02500000000009C9","17");
		PRESTIGE.put("0x02500000000009CA","17");
		PRESTIGE.put("0x02500000000009CB","17");
		PRESTIGE.put("0x02500000000009CC","17");
		PRESTIGE.put("0x02500000000009CD","17");
	}
	
	public static String getPRESTIGE(String key){
		if(PRESTIGE == null){
			APIUtils.setPRESTIGE();
		}
		return PRESTIGE.get(key);
	}
	
	public static Map<String,String> TIER;
	private static void setTIER(){
		if(TIER != null) return;
		
		TIER = new HashMap<String,String>();
		TIER.put("rank-1.png", "bronze");
		TIER.put("rank-2.png", "silver");
		TIER.put("rank-3.png", "gold");
		TIER.put("rank-4.png", "platinum");
		TIER.put("rank-5.png", "diamond");
		TIER.put("rank-6.png", "master");
		TIER.put("rank-7.png", "grandmaster");
	}
	
	public static String getTIER(String key){
		if(TIER == null){
			APIUtils.setTIER();
		}
		String tier = TIER.get(key);
		if(tier == null)
			return "unranked";
		else
			return tier;
	}
	
	public static Map<String,String> DATA_CATEGORY;
	private static void setDATA_CATEGORY(){
		if(DATA_CATEGORY != null) return;
		
		DATA_CATEGORY = new HashMap<String,String>();
		DATA_CATEGORY.put("Time Played","overwatch.guid.0x0860000000000021");
		DATA_CATEGORY.put("Games Won","overwatch.guid.0x0860000000000039");
		DATA_CATEGORY.put("Win Percentage","overwatch.guid.0x08600000000003D1");
		DATA_CATEGORY.put("Weapon Accuracy","overwatch.guid.0x086000000000002F");
		DATA_CATEGORY.put("Eliminations per Life","overwatch.guid.0x08600000000003D2");
		DATA_CATEGORY.put("Multikill - Best","overwatch.guid.0x0860000000000346");
		DATA_CATEGORY.put("Objective Kills - Average","overwatch.guid.0x086000000000039C");
	}
	
	public static String getDATA_CATEGORY(String key){
		if(DATA_CATEGORY == null){
			APIUtils.setDATA_CATEGORY();
		}
		return DATA_CATEGORY.get(key);
	}
	
	public static Map<String,String> HEROES;
	private static void setHEROES(){
		if(HEROES != null) return;
		
		HEROES = new HashMap<String,String>();
		HEROES.put("0x02E00000FFFFFFFF", "ALL HEROES");
		HEROES.put("0x02E0000000000002","reaper");
		HEROES.put("0x02E0000000000003","tracer");
		HEROES.put("0x02E0000000000004","mercy");
		HEROES.put("0x02E0000000000005","hanzo");
		HEROES.put("0x02E0000000000006","torbjorn");
		HEROES.put("0x02E0000000000007","reinhardt");
		HEROES.put("0x02E0000000000008","pharah");
		HEROES.put("0x02E0000000000009","winston");
		HEROES.put("0x02E000000000000A","widowmaker");
		HEROES.put("0x02E0000000000015","bastion");
		HEROES.put("0x02E0000000000016","symmetra");
		HEROES.put("0x02E0000000000020","zenyatta");
		HEROES.put("0x02E0000000000029","genji");
		HEROES.put("0x02E0000000000040","roadhog");
		HEROES.put("0x02E0000000000042","mccree");
		HEROES.put("0x02E0000000000065","junkrat");
		HEROES.put("0x02E0000000000068","zarya");
		HEROES.put("0x02E000000000006E","soldier76");
		HEROES.put("0x02E0000000000079","lucio");
		HEROES.put("0x02E000000000007A","dva");
		HEROES.put("0x02E00000000000DD","mei");
		HEROES.put("0x02E000000000013B","ana");
		HEROES.put("0x02E000000000012E","sombra");
		HEROES.put("0x02E000000000013E","orisa");
		HEROES.put("0x02E000000000012F","doomfist");
		HEROES.put("0x02E00000000001A2","moira");
		HEROES.put("0x02E0000000000195","brigitte");
	}
	public static String getHEROES(String key){
		if(HEROES == null){
			APIUtils.setHEROES();
		}
		return HEROES.get(key);
	}
	
	public static String sanitize(String key){
		key = key.toLowerCase();
		key = key.replaceAll("-", "_");
		key = key.replaceAll(" ", "_");
		key = key.replaceAll("___", "_");
		key = key.replaceAll("__", "_");
		key = key.replace("soldier_76", "soldier76");
		key = key.replace("soldier:_76", "soldier76");
		key = key.replace("lúcio", "lucio");
		key = key.replace("d.va", "dva");
		key = key.replace("torbjörn","torbjorn");
		return key;
	}
	
	public static String tryExtract(String value){
		value = value.toLowerCase().replaceAll(",", "");
		if(value.contains("hour"))
			return value.replace("hours", "").replace("hour", "");
		else if(value.contains("minute"))
			return String.valueOf(Float.parseFloat(value.replace("minutes", "").replaceAll("minute", "")) / 60.f);
		else if(value.contains("second"))
			return String.valueOf(Float.parseFloat(value.replace("seconds", "").replace("second", "")) / 60.f / 60.f);
		else if(value.contains("%"))
			return String.valueOf(Float.parseFloat(value.replace("%", "")) / 100.f);
		else if(value.contains(":")){
			String[] values = value.split(":");
			if(values.length == 2){
				Float minute = Float.parseFloat(values[0]);
				Float second = Float.parseFloat(values[1]);
				second = second / 60 / 60;
				minute = minute / 60;
				return String.valueOf(minute + second);
			}else if(values.length == 3){
				Float hour = Float.parseFloat(values[0]);
				Float minute = Float.parseFloat(values[1]);
				Float second = Float.parseFloat(values[2]);
				second = second / 60 / 60;
				minute = minute / 60;
				return String.valueOf(hour + minute + second);
			}
		}else if(value.equals("--"))
			return "0";
		
		
		
		return value;
	}
}
