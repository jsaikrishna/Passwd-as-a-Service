# Passwd-as-a-Service

The Service exposes the passwrd and group files that are in /etc/passwrd and /etc/group in a UNIX like System. The Main aim of the service is to create a HTTP Service that generates the response based on query to the HTTP Service.


The Service was developed using the SpringBoot Framework and build with the Maven. The Application Service is designed using MVC Pattern (Model-View-Controller) where the Controller class (MainController.java) has been designed in the backend to handle the HTTP request, the controller returns the view which is responsible for rendering the HTML Content of the result.

Inorder to test the Application Service, Unit-Tests has been designed to check the correctness of developed functionality. Mockito and PowerMockito libraries are used to mock the static and final classess involved in the functionlaity.


Inorder to run the application clone the repository to the local system and then follow the command below to cd to the code respository folder.

```
cd Passwd-as-a-Service/passwd/

```
As the application servcie is developed using the SpingFramework, follow the command below to Start the Service.

```
mvn spring-boot:run

```

# Example HTTP Request to the Service.

* Getting all the user in Passwrd File

```
http://localhost:8080/users

```

* Getting the user based on the Query Parameters
 ```
 http://localhost:8080/users/query[?name=r][&uid=0][&gid=0][&comment=home][&shell=%2Fusr%2Fbin%2Ffalse]
 ```
 
 Here the Bracket notation indicates any one or more number or parameters can be supplied to the request.
   - Name
   - uid
   - gid
   - comment
   - home
   - shell
   

* Getting the user information based on the exact match of the shell parameter path.
```
http://localhost:8080/users/query?shell=%2Fusr%2Fbin%2Ffalse

```


* Getting the user's based on the provided user-id (uid) which is an integer. Also, this request returns the 404 Error if the  the specified user resource with provided user-id (uid) is not found.

```
http://localhost:8080/users/uid

```

* Getting the Groups for the provided user-id (uid).

```
http://localhost:8080/users/29/groups

```

* Getting the Groups of the system.

```
http://localhost:8080/groups

```

* Getting the List of groups that matches the parameters in the HTTP request.

```
http://localhost:8080/groups/query[?name=x][&gid=20][&member=y][&member=z][&member=w]

```

Here the Bracket notation indicates any one or more number or parameters can be supplied to the request.
   - Name
   - gid
   - member
   - member
   - member
   
* Getting the groups that matches the specified Group-id (gid).

```
http://localhost:8080/groups/29

```
