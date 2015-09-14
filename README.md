Marakana RESTful Java
=====================

* To compile: `mvn compile`
* To run tests: `mvn test`
* To run the server: `mvn exec:exec`

## Branch for class 4106

### Lab 1: Run example
* Run the server, e.g. `mvn exec:exec`
* Test the `HelloResource`, e.g. http://localhost:8080/hello using your favourite REST client (Postman, CURL, etc.)

### Lab 2: Add Root resource
* Add `RootResourceTest` to test the `RootResource`:
  * Assert response has `200` status code
  * Assert that response contains OK message, e.g `As of MM-dd-yyyy hh:mm:ss, everything is OK.`
* Add `RootResource` to pass the test.

### Lab 3: Add GreetingResource with QueryParam
* Add `GreetingsResourceTest` to test the `GreetingResource`:
  * Test the greeting resource with a name passed in
  * Test the greeting resource with no name passed in
  * Assert proper status codes
  * Assert proper MIME types (MediaType.TEXT_PLAIN for now)
* Add `GreetingsResource` to pass the test.

### Lab 4: Add DefaultValue to GreetingResource
* Update `GreetingResourceTest` to include default value for the name, e.g. `stranger`
* Update `GreetingResource` to pass the test.

