# Autumn

All right, but apart from the sanitation, the medicine, education, wine, public order, irrigation, roads, the fresh-water system, and public health, what have the Romans ever done for us?

The purpose of this project is to show what does popular frameworks do for us.
Besides that, This is also an experiment of using as few libraries as possible without going completely insane.

Enjoy


## Testing/using

You can find Postman environment and collections in the ```doc/postman``` directory to try it out.

To start the service please run the ```./mvnw cargo:run``` command.
The service will be available at http://localhost:8080/autumn .


## Commands to try

1. Create a new user.

	```shell
	curl -X POST \
		--header "Content-Type: application/json" \
		-d '{ "username": "Test user" }' \
		http://localhost:8080/autumn/users
	```

2. List the users

	```shell
	curl -X GET \
		--header "Accept: application/json" \
		http://localhost:8080/autumn/users
	```

3. Display a single user by ID

	```shell
	curl -X GET \
		--header "Accept: application/json" \
		http://localhost:8080/autumn/users/1
	```

4. Delete a user

	```shell
	curl -X DELETE \
		--header "Accept: application/json" \
		http://localhost:8080/autumn/users/1
	```
