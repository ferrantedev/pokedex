package com.pokemon.pokedex.resolver.shakespeare.http.pokeapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokemon.pokedex.configuration.ApplicationProperties;
import com.pokemon.pokedex.configuration.Constants;
import com.pokemon.pokedex.resolver.shakespeare.http.pokeapi.model.api.FlavorTextEntry;
import com.pokemon.pokedex.resolver.shakespeare.http.pokeapi.model.api.FlavorTextLanguage;
import com.pokemon.pokedex.resolver.shakespeare.http.pokeapi.model.api.PokemonDetailsResponse;
import java.io.IOException;
import java.util.ArrayList;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
class PokeApiPokeDexServiceTest {

  public static MockWebServer mockBackEnd;

  ApplicationProperties properties;

  PokeApiPokeDexService pokeApiPokeDexService;

  @BeforeAll
  static void setUp() throws IOException {
    mockBackEnd = new MockWebServer();
    mockBackEnd.start();
  }

  @BeforeEach
  public void beforeEach(){
    String mockShakespearePath = "/pokeapi/path/";
    properties = Mockito.mock(ApplicationProperties.class);
    Mockito.when(properties.getPokeapiServiceEndpoint())
        .thenReturn(mockBackEnd.url(mockShakespearePath).encodedPath());
    Mockito.when(properties.getPokeapiServiceBaseUrl())
        .thenReturn("http://" + mockBackEnd.url(mockShakespearePath).host() + ":" +
            mockBackEnd.url(mockShakespearePath).port());

    pokeApiPokeDexService = new PokeApiPokeDexService(properties,
        WebClient.create(properties.getPokeapiServiceBaseUrl()));
  }

  @AfterAll
  static void tearDown() throws IOException {
    mockBackEnd.shutdown();
  }

  @Test
  void resolvePokemonDescription() throws JsonProcessingException {
    String expectedDescription = "The quick brown fox jumps over the lazy dog";

    PokemonDetailsResponse mockPokemonDetailsResponse = new PokemonDetailsResponse();
    mockPokemonDetailsResponse.setFlavorTextEntries(new ArrayList<>());
    FlavorTextEntry entry = new FlavorTextEntry();
    entry.setFlavorText(expectedDescription);
    FlavorTextLanguage flavorTextLanguage = new FlavorTextLanguage();
    flavorTextLanguage.setName(Constants.TARGET_FLAVOR);
    entry.setLanguage(flavorTextLanguage);
    mockPokemonDetailsResponse.getFlavorTextEntries().add(entry);

    ObjectMapper om = new ObjectMapper();

    mockBackEnd.enqueue(new MockResponse()
        .setBody(om.writeValueAsString(mockPokemonDetailsResponse))
        .addHeader("Content-Type", "application/json"));
    StepVerifier.create(pokeApiPokeDexService.resolvePokemonDescription("bulbasaur"))
        .expectNext(expectedDescription)
        .expectComplete()
        .verify();
  }

}