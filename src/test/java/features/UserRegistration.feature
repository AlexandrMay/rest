Feature: UserRegistration

  Scenario Outline: Code generating
    Given Request is prepared and body contains <key> and <value>
    When I send the request with <resource>
    Then I have statusCode <statusCode> and <keyword> equals to <operand>
    Examples:
    |key     |value      |resource                    |statusCode|keyword|operand|
    |"phone"|"+380664853368"|/v1/office/registration.code|200|"success"  |true   |



    Scenario Outline: Registration confirm
      Given Request is prepared and body with <key> and <value> using <code> with generated code
      When I send the request with <resource>
      Then I expect statusCode <statusCode>
      Examples:
      |key|value|resource|statusCode|code|
      |"code"|"generatedCode"|/v1/office/registration.confirm|400|"code"|
