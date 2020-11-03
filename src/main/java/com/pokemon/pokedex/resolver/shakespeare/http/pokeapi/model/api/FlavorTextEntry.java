package com.pokemon.pokedex.resolver.shakespeare.http.pokeapi.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FlavorTextEntry {

  @JsonProperty("flavor_text")
  private String flavorText;
  private FlavorTextLanguage language;

}
