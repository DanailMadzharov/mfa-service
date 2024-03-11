# Getting Started

This microservice is responsible for issuing and validating MFA one time passwords. It provides 2 endpoints, 
one for creating a MFA code and another one for verifying it with the corresponding email address.
Example CURL-s can be found on the bottom of this page.

NOTE: For the purpose of this task I have not used FreeMaker for making my emails look better.
This is a demonstration project.

# HOW TO RUN LOCALLY

In order to run this application locally with Docker-Compose we must follow these steps:

0. Install docker and docker-compose, have docker running. I suggest restarting it if it is already running,
sometimes the build command is not working when fired on an old daemon.

1. Create a Google Account App password in order to be able to send emails from your email address:

* Go to "Manage your Google Profile" on top right
*  On the left pannel click Security
*  Turn on 2-Factor Authentication if it is not turned on
*  Open the 2-Factor Authentication page and on the botton you will see "App Passwords"
* Choose an app name, create the app password and save it to the .env file in the root directory


2. Populate the .env file which is in the root directory with the newly created APP_PASSWORD for GMAIL and the email 
that the app password corresponds to. The other properties you can leave as they are.


3. From the root directory of the project run the following command:
`docker-compose up --build`

# Example CURL requests

TIP: CURL requests can be pasted into the Postman url input box and they automatically get converted to basic requests

1. Create and send MFA email


`curl --location 'http://localhost:8069/v1/otp/verify' \
--header 'Content-Type: application/json' \
--data '{
"email" : "yourEmailAddress",
"code" : "yourCode"
}'`

2. Verify MFA code


`curl --location 'http://localhost:8069/v1/otp/verify' \
   --header 'Content-Type: application/json' \
   --data '{
   "email" : "yourEmailAddress",
   "code" : "yourCode"
   }'`