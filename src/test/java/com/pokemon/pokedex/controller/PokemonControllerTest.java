package com.pokemon.pokedex.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.pokemon.pokedex.api.PokemonDescriptionResponse;
import com.pokemon.pokedex.resolver.PokemonDescriptionService;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = PokemonController.class)
@AutoConfigureWebTestClient
class PokemonControllerTest {

  @MockBean
  PokemonDescriptionService pokemonDescriptionService;

  @Autowired
  private WebTestClient webTestClient;

  @Test
  void testController() {
    final String pokemonName = "dragonite";
    final String pokemonDescription = "";
    PokemonDescriptionResponse expectedResponse = new PokemonDescriptionResponse(pokemonName,
        pokemonDescription);
    Mockito.when(pokemonDescriptionService.resolvePokemonDescription(Mockito.anyString()))
        .thenReturn(Mono.just(pokemonDescription));

    ResponseSpec responseSpec = webTestClient.get()
        .uri("/pokemon/" + pokemonName)
        .exchange();
        responseSpec.expectBody(PokemonDescriptionResponse.class).value(
            pokemonDescriptionResponse -> {
              assertEquals(expectedResponse.getName(), pokemonDescriptionResponse.getName());
              assertEquals(expectedResponse.getDescription(),pokemonDescriptionResponse.getDescription() );
            });
        responseSpec.expectStatus().isOk();
  }

}