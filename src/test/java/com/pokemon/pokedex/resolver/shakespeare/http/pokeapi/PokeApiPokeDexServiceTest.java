package com.pokemon.pokedex.resolver.shakespeare.http.pokeapi;

import com.pokemon.pokedex.configuration.ApplicationProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
class PokeApiPokeDexServiceTest {

  @Mock
  ApplicationProperties properties;

  @Mock
  WebClient pokeApiWebClient;

  @InjectMocks
  PokeApiPokeDexService pokeApiPokeDexService;

  @Test
  void resolvePokemonDescription() {

  }

}