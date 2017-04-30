@echo off

set SOURCE_FOLDER=.

set CS_COMPILER_PATH=..\..\CLIENT\tools\protobuf-net+r668\ProtoGen\protogen.exe

set CS_TARGET_PATH=..\..\CLIENT\unity_proj\GAME2017\Assets\Scripts\Network\pb

del %CS_TARGET_PATH%\*.* /f /s /q

for /f "delims=" %%i in ('dir /b "%SOURCE_FOLDER%\*.proto"') do (

     echo %CS_COMPILER_PATH% -i:%SOURCE_FOLDER%\%%i -o:%CS_TARGET_PATH%\%%~ni.cs
     %CS_COMPILER_PATH% -i:%SOURCE_FOLDER%\%%i -o:%CS_TARGET_PATH%\%%~ni.cs

)

pause