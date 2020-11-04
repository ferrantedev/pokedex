package com.pokemon.pokedex.resolver.shakespeare.http.shakespeareTranslation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokemon.pokedex.configuration.ApplicationProperties;
import com.pokemon.pokedex.configuration.Constants;
import com.pokemon.pokedex.resolver.shakespeare.http.shakespeareTranslation.model.api.ShakespeareTranslationResponse;
import com.pokemon.pokedex.resolver.shakespeare.http.shakespeareTranslation.model.api.TranslationContent;
import java.io.IOException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
class ShakespeareTranslationServiceTest {

  public static MockWebServer mockBackEnd;

  ApplicationProperties properties;

  ShakespeareTranslationService shakespeareTranslationService;

  @BeforeAll
  static void setUp() throws IOException {
    mockBackEnd = new MockWebServer();
    mockBackEnd.start();
  }
  @BeforeEach
  public void beforeEach(){
    String mockShakespearePath = "/shakespeare/path";
    properties = Mockito.mock(ApplicationProperties.class);
    Mockito.when(properties.getShakespeareServiceEndpoint())
        .thenReturn(mockBackEnd.url(mockShakespearePath).encodedPath());
    Mockito.when(properties.getShakespeareServiceBaseUrl())
        .thenReturn("http://" + mockBackEnd.url(mockShakespearePath).host() + ":" +
            mockBackEnd.url(mockShakespearePath).port());

    shakespeareTranslationService = new ShakespeareTranslationService(properties,
        WebClient.create(properties.getShakespeareServiceBaseUrl()));
  }

  @AfterAll
  static void tearDown() throws IOException {
    mockBackEnd.shutdown();
  }

  @Test
  void translate() throws JsonProcessingException {
    String expectedTranslation = "The quick brown fox jumps over the lazy dog";
    ShakespeareTranslationResponse mockResponse =
        new ShakespeareTranslationResponse(new TranslationContent(expectedTranslation,
            Constants.TARGET_FLAVOR, ""));

    ObjectMapper om = new ObjectMapper();

    mockBackEnd.enqueue(new MockResponse()
        .setBody(om.writeValueAsString(mockResponse))
        .addHeader("Content-Type", "application/json"));
    StepVerifier.create(shakespeareTranslationService.translate("the fox jumps over the dog"))
        .expectNext(expectedTranslation)
        .expectComplete()
        .verify();
  }

}