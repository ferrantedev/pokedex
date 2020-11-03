package com.pokemon.pokedex.resolver.shakespeare;

import com.pokemon.pokedex.resolver.PokemonDescriptionService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ShakespearePokemonDescriptionService implements PokemonDescriptionService {

  @Override
  public Mono<String> resolvePokemonDescription(String pokemonName) {




    return Mono.just("");
  }

}
