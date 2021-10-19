# Assessment-Etisalat


1) Run the Project using the following command after building the Project
  java -jar target/Assessment-0.0.1-SNAPSHOT.jar -h
  Or 
  Import the project in an IDE like Eclipse as Maven Project and then run the project as Java Application


2)Register for a user using the following url:

curl -X POST "http://localhost:8080/weatherInfo/register?email=xxx%40gmail.com&name=xxx&password=yyyyyy" -H "accept: application/json" -d ""

After the user is registered, swagger can be accessed using the credentials used during registration
Swagger 
---------------
http://localhost:8080/swagger-ui/
