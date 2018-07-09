Feature: UserRegistration

@NeedTo
  Scenario: registration.code
    Given Сформирован запрос с корректным номером телефона
    When Запрос отправлен на ресурс registration.code
    Then Получен статус-код 200
    And Ответ содержит такие данные
    |success|true|


  Scenario Outline: registration.code ошибки
    Given Request is prepared and body contains <key> and <value>
    When I send the new request with <resource>
    Then I expect statusCode <statusCode>
    And Ответ содержит <ключ ошибки> и <код ошибки>
    And А также <ключ сообщения> и <текст сообщения>
    Examples:
      |key     |value      |resource                    |statusCode|ключ ошибки|код ошибки|ключ сообщения|текст сообщения|
      |"phone" |"+3806648533"|/v1/office/registration.code|400   |error.code   |7         |error.message |Incorrect request body. Parameters: 'phone' are malformed or incorrect.|
      |"phone" |"+380664853398"|/v1/office/registration.code|409   |error.code |8         |error.message |Given phone already exists in the system.                              |
      |"phone" |""|/v1/office/registration.code|400   |error.code              |6         |error.message |Incorrect request body. Parameters: 'phone' are required.              |


  Scenario: registration.confirm
    Given Формирую запрос с корректным номером телефона и кодом подтверждения
    When Запрос отправлен на ресурс registration.confirm
    Then Получен статус-код 200
    And Ответ содержит handle

