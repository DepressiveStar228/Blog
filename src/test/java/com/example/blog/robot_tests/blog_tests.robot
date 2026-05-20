*** Settings ***
Documentation     Набір тестів для блогу з використанням Keyword-Driven підходу
Library           SeleniumLibrary
Suite Teardown    Close All Browsers

*** Variables ***
${URL}            http://localhost:8080/posts
${BROWSER}        Chrome
${VALID_USER}     artemka.n.arambler.ru@gmail.com
${VALID_PASS}     111111

*** Test Cases ***
Register New User
    [Documentation]    Реєстрація користувача у чистій базі даних
    Open Blog Page
    Click Link    Реєстрація
    Input Text    id=username    Robot
    Input Text    id=email       ${VALID_USER}
    Input Text    id=password    ${VALID_PASS}
    Click Button  css=button[type='submit']
    Page Should Contain    Увійдіть в аккаунт

Successful User Login
    [Documentation]    Перевірка успішного входу користувача в систему
    Open Blog Page
    Go To Login Page
    Enter Credentials    ${VALID_USER}    ${VALID_PASS}
    Submit Login Form
    Verify Successful Login

Create New Post
    [Documentation]    Перевірка створення нового поста після авторизації
    Click Link    + Новий пост
    Input Text    id=title    Тестовий Заголовок Robot
    Input Text    id=content  Тестовий текст поста від Robot Framework
    Click Button  css=button[type='submit']
    Page Should Contain    Пост створено!

*** Keywords ***
Open Blog Page
    Open Browser    ${URL}    ${BROWSER}
    Maximize Browser Window

Go To Login Page
    Click Link    Увійти

Enter Credentials
    [Arguments]    ${username}    ${password}
    Input Text     name=username    ${username}
    Input Text     name=password    ${password}

Submit Login Form
    Click Button   css=button[type='submit']

Verify Successful Login
    Page Should Contain    Вийти