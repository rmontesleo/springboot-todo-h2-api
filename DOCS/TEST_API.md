# Test API

[RETURN TO README](../README.md)

## Test with Swagger

### See the API definition
- [API](http://localhost:8080/api/todoapp/v3/api-docs/)
- [Swagger UI](http://localhost:8080/api/todoapp/swagger-ui.html)


## Test with  user-controller with curl

### Verify API is runnig.  If the result is 200 the API is available
```bash
http_code=$(curl --write-out "%{http_code}\n" --output /dev/null --silent   http://localhost:8080/api/todoapp/v3/api-docs) && echo $http_code
```


### Get all users
```bash
curl -X 'GET' \
'http://localhost:8080/api/todoapp/users/all' \
-H 'accept: */*'
```

### The first time, response will be
```json
[]
```

### Signup new user
```bash
curl -X 'POST' \
'http://localhost:8080/api/todoapp/users/signup' \
-H 'accept: */*' \
-H 'Content-Type: application/json' \
-d '{
  "userName": "chanchito",
  "password": "feliz"
}'
```

### Response include the userId. Here some sample if is the first user to save
```json
{
  "userId": 1,
  "userName": "chanchito"
}
```


### Login user.
```bash
curl -X 'POST' \
  'http://localhost:8080/api/todoapp/users/login' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "userName": "chanchito",
  "password": "feliz"
}'
```

### The response gets the userId value
```json
1
```

### Get an specific user by id
```bash
curl -X 'GET' \
  'http://localhost:8080/api/todoapp/users/1' \
  -H 'accept: */*'
```

### Sample Response
```json
{
  "userId": 1,
  "userName": "chanchito"
}
```

### Test again all users
```bash
curl -X 'GET' \
  'http://localhost:8080/api/todoapp/users/all' \
  -H 'accept: */*'
```

### Sample response
```bash
[
  {
    "userId": 1,
    "userName": "chanchito"
  }
]
```

## Test todo controller with curl


### gell all todos from all users
```bash
curl -X 'GET' \
'http://localhost:8080/api/todoapp/todos/all' \
-H 'accept: */*'
```

### Post a new todo for user with id 1
```bash
curl -X 'POST' \
  'http://localhost:8080/api/todoapp/todos/1' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "title": "Learn Java",
  "description": "Learn POO, Design patterns and Spring Boot"
}'
```

### Sample response
```json
{
  "todoId": 1,
  "createdBy": 1,
  "createdAt": "2022-06-18T23:06:32.6822896",
  "updatedAt": null,
  "title": "Learn Java",
  "description": "Learn POO, Design patterns and Spring Boot",
  "completed": false
}
```

### Get all todos for user with id 1
```bash
curl -X 'GET' \
  'http://localhost:8080/api/todoapp/todos/1' \
  -H 'accept: */*'
```

### Get body and header
```bash
full_response=$(curl -i -s   -X 'GET' 'http://localhost:8080/api/todoapp/todos/1')
```



### Sample response
```json
[
  {
    "todoId": 1,
    "createdBy": 1,
    "createdAt": "2022-06-18T23:06:32.68229",
    "updatedAt": null,
    "title": "Learn Java",
    "description": "Learn POO, Design patterns and Spring Boot",
    "completed": false
  }
]
```

### Get the todo with id 1 for the user with id 1
```bash
curl -X 'GET' \
  'http://localhost:8080/api/todoapp/todos/1/1' \
  -H 'accept: */*
```

### Sample response
```json
{
  "todoId": 1,
  "createdBy": 1,
  "createdAt": "2022-06-18T23:06:32.68229",
  "updatedAt": null,
  "title": "Learn Java",
  "description": "Learn POO, Design patterns and Spring Boot",
  "completed": false
}
```

### get all todos by useer and todo status
```bash
curl -X 'GET' \
  'http://localhost:8080/api/todoapp/todos/1/status/false' \
  -H 'accept: */*'
```

### Sample response
```json
[
  {
    "todoId": 1,
    "createdBy": 1,
    "createdAt": "2022-06-18T23:06:32.68229",
    "updatedAt": null,
    "title": "Learn Java",
    "description": "Learn POO, Design patterns and Spring Boot",
    "completed": false
  }
]
```

###  Delete the some todo by user  http://localhost:8080/api/todoapp/todos/{userId}/{todoId}
```bash
curl -X 'DELETE' \
  'http://localhost:8080/api/todoapp/todos/1/1' \
  -H 'accept: */*'
```

[RETURN TO README](../README.md)