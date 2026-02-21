## Running tests
### Running all tests
* Run `mvn test` in the project directory to run all tests

### Running specific tests
* Run `mvn test -Dtest=TestClassName` to run a specific test class. Example: `mvn test -Dtest=TestAllFiles`


* Run `mvn test -Dtest=TestClassName#testMethodName` to run a specific test method. Example: `mvn test -Dtest=TestAllFiles#badTypeFilesShouldFail`


* Run `mvn test "-Dtest=TestSpecificFile" "-Dinput=filePath"` to run a specific test class with a specific input file.
  Example: `mvn test -Dtest=TestSpecificFile -Dinput=src/test/resources/syntax/good/good-1.ml`