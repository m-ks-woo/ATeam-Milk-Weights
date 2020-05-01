REM OS:     Windows 10
REM IDE:    Eclipse 2019-06
REM JDK:    C:\Program Files\Java\jdk-11.0.6\bin
REM JRE:    C:\Program Files\Java\jre1.8.0_241\bin
REM JavaFX: C:\Users\Matt\Documents\eclipse-java\javafx-sdk-11.0.2\lib

REM My project directory structure
REM C:\USERS\Matt\Documents\GitHub\ATeam-Milk-Weights
REM .git
REM .settings
REM application\Main.java
REM application\Entry.java
REM application\Farm.java
REM application\FarmTable.java
REM Makefile
REM manifest.txt
REM csv
REM build.bat
REM stylesheets.css
REM screenshot1.png
REM screenshot2.png


REM My command line run command as copied from Eclipse
REM "C:\Program Files\AdoptOpenJDK\jre-11.0.6.10-hotspot\bin\javaw.exe" --module-path "C:\Program Files (x86)\Java FX\javafx-sdk-11.0.2\lib" --add-modules javafx.controls,javafx.fxml -Dfile.encoding=UTF-8 -classpath "C:\Users\deppe\workspace-eclipse\FX_DEMO_001" application.Main

REM build the executable jar, manifest.txt is required
jar cvmf manifest.txt executable.jar application 

REM use jar tool to create a zip file that does not include .git and project settings
REM Note 1: you will have to add other file types if they exist and are required for your project (WE WANT ALL FILES HERE)
REM Note 2: capitalization matters
REM CAUTION: all files must be explicitly included in this form (and we need all but the .git and project settings folders)
jar cvf fx.zip Makefile application *.css *.bat *.jar *.txt *.jpg *.png *.class *.java

REM include a command to run the jar file from the command line (just to see that it works!)
REM TODO: edit path so that it has the correct path for your javafx library
java --module-path "C:\Program Files (x86)\Java FX\javafx-sdk-11.0.2\lib" --add-modules javafx.controls,javafx.fxml -jar executable.jar

