set ECLIPSE_PATH=C:\svitools\oxygen\eclipse\eclipse.exe
set WORKSPACE_PATH=C:\LCOM_java\sp_workspace
set PROJECT_NAME=ultimo
set WAR_DESTINATION=C:\suipassi\ultimo\Docs
set WAR_FILENAME=C:\suipassi\ultimo\Docs\sp.war

REM ====== VERIFICA PREREQUISITI ======
echo ===================================
echo EXPORT WAR DA ECLIPSE (Metodo avanzato)
echo ===================================
echo.

REM Verifica se Eclipse esiste
if not exist "%ECLIPSE_PATH%" (
    echo ERRORE: Eclipse non trovato in %ECLIPSE_PATH%
    echo Modifica la variabile ECLIPSE_PATH nel file batch.
	PAUSE
    exit /b 1
)


REM ====== CREAZIONE DEL FILE DI CONFIGURAZIONE TEMPORANEO ======
set TEMP_DIR=%TEMP%\eclipse_export_%RANDOM%
set EXPORT_CONFIG=%TEMP_DIR%\export.xml

echo Creazione della configurazione di esportazione...
mkdir "%TEMP_DIR%"

echo ^<?xml version="1.0" encoding="UTF-8"?^> > "%EXPORT_CONFIG%"
echo ^<project name="ExportWAR" default="export"^> >> "%EXPORT_CONFIG%"
echo   ^<target name="export"^> >> "%EXPORT_CONFIG%"
echo     ^<eclipse.refreshLocal resource="%PROJECT_NAME%" depth="infinite"/^> >> "%EXPORT_CONFIG%"
echo     ^<eclipse.exportWAR webproject="%PROJECT_NAME%" >> "%EXPORT_CONFIG%"
echo                     destination="%WAR_DESTINATION%\%WAR_FILENAME%" >> "%EXPORT_CONFIG%"
echo                     overwrite="true" >> "%EXPORT_CONFIG%"
echo                     exportSource="false"/^> >> "%EXPORT_CONFIG%"
echo   ^</target^> >> "%EXPORT_CONFIG%"
echo ^</project^> >> "%EXPORT_CONFIG%"

REM ====== ESECUZIONE DI ECLIPSE HEADLESS ======
echo Esecuzione di Eclipse in modalità headless per esportare il WAR...
echo.

"%ECLIPSE_PATH%" -noSplash -data "%WORKSPACE_PATH%" -application org.eclipse.ant.core.antRunner -buildfile "%EXPORT_CONFIG%"

REM Verifica risultato
if exist "%WAR_DESTINATION%\%WAR_FILENAME%" (
    echo.
    echo WAR esportato con successo: %WAR_DESTINATION%\%WAR_FILENAME%
    echo.
) else (
    echo.
    echo ERRORE: Esportazione WAR fallita.
    echo.
    exit /b 1
)

REM Rimuovi la directory temporanea
rmdir /s /q "%TEMP_DIR%"

echo ===================================
echo Esportazione completata!
echo ===================================

endlocal