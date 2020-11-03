package com.pokemon.pokedex.resolver.shakespeare.http.pokeapi.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class PokemonDetailsResponse {

  @JsonProperty(value = "flavor_text_entries")
  List<FlavorTextEntry> flavorTextEntries;

}
