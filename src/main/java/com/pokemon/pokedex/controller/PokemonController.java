package com.pokemon.pokedex.controller;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.pokemon.pokedex.api.PokemonDescriptionResponse;
import com.pokemon.pokedex.error.PokedexServiceException;
import com.pokemon.pokedex.resolver.PokemonDescriptionService;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@RestController
public class PokemonController {

  private final PokemonDescriptionService pokemonDescriptionService;
  private final Cache<String, String> cache;

  @Autowired
  public PokemonController(
      PokemonDescriptionService pokemonDescriptionService) {
    this.pokemonDescriptionService = pokemonDescriptionService;
    this.cache = Caffeine.newBuilder()
        .expireAfterWrite(1, TimeUnit.MINUTES)
        .maximumSize(10000)
        .build();
  }

  @GetMapping(value = "/pokemon/{pokemonName}")
  public Mono<PokemonDescriptionResponse> getShakespeareanDescription(
      @PathVariable final String pokemonName) {
    return Mono.just(pokemonName)
        .map(name->cache.getIfPresent(name))
        .onErrorResume(NullPointerException.class,
            e -> pokemonDescriptionService
                .resolvePokemonDescription(pokemonName).doOnSuccess(s -> cache.put(pokemonName, s)))
        .map(pokemonDescription -> new PokemonDescriptionResponse(pokemonName, pokemonDescription))
        .onErrorResume(WebClientResponseException.class,
            e -> Mono.error(
                new PokedexServiceException(e.getStatusCode(), e.getResponseBodyAsString(),
                    e.getCause())))
        .log();
  }

}
