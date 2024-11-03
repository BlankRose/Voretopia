@echo off
setlocal EnableDelayedExpansion

:: Checks if deployment of current build is requested
set OR=0
if "%~1"=="deploy" set OR=1
if "%~1"=="d" set OR=1
if "%OR%"=="1" (
    cd %~dp0\..

    :: Copy the newest version of the plugin to the test folder
    echo Insert latest build version...
    del "test\plugins\voretopia-?.?.?.jar"
    copy "build\voretopia-?.?.?.jar" "test\plugins\"

    :: Insert or update this plugins' extension packs
    set src=datapacks
    set dest=test\world\datapacks
    for /D %%d in ("!src!\*") do (
        echo Inserting %%~nd extension pack...
        if not exist "!dest!\%%~nd" (
            mkdir "!dest!\%%~nd"
        )
        xcopy /s /d "%%d\*" "!dest!\%%~nd\" > nul
    )
)

:: Start the test server
cd %~dp0
java -DIReallyKnowWhatIAmDoingISwear -Xmx1024M -Xms1024M -jar spigot-1.21.3.jar nogui