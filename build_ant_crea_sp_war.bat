
echo Building WAR using Ant...
cd /d %~dp0%

REM Run Ant build
C:\svitools\oxygen\eclipse\plugins\org.apache.ant_1.10.1.v20170504-0840\bin\ant war

IF EXIST "dist\*.war" (
    echo ✅ WAR built successfully!
) ELSE (
    echo ❌ Failed to build WAR.
)

 
 
pscp -pw Lagora_15 C:\suipassi\ultimo\dist\sp.war root@5.249.150.203:/opt/tomcat9/
plink -pw Lagora_15 root@5.249.150.203 "mv /opt/tomcat9/sp.war /opt/tomcat9/webapps/sp.war"