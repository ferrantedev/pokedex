package com.pokemon.pokedex.controller;

import com.pokemon.pokedex.api.PokemonDescriptionResponse;
import com.pokemon.pokedex.error.PokedexServiceException;
import com.pokemon.pokedex.resolver.PokemonDescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@RestController
public class PokemonController {

  private final PokemonDescriptionService pokemonDescriptionService;

  @Autowired
  public PokemonController(
      PokemonDescriptionService pokemonDescriptionService) {
    this.pokemonDescriptionService = pokemonDescriptionService;
  }

  @GetMapping(value = "/pokemon/{pokemonName}")
  public Mono<PokemonDescriptionResponse> getShakespeareanDescription(
      @PathVariable final String pokemonName) {
    return pokemonDescriptionService
        .resolvePokemonDescription(pokemonName)
        .map(pokemonDescription -> new PokemonDescriptionResponse(pokemonName, pokemonDescription))
        .onErrorResume(WebClientResponseException.class,
            e -> Mono.error(
                new PokedexServiceException(e.getStatusCode(), e.getResponseBodyAsString(),
                    e.getCause())));
  }

}
