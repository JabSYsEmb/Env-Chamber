:: Script to build and execute the project with one command in CMD for windows running devices
:: in order to be able to run this script without throwing exceptions, maven 3.6+ version and 
:: JDK need be installed and added to paths.

set EXE=javaw.exe

FOR /F %%a IN ('tasklist /FI "IMAGENAME eq %EXE%"') DO IF %%a EQU %EXE% GOTO KILL

GOTO BUILD

:build

mvn clean package && .\modules\app\target\app-1.exe

:kill

Taskkill /IM %EXE% /F

GOTO BUILD
