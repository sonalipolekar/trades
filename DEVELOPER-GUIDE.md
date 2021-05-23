# Trades Application

## How to start application
1. Through CLI
	run `gradlew bootRun`
2. Run `com.example.trades.Application' class as Java application

This will run with embedded tomcat server on http://localhost:8080/ 

## Application properties
- for mongo db connectivity
1. trades.repository.mongo.database-name=tradesdb
2. trades.repository.mongo.host=//localhost:27017/
3. trades.repository.mongo.enabled=true/false || by default MongoDB is used to store Trades Data. However, different repository can be used by extending `TradesRepository` class and disabling this property.

- for including error message to ResponseStatusException
server.error.include-message=always