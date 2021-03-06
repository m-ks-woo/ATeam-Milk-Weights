.PHONY = make jar runjar test clean

# replace with path to your javac,java,jar,javafx installations
JC = C:\Program Files (x86)\Java\jdk1.8.0_231\bin\javac.exe
JAR = C:\Program Files (x86)\Java\jdk1.8.0_231\bin\jar.exe
JAVA = C:\Program Files (x86)\Java\jdk1.8.0_231\bin\javaw.exe
MP = --module-path C:\Program Files\Java\jdk-11.0.6\lib --add-modules javafx.controls,javafx.fxml #-Dfile.encoding=UTF-8 
CP = -classpath ".:application" 
APP = application.Main

#CLASSPATH = .:junit-platform-console-standalone-1.5.2.jar:json-simple-1.1.1.jar

make: 
	$(JC) $(MP) $(CP) -d . application/*.java

run:
	$(JAVA) $(MP) $(CP) application.Main

fx: 
	$(JC) $(MP) $(CP) -d . application/*.java

fxrun:
	$(JAVA) $(MP) $(CP) $(APP)

jar: 
	$(JAR) cvmf manifest.txt executable.jar .

runjar:
	java $(MP) -jar executable.jar

zip:
	zip team.zip application/* *

test: 
	javac $(MP) -cp $(CLASSPATH) *.java
	java -jar junit-platform-console-standalone-1.5.2.jar --class-path $(CLASSPATH) -p ""

clean:
	\rm application/*.class
	\rm executable.jar
