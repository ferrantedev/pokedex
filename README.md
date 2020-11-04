## TrueLayer's Pokemon description resolver challenge

Obtain the pokemon's description in a Shakespearean style.

This service consumes the PokeApi `https://pokeapi.co/api/v2/pokemon-species/braixen/` 
endpoint and the shakespeare translate api `https://api.funtranslations.com/translate/shakespeare`
provided by Funtranslations. 

As Funtranslations api is rate limited, this api is backed by an in-memory 
cache. The cache eviction timeout is set at 1 minutes.
Nevertheless, if you encounter the following error, it means you've reached their limit.

```json 
{
    "status" : 429,
    "message" : "",
    "error" : "Too Many Requests",
    "timestamp" : "2020-11-04T10:11:19.862+00:00",
    "requestId" : "094970cd-3",
    "path" : "/pokemon/charizard"
 }
```

## Run requirements
In order to run this microservice, you will need to install:
- Java 8+
- Gradle
- Docker

## Running the tests and viewing the test report
With your favourite CLI, navigate to the project's root.
Clean, build, test and generate the code coverage report by running 
`./gradlew clean build test jacocoTestReport`

Open the `index.html` file inside the folder `build/jacoco/test/html/`.


## Run instructions
With your favourite CLI, navigate to the project's root.

- Add execution permission to the gradle wrapper, `chmod +x gradlew`

- Build and package the project `./gradlew clean build bootjar`

- Build the docker image: `docker build . -t pokedex`

- Run the image locally `docker run -d -p 5000:5000 -it pokedex`

The last command should output the container id and are now ready to consume.

## Consumption instructions
Open your favourite CLI, make sure you have `curl` and `json_pp` installed.
To consume the service execute the following command in your terminal:

`curl http://localhost:5000/pokemon/dragonite | json_pp`

Example response:

```json
{
    "name": "dragonite",
    "description": "An extremely rarely seen marine pokémon. Its intelligence is did doth sayeth to match yond of humans."
}
```

