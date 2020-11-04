package com.pokemon.pokedex.resolver.shakespeare.http.pokeapi;

import com.pokemon.pokedex.configuration.ApplicationProperties;
import com.pokemon.pokedex.configuration.Constants;
import com.pokemon.pokedex.resolver.shakespeare.http.pokeapi.model.api.PokemonDetailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PokeApiPokeDexService {

  private final ApplicationProperties properties;
  private final WebClient pokeApiWebClient;

  @Autowired
  public PokeApiPokeDexService(ApplicationProperties properties,
      WebClient pokeApiWebClient) {
    this.properties = properties;
    this.pokeApiWebClient = pokeApiWebClient;
  }

  public Mono<String> resolvePokemonDescription(final String pokemonName) {
    return pokeApiWebClient.get().uri(properties.getPokeapiServiceEndpoint() + "/" + pokemonName)
        .retrieve().bodyToMono(PokemonDetailsResponse.class)
        .flatMapIterable(res -> res.getFlavorTextEntries())
        .filter(flavorTextEntry ->
            Constants.TARGET_FLAVOR.equalsIgnoreCase(flavorTextEntry.getLanguage().getName()))
        .take(1)
    .single()
    .map(flavorTextEntry -> flavorTextEntry.getFlavorText());
  }

}
