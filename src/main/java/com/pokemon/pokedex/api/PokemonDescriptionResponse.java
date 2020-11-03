package com.pokemon.pokedex.api;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PokemonDescriptionResponse {

  private String name;

  private String description;

}
