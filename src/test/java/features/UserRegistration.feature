Feature: UserRegistration

  Scenario: registration.code
    Given Сформирован запрос с корректным номером телефона
    When Запрос отправлен на ресурс registration.code
    Then Получен статус-код 200
    And Ответ содержит такие данные
    |success|true|


  Scenario Outline: registration.code ошибки
    Given Запрос сформирован, в теле передан <телефон> и <номер>
    When Я отправляю запрос на  <ресурс>
    Then Получен такой статус-код <статус-код>
    And Ответ содержит <ключ ошибки> и <код ошибки>
    And А также <ключ сообщения> и <текст сообщения>
    Examples:
      |телефон     |номер      |ресурс                    |статус-код|ключ ошибки|код ошибки|ключ сообщения|текст сообщения|
      |"phone" |"+3806648533"|/v1/office/registration.code|400   |error.code   |7         |error.message |Incorrect request body. Parameters: 'phone' are malformed or incorrect.|
      |"phone" |"+380664853398"|/v1/office/registration.code|409   |error.code |8         |error.message |Given phone already exists in the system.                              |
      |"phone" |""|/v1/office/registration.code|400   |error.code              |6         |error.message |Incorrect request body. Parameters: 'phone' are required.              |
      |"phon"  |"+380664853399"|/v1/office/registration.code|400|error.code    |5         |error.message |Incorrect request body. Parameters: 'phon' are unknown.                |
      |"phone" |"+380664853399"|/v1/office/registration.cod |404|code    |100       |message |Requested resource were not found at given endpoint.                   |
      |"phone" |"380664853399" |/v1/office/registration.code|400|error.code    |7         |error.message |Incorrect request body. Parameters: 'phone' are malformed or incorrect.|
      |"phone"|"+38066485339945679"|/v1/office/registration.code|400|error.code    |7         |error.message |Incorrect request body. Parameters: 'phone' are malformed or incorrect.|

  Scenario: registration.confirm
    Given Формирую запрос с корректным номером телефона и кодом подтверждения
    When Запрос отправлен на ресурс registration.confirm
    Then Получен статус-код 200
    And Ответ содержит handle

