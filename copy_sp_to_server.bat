pscp -pw Lagora_15 C:\suipassi\ultimo\dist\sp.war root@5.249.150.203:/opt/tomcat9/

plink -pw Lagora_15 root@5.249.150.203 "mv /opt/tomcat9/sp.war /opt/tomcat9/webapps/sp.war"
pauseECHO FINE