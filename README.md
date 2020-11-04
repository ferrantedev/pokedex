## TrueLayer's Pokemon description resolver challenge

Obtain the pokemon's description in a Shakespearean style.

## Run requirements
In order to run this microservice, you will need to install:
- Java 8+
- Gradle
- Docker

## Running the tests and viewing the test report
With your favourite CLI, navigate to the project's root.
Clean, build, test and generate the code coverage report by running 
`./gradlew clean build test jacocoTestReport`

Open the `index.html` file inside the folder `build/jacoco/test/html/`


## Run instructions
With your favourite CLI, navigate to the project's root.

- Build and package the project `./gradlew clean build bootjar`

- Build the docker image: `docker build . -t pokedex`

- Run the image locally `docker run -d -p 5000:5000 -it pokedex`

The last command should output the container id and are now ready to consume.

## Consumption instructions

