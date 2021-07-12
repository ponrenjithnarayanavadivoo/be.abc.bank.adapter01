# Adapter Service
  SPRING MVC Adapter Service provides REST call to [Account Service](https://github.com/ponrenjithnarayanavadivoo/bank-accounts.service01) and [Transaction Service](https://github.com/ponrenjithnarayanavadivoo/bank-transactions.service01). 
 
 SPRING MVC Adapter Service has two parts: _createAccount_ to create accounts for ABC Bank customers and _viewCustomerInfo_ to see the detais of the transaction and account.
  
### Prerequisites
You need the following installed and available in your $PATH:

* Java 8
* Apache maven 3.0.4 or greater

#### REST API's 

[Account-Service](https://github.com/ponrenjithnarayanavadivoo/be.abc.bank.accounts-api)

[Transaction-Service](https://github.com/ponrenjithnarayanavadivoo/be.abc.bank.transaction-api)

####  Maven builds:
```
mvn clean install
```

#### Execution
```
java -jar  adapter-0.0.1.jar
```

#### To check the application deployed successfully on your local machine 
http://localhost:9080/

###  Configuration

The api is centralized on GitHub public repository and the settings.xml in the root folder needs to be configured on your local machine.


