:: Script to build and execute the project with one command in CMD for windows running devices
:: in order to be able to run this script without throwing exceptions, maven 3.6+ version and 
:: JDK need be installed and added to paths.

tasklist /FI "IMAGENAME eq javaw.exe" /FO CSV > %temp%\search.log

FOR /F %%A IN (%temp%\search.log) DO IF %%~zA EQU 0 GOTO end

Taskkill /IM javaw.exe /F

:end

del %temp%\search.log

mvn clean package && .\modules\app\target\app-1.exe
