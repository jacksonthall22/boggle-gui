@echo off
setlocal

:: Ensure PATH_TO_FX is set
if "%PATH_TO_FX%"=="" (
    echo Error: PATH_TO_FX is not set. Please set PATH_TO_FX to the JavaFX SDK path.
    exit /b 1
)

:: Compile the Java source files
set "compilingMsg=Compiling the project... "
<nul set /p="%compilingMsg%"
javac --module-path "%PATH_TO_FX%" --add-modules javafx.controls,javafx.fxml -d out src\*.java
if %ERRORLEVEL% NEQ 0 (
    echo failed.
    exit /b 1
)
echo done!

:: Run the application
echo Running the application...
java --module-path "%PATH_TO_FX%" --add-modules javafx.controls,javafx.fxml -cp out BoggleGUI

:: Exit message
if %ERRORLEVEL% EQU 0 (
    echo Program exited normally (user closed the GUI).
) else (
    echo Program exited with an error.
)
