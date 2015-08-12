# routeopt
Routeopt is a Java web application that provides RESTfull web services to optimize WM Company supply routes.

Architecture
============

The application is a Java web application that integrates with MongoDB. 

Why Java? 
---------

Given the three options (Java, JavaScript and Ruby), I really like JavaScript and NodeJS and I think it is in an incredible growing pace. But Java has a broader landscape, a huge and passionate community that maintain innovative and reliable tools. 

Why MongoDB?
------------

The application is designed to be stateless. It helps Routeopt scale better. MongoDB is a wonderful choice to provide scalable and high available persistence. This combo makes Routeopt ready for the cloud and to help WM Company grow its business.

The Architecture
----------------

The application was conceived with the following tiers:
	- Integration tier: Responsible for exposing the REST API marshalling and unmarshalling the request/response messages exchanged with external systems. You can find more at /routeopt-rest.
	- Application tier: Responsible for providing the required business logics. You can find more at /routeopt-services.
	- Data tier: Responsible for providing persistence mechanism and mapping domain objects into MongoDB documents. You can find more at /routeopt-repositories.

**Integration tier**
The integration tier is based on SpringMVC and its main component is: 
	- Controller: Responsible for handling the web services requests and routing them to the right business services.

**Application tier**
The application tier is based on Spring IoC container and its main component are:
	- Service: These are where we implement the business logics.

**Data tier**
The data tier is based on Spring Data and its main component is:
	- Repository: This component is responsible for integrating with MongoDB allowing data retrieval and persistence.

Requirements
------------

The application uses MongoDB as its persistence solution. Before running Routeopt please check if mongod is running.
Also, take a minute to configure MongoDB settings in: 
/routeopt-repositories/src/main/resources/mongodb.properties and 
/routeopt-repositories/src/test/resources/mongodb.properties. 

NOTE: The application supports MongoDB replicas. In order to do so, please  configure nodes addresses on "addresses" key using the format "host:port". 

Example:
1.2.3.4:1000, 1.2.4.5:2000. (The first address has precedence when writing data).

Running
-------
	- You can import the projects in you favourite IDE and take a look on how fun was to build them.
	- You can pack the application using Maven and deploy it on you favourite Java application server.
		- Open your terminal/cmd, browse to /routeopt and run:
		>	mvn clean package
		- Your war file is going to be at /routeopt-rest/target
	- Once the REST API is deployed, there will be 3 endpoints available to be used.

Web Services
	All the operations return JSON messages.
	
	- http://localhost:8080/routeopt-rest/optimize - Route optimizer
	- Operation:
		GET /{map}/{from}/{to}/{truckAutonomy}/{gasPrice} 
		Returns the optimizes route starting at "from" point and ending at "to" point. It will consider the truck fuel autonomy (kilometres/litre) and the gas price.
		Example: GET http://localhost:8080/routeopt-rest/optimize/XPTO/A/D/10/2.5

	- /localhost:8080/routeopt-rest/map	- Map management. A map is the information all the possible legs that between 2 places in a supply chain.
	- Operations:
		GET / 
		Returns all the subroutes been managed by the application.
		Example: GET http://localhost:8080/routeopt-rest/map
			
		GET /{mapName} 
		Returns all the subroutes given a map name.
		Example: GET http://localhost:8080/routeopt-rest/map/XPTO
		
		POST / * JSON request
		Creates map entries based on the JSON format: {"name": "....", "routes": "FROM TO DISTANCE"}
		Example: POST http://localhost:8080/routeopt-rest/map
				 {"name":"Test","routes":"A B 10\nB D 15\nA C 20\n\nC D 30\nB E 50\nD E 30"}

		DELETE /{id}
		Deletes a subroute given its id.
		Example: DELETE http://localhost:8080/routeopt-rest/map/mySubrouteId

	NOTE: You can find JUnit test cases that with a REST client at /routeopt-rest/src/test/java. They can help you have fun with the API faster.
		  You can also use "Advanced REST client" Chrome plugin to test the Web Services.

TO DO
-----
Several architectural services could be added to the application if it was supposed to evolve. Including:
- Security
- Audit Trail
- Improved Error Handling
- Integration Log + Message Reprocessing
- Asynchronous Integration Support
- Integrating Quota and Monetization
- Continuous Delivery