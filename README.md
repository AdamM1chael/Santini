# [Santini](https://google.com)

Santini is a Java & Spring based cryptocurrency trading bot that uses the public Binance API. It is run by providing it with API keys generated at binance.com (Also provide Santini with Twitter API keys if tweet alerts are desired). The bot then will pull recent trade candlestick data for the last 125 hours (5.2 days) and calculate a target price. If the target price is above the current price, a sell is executed and a buy back is placed. If not, Santini will wait 25 seconds before repeating the prediction/trading process. Above is a link to the current status of Santini running connected to my personal Binance account.

### Architecture

The bot itself is kicked off by a Spring Boot Application. The Spring App also exposes some endpoints that can interact and use the bot's functions. The available endpoints will be described in the API section. These endpoints will then be used for a web UI showing more informative feedback about the bot's operation and status.

### Endpoints

To get the current bot's status (main UI):

```$xslt
GET: http://host-ip:port/status
```

To get the current bot's BTC balance:

```$xslt
GET: http://host-ip:port/balance/btc
```

To get the current bot's profit (%):

```$xslt
GET: http://host-ip:port/balance/profit
```

To get the current bot's open orders:

```$xslt
GET: http://host-ip:port/orders
```

To shutdown the bot:

```$xslt
GET: http://host-ip:port/seppuku?pass={password}
```

### Install

Some example commands to run before attempting to build or run

```$xslt
# Install necessary commands/software
sudo apt update
sudo apt upgrade
sudo apt install apache2 default-jdk maven cloc

# Clone the necessary repos
git clone https://github.com/elseifn/Santini.git
git clone https://github.com/binance-exchange/binance-java-api.git

# Move the example start script to the root directory
# Make sure to edit the sh file to fill in password and api keys
cp Santini/src/main/resources/examples/bringYourselfBackOnline.sh bringYourselfBackOnline.sh
# Make the script executable
chmod +x bringYourselfBackOnline.sh

# Remove and replace index with example
# Make sure to edit the index.php file to point to the correct place, i.e. replace host and port number
sudo rm /var/www/html/index.*
sudo cp Santini/src/main/resources/examples/index.php /var/www/html/index.php

# Execute start script to rebuild Binance-API, Santini, and start the program
./bringYourselfBackOnline.sh
```

### Building

First clone and build the [Binance-API](https://github.com/binance-exchange/binance-java-api) repository to install the necessary packages into your local Maven repository (it is needed to build Santini)

To build Santini clone this repository and on the same level as pom.xml, execute

```$xslt
mvn clean install
```

### Executing

To run Santini, execute the following

```$xslt
java -jar target/Santini-{VERSION}.jar <arg1> <arg2> <arg3> <arg4> <arg5> <arg6>
```

- arg1 = Binance API Key\*
- arg2 = Binance Secret Key\*
- arg3 = Twitter OAuth Consumer Key
- arg4 = Twitter OAuth Consumer Secret
- arg5 = Twitter OAuth Access Token
- arg6 = Twitter OAuth Access Token Secret

#### Executing without Twitter

If you desire to not use the tweeting feature of the bot, simply pass in only the two Binance keys necessary to trade!

```$xslt
java -jar target/Santini-{VERSION}.jar <arg1> <arg2>
```

- arg1 = Binance API Key\*
- arg2 = Binance Secret Key\*

\*The Binance API Key absolutely MUST have approval to execute trades from Binance, but does not need approval to withdraw.
