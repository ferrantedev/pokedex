package com.pokemon.pokedex.resolver.shakespeare;

import com.pokemon.pokedex.resolver.shakespeare.http.pokeapi.PokeApiPokeDexService;
import com.pokemon.pokedex.resolver.shakespeare.http.shakespeareTranslation.ShakespeareTranslationService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
class ShakespearePokemonDescriptionServiceTest {

  @InjectMocks
  ShakespearePokemonDescriptionService shakespearePokemonDescriptionService;
  @Mock
  private PokeApiPokeDexService pokeDexService;
  @Mock
  private ShakespeareTranslationService translationService;

  @Test
  void resolvePokemonDescription_givenPokemonName_expectShakespeareanDescription() {
    String pokemonName = "bulbasaur";
    String expectedDescription = "Phasellus sit amet eros non leo condimentum euismod. Nam fermentu";
    Mockito.when(pokeDexService.resolvePokemonDescription(Mockito.anyString()))
        .thenReturn(Mono.just(pokemonName));
    Mockito.when(translationService.translate(Mockito.anyString()))
        .thenReturn(Mono.just(expectedDescription));
    StepVerifier.create(
    shakespearePokemonDescriptionService.resolvePokemonDescription(pokemonName))
        .expectNext(expectedDescription)
    .expectComplete()
    .verify();
  }

}