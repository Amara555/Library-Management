
## Postman Collection

You can find the Postman collection for the Library Management System API in the `docs/postman/` folder. To import it into Postman:

1. Download the `File-system-collection.json` file.
2. Open Postman and click **Import**.
3. Select the downloaded `.json` file.

This collection contains all the API endpoints for book, patron, and borrowing record management.

📖 Library Management System API Documentation

Base URL
http://localhost:8080/api

1-Add new User 

Path : /user/add

Request Body:

{

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

