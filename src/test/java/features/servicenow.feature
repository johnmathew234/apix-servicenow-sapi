Feature: Testing the API's

Scenario: Create Incident 
Given I create a new request with https://localhost:8095/api/ service
And I add the incidents endpoint to the service
And  I pass application/json as content type
And I pass 1234 as x-correlation-id
And I pass 1234 as client_id
And I pass 1234 as client_secret
And I send the values of src/test/resources/cucumberResources/createCucumberInput.json in the request body
And I send the POST request to the service
Then I get the 201 response code

Scenario: Get Incidents details
Given I create a new request with https://localhost:8095/api/ service
And I add the incidents/7ad14f681bd130106515a9f6624bcbde endpoint to the service
And I pass 1234 as x-correlation-id
And I pass 1234 as client_id
And I pass 1234 as client_secret
And I send the GET request to the service
Then I get the 200 response code


Scenario: Update Incidents details
Given I create a new request with https://localhost:8095/api/ service
And I add the incidents/2d5dd09c1b333010c9955283604bcb91 endpoint to the service
And  I pass application/json as content type
And I pass 1234 as x-correlation-id
And I pass 1234 as client_id
And I pass 1234 as client_secret
And I send the values of src/test/resources/cucumberResources/updateCucumberInput.json in the request body
And I send the PUT request to the service
Then I get the 200 response code

Scenario: Deleting Incident details
Given I create a new request with https://localhost:8095/api/ service
And I add the incidents/7ad14f681bd130106515a9f6624bcbde endpoint to the service
And I pass 1234 as x-correlation-id
And I pass 1234 as client_id
And I pass 1234 as client_secret
And I send the DELETE request to the service
Then I get the 200 response code

Scenario: Retrieve all Incident 
Given I create a new request with https://localhost:8095/api/ service
And I add the incidents/retrieveAll endpoint to the service
And  I pass application/json as content type
And I pass 1234 as x-correlation-id
And I pass 1234 as client_id
And I pass 1234 as client_secret
And I send the values of src/test/resources/cucumberResources/retrieveCucumberInput.json in the request body
And I send the POST request to the service
Then I get the 200 response code
