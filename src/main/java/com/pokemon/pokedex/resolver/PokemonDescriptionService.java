package com.pokemon.pokedex.resolver;

import reactor.core.publisher.Mono;

public interface PokemonDescriptionService {

  Mono<String> resolvePokemonDescription(final String pokemonName);

}
