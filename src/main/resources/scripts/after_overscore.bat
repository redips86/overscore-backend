set SRC=C:\overscore_deploy
set DST=C:\overscore_real

FOR /f "tokens=1-2 delims=," %%a IN ("%DEPLOYMENT_GROUP_NAME%") DO (
   SET FIRST=%%a
   SET SECOND=%%b
)

echo %FIRST%
echo %SECOND%

mkdir %DST%\%FIRST%
mkdir %DST%\%FIRST%\lib

copy %SRC%\lib\* %DST%\%FIRST%\lib
copy %SRC%\%FIRST%.jar %DST%\%FIRST%