Feature: UserRegistration


  Scenario Outline: Code generating
    Given Request is prepared and body contains <key> and <value>
    When I send the request with <resource>
    Then I expect statusCode <statusCode>
    And And <keyword> equals to <operand>
    Examples:
    |key     |value      |resource                    |statusCode|keyword|operand|
    |"phone"|"+380664853303"|/v1/office/registration.code|200|"success"  |true   |


    Scenario Outline: Wrong code generating

      Given Request is prepared and body contains <key> and <value>
      When I send the new request with <resource>
      Then I expect statusCode <statusCode>
      And Response includes <code> is <receivedCode> and <message> is <receivedMessage>

      Examples:
        |key     |value      |resource                    |statusCode|code|receivedCode|message|receivedMessage|
        |"phone" |"+3806648533"|/v1/office/registration.code|400   |error.code|7     |error.message|Incorrect request body. Parameters: 'phone' are malformed or incorrect.|
        |"phone" |"+380664853398"|/v1/office/registration.code|409   |error.code|8     |error.message|Given phone already exists in the system.|
        |"phone" |""|/v1/office/registration.code|400   |error.code|6     |error.message|Incorrect request body. Parameters: 'phone' are required.|




  Scenario Outline: Registration confirm
      Given Request is prepared and body with <key> and <value> using <code> with generated code
      When I send the request with <resource>
      Then I expect statusCode <statusCode>
      Examples:
      |key|value|resource|statusCode|code|
      |"phone"|"+380664853303"|/v1/office/registration.confirm|200|"code"|


