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

### Lab 5: Add PathParam to GreetingResource
* Update `GreetingResourceTest` to add a new test for `GreetingResource` to use path parameter value.
* Update `GreetingResource` to pass the test.

### Lab 6: Use Response object
* Refactor `GreetingResource` to use `javax.ws.rs.core.Response` instead of `String`.
* Make sure the tests are still passing.

### Lab 7: Add HeaderParam to GreetingResource
* Update `GreetingResourceTest` tests to use header parameters, e.g. `X-NewCircle-Echo`
  * Assert response headers are properly handled, e.g. `X-NewCircle-Echo-Response`
* Update `GreetingResponse`
  * Inject `HeaderParam`, e.g. `X-NewCircle-Echo`
  * Echo back new header, e.g. `X-NewCircle-Echo-Response`
* Make sure the tests are passing.

### Lab 8: Replace @HeaderParam with HttpHeaders and @Context
* Refactor `GreetingResponse` to use `HttpHeaders` and `@Context` instead of `@HeaderParam`.
* Make sure the tests are passing.

### Lab 9: Support HTML MediaType Requests
* Update `GreetingResourceTest` to add a new test for `GreetingResource` to use HTML MediaType.
* Update `GreetingResource` to pass the test.

### Lab 10: Create UserResource
* Create `UserResourceTest` to test `UserResource` for:
  * Test the retrieving of a user as TEXT_PLAIN
* Create `UserResource` to pass the tests.

### Lab 11: Update UserResource to save and delete users
* Update `UserResourceTest` to test `UserResource` for:
 * Test the creating of a user
 * Test the deleting of a user
* Update `UserResource` to pass the tests.

### Lab 12: Add support for exception handling
* Update `UserResourceTest` to test `UserResource` for:
  * Test duplicate submission (duplicate users)
  * Test entity not found exception
* Update `UserResource` to handle `DuplicateEntityException` with try-catch.
* Update `UserResource` to handle `NoSuchEntityException` with try-catch.
* Make sure the tests are passing.

### Lab 13: Add exception mapper provider for exception handling
* Remove try-catch handling from `UserResource`.
* Add new package `chirp.service.providers` for additional providers we will add.
* Add `DuplicateEntityExceptionMapper` provider (`@Provider`) to handle `DuplicateEntityException` errors.
* Add `NoSuchEntityExceptionMapper` provider (`@Provider`) to handle `NoSuchEntityException` errors.
* Make sure the tests are passing.

### Lab 14: Add generic exception mappers
* Update `UserResourceTest` to test `UserResource` for:
  * Test generic exception, e.g. NullPointerException
  * Test various web application exceptions, e.g. `BadRequestException`, `NotAcceptableException`, `NotAllowedException`, `NotSupportedException`, `InteralServerErrorException`, etc.
* Add `WebApplicationExceptionMapper` provider (`@Provider`) to handle `WebApplicationException` errors.
* Add `DefaultExceptionMapper` provider (`@Provider`) to handle generic exceptions.
* Make sure the tests are passing.
