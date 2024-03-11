# Getting Started

This microservice is responsible for issuing and validating MFA one time passwords. It provides 2 endpoints,
one for creating a MFA code and another one for verifying it with the corresponding email address. I also added one
more value to MfaType.java - SMS, so that I can show the idea of extending the service with different MFA types .
Example CURL-s can be found on the bottom of this page.

NOTE: For the purpose of this task I have not used FreeMaker for making my emails look better.
This is a demonstration project.

# HOW TO RUN LOCALLY

In order to run this application locally with Docker-Compose we must follow these steps:

0. Install docker

1. Install docker-compose

2. Start docker.

   TIP: I suggest restarting it if it is already running,
   sometimes the build command is not working when fired on top of an old daemon.

3. From the root directory of the project run the following command:
   `docker-compose up --build`

# Example CURL requests

TIP: CURL requests can be pasted into the Postman url input box and they automatically get converted to basic requests

1. Create and send MFA email

`curl --location 'http://localhost:8069/v1/otp' \
--header 'Content-Type: application/json' \
--data '{
"email" : "yourEmailAddress"
}'`

2. Verify MFA code

`curl --location 'http://localhost:8069/v1/otp/verify' \
--header 'Content-Type: application/json' \
--data '{
"email" : "yourEmailAddress",
"code" : "yourCode"
}'`