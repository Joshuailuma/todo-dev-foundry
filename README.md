# Todo App for Devfoundry

In this backend application, you can:

1. Register
2. Login
3. Add a todo
4. Edit a todo
5. Mark a todo as completed
6. Delete a todo

## Getting started


All required packages are included in the pom.xml file.

1. Install [Java 17](https://www.java.com/download/ie_manual.jsp)
2. Install [Maven](https://maven.apache.org/install.html)
3. From project directory, run `mvn spring-boot:run`

The Todo app is hosted locally by default on `http://127.0.0.1:8080`

### Error Handling
Errors are returned as JSON objects in the following format:
```json
{
  "message": "Error message",
  "httpStatus": "BAD_REQUEST",
  "errors": [
    "Error message details"
  ]
}
```

The API will return three error types when requests fail:

- 201: Created
- 200: OK
- 400: Bad Request
- 404: Resource Not Found 

### Endpoints
`POST /auth/register`

- Registers a user
- Request parameters: Firstname, lastname, email, password
- Returns: An object with registration details of the user
  Sample: `curl http://127.0.0.1:8080/auth/register -X POST -H "Content-Type: application/json" -d 
'{
"firstName": "Joshua",
    "lastName": "me",
    "email":"realtime@gmail.comm",
    "password": "123456!@"
}'`

```json
{
  "message": "Registration successful",
    "result": {
        "id": "13660975-7282-494f-bac4-0846088d84a3",
        "email": "realtime@gmail.comm",
        "firstName": "Joshua",
        "lastName": "me"
    }
}
```

`POST /auth/login`

- Logs the user in
- Request parameters: Email, password
- Returns: An object with login details of the user
  Sample: `curl http://127.0.0.1:8080/auth/login -X POST -H "Content-Type: application/json" -d 
'{
    "email":"realtime@gmail.comm",
    "password": "123456!@"
}'`

```json
{
  "message": "Login successful",
    "result": {
        "token": "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6W10sImlhdCI6MTczNjQxMDIwMywiZXhwIjoxNzM2ODEwMjAzLCJzdWIiOiJyZWFsdGltZUBnbWFpbC5jb21tIn0.5WC2cHSz6mO7mAk2yUgTAf2V0_qWX62rudXSZcE2uOM",
        "expiresAt": 40000
    }
}
```

`POST /todo/add-todo`

- Allows user to add a todo
- Request parameters: Title, text, priority, dueDate
- Returns: Details of the saved todo
  Sample: `curl http://127.0.0.1:8080/todo/add-todo -X POST -H "Content-Type: application/json" -d 
'{
    "title":"Second todo",
    "text":"A very sweet message",
    "priority": "LOW",
    "dueDate": "12-03-2025"
}'`

```json
{
  "message": "Todo successfully saved",
  "result": {
    "id": "f6cce2ab-e16a-41b8-8057-e5423a0ad1fe",
    "title": "Second todo",
    "text": "Mop the floor",
    "completed": false,
    "user": null,
    "priority": "LOW",
    "startDate": "2025-01-09",
    "dueDate": "2025-03-12"
  }
}
```

`PATCH /todo/edit-todo`

- Allows user to edit a todo
- Optional parameters: Title, text, dueDate
- Returns: Details of the edited todo
  Sample: `curl http://127.0.0.1:8080/todo/edit-todo -X POST -H "Content-Type: application/json" -d 
'{
    "title":"An edited todo",
    "text":"Clean the house",
    "priority": "High",
    "dueDate": "12-03-2026"
}'`

```json
{
  "message": "Todo item edited",
  "result": {
    "id": "f6cce2ab-e16a-41b8-8057-e5423a0ad1fe",
    "title": "Old todo",
    "text": "A very sweet message",
    "completed": false,
    "user": null,
    "priority": "LOW",
    "startDate": "2025-01-09",
    "dueDate": "2025-03-12"
  }
}
```


`POST /todo/mark-complete/{todoId}`

- Allows user to mark a todo as completed
-  Path variable: TodoId
- Returns: Details of the todo marked as completed
  Sample: `curl http://127.0.0.1:8080/todo/mark-complete/f6cce2ab-e16a-41b8-8057`

```json
{
  "message": "Todo completed",
  "result": {
    "id": "f6cce2ab-e16a-41b8-8057-e5423a0ad1fe",
    "title": "Old todo",
    "text": "A very sweet message",
    "completed": true,
    "user": null,
    "priority": "LOW",
    "startDate": "2025-01-09",
    "dueDate": "2025-03-12"
  }
}
```


`DELETE /todo/delete/{todoId}`

- Allows user to delete a todo
-  Path variable: TodoId
- Returns: Details of the deleted todo
  Sample: `curl http://127.0.0.1:8080/todo/delete/f6cce2ab-e16a-41b8-8057`

```json
{"message": "Todo item deleted",
  "result": {
    "id": "f6cce2ab-e16a-41b8-8057-e5423a0ad1fe",
    "title": "Old todo",
    "text": "A very sweet message",
    "completed": true,
    "user": null,
    "priority": "LOW",
    "startDate": "2025-01-09",
    "dueDate": "2025-03-12"
  }
}
```

`GET /todo/get-todos`

- Allows user to fetch all todos
- Returns: Details of all todos
  Sample: `curl http://127.0.0.1:8080/todo/get-todos`

```json
{
  "content": [
    {
      "title": "New todo",
      "text": "A very sweet message",
      "priority": "LOW",
      "completed": false,
      "startDate": "2025-01-08",
      "dueDate": "2025-12-13"
    }
  ],
  "pageNumber": 1,
  "pageSize": 50,
  "totalElements": 1,
  "totalPages": 1,
  "last": true,
  "first": true
}
```