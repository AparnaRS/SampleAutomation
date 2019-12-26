# Page Object model Test automation framework using Selenium with Java, TestNG and Maven-
This is a sample project to demonstrate automation for react shopping cart as part of assignment to be submitted to OpenTable

TestNG is used as test framework.

Dependency
Java
Maven

###libraries used
Selenium
TestNG
log4j
Extent Reports

### Steps to clone execute the tests

git clone https://github.com/AparnaRS/OpenTable.git

##Steps to execute tests


mvn test -Dbrowser=chrome     // to execute tests in chrome


mvn test =Dbrowser=firefox     // to execute tests in firefox


### The driver versions uploaded are for chromedriver 79 

Out of 11, 10 test cases and expected to pass and 1 testt case is expected to fail

Check report index.html in folder 'test-output'

Not sure if you will be able to generate allure-report, cause it needs allure-commandline to be installed on local machine, but you can view sample allure report in allure-report/index.html

To generate a new allure report, 
1. Install allure-commandline on local machine

2. allure generate

3. allure open