## Tutorial App
This documentaion is **only for the app.**

### Prerequisite Knowledge
* Views and ViewGroups in Android
* Basic Concept of multi-threading
* HTTP requests

### What do we intend to do?
* This app represents the frontend part for the backend springboot java application which we created in the [previous tutorial.](https://medium.com/webtutsplus/a-simple-user-authentication-api-made-with-spring-boot-4a7135ff1eca)  
* With frontend part, we mean that this app interacts with the user. Users enters the data into this app which is then sent to the backend software for processing.
* For readers who are familiar with web development, this app is similar to a HTML form that can POST data to backend, so that this data can be processed by a backend script and the response from the backend is displayed to the user.   
* Using this app, the user can register, login, logout and delete the user table from the backend service we created in previous tutorial.  
  
### Important Libraries/Classes used
* HttpURLConnection - for sending requests.  
* AsyncTask - for executing network calls on background thread.
