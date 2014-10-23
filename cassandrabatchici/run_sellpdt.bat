set JAVA_HOME=C:\jdk1.7.0_03
set PATH=%JAVA_HOME%\bin;%PATH%

call mvn clean install
call mvn exec:java -Dexec.mainClass=org.springframework.batch.core.launch.support.CommandLineJobRunner -Dexec.args="launch-context.xml kioskjob"
call mvn exec:java -Dexec.mainClass=org.springframework.batch.core.launch.support.CommandLineJobRunner -Dexec.args="launch-context.xml kioskcsvsellproductjob"

