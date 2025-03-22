📖 Library Management System API Documentation
Base URL
http://localhost:8080/api
1-Add new User 
Path : /user/add
Request Body:
"domain" : {
"arabicName":"amara",
"englishName":"amara",
"username":"amarashujaa",
"password":"123456"
}
Response:
-200 Created: User added successfully.
-400 Bad Request: Invalid input data.
------------------------------------------------------------
2-logIn 
Path : /login
Request Body:
{
    "userName":"amarashujaa",
    "password":"123456"
}
Response:
-200 Created: success.
-400 Bad Request: Invalid input data.
--------------------------------------------------------------------

