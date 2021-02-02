#!/bin/sh

# Kill the current running version of Santini
curl http://www.elseif.cn:17071/seppuku?pass={SECRET_HASH256}

# Remove old Santini logs
rm nohup.out
rm logs/*.log*

# Update the binance-java-api package
cd binance-java-api
git pull
mvn clean install
# mvn clean install -DskipTests
cd ..

# Update Santini's code and rebuild
cd santini
git pull
mvn clean install
cd ..

# Print out updated line count for Santini's code
cloc santini

# Start new Santini instance so even Control+C doesn't kill the running instance
nohup java -jar santini/target/santini*.jar <arg1> <..> <arg6> &

# USEFULL COMMANDS
# =======================================
# ps aux | grep -i santini   # This command finds a running instance of Santini
# sudo kill -9 {PID}       # Kills running Santini instance
