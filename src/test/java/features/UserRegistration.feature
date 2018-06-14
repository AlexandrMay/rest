Feature: UserRegistration

  Scenario Outline: Code generating
    Given Request is prepared and body contains <key> and <value>
    When I send the request with <resource>
    Then I have statusCode <statusCode>
    Examples:
    |key     |value      |resource                    |statusCode|
    |"phone"|"+380664853301"|/v1/office/registration.code|200|
    |  null     |"+380664853301"|/v1/office/registration.code|401|
