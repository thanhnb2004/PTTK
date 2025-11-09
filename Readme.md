mvn jetty:run

cd /home/hung/Hung/src/src/PTTKHT

mvn jetty:stop
pkill -f "mvn jetty"

mvn clean package

#####

###Truy cap
http://localhost:8080/GdChinhKh
