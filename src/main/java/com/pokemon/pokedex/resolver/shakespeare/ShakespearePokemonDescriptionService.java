package com.pokemon.pokedex.resolver.shakespeare;

import com.pokemon.pokedex.resolver.PokemonDescriptionService;
import com.pokemon.pokedex.resolver.shakespeare.http.pokeapi.PokeApiPokeDexService;
import com.pokemon.pokedex.resolver.shakespeare.http.shakespeareTranslation.ShakespeareTranslationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * PokemonDescriptionService implementation in charge of obtaining the translation from the
 * Shakespeare translation third party service.
 */
@Service
@Slf4j
public class ShakespearePokemonDescriptionService implements PokemonDescriptionService {

  private final PokeApiPokeDexService pokeDexService;
  private final ShakespeareTranslationService translationService;

  @Autowired
  public ShakespearePokemonDescriptionService(
      PokeApiPokeDexService pokeDexService,
      ShakespeareTranslationService translationService) {
    this.pokeDexService = pokeDexService;
    this.translationService = translationService;
  }

  @Override
  public Mono<String> resolvePokemonDescription(String pokemonName) {
    return pokeDexService.resolvePokemonDescription(pokemonName)
        .flatMap(translationService::translate);
  }

}
