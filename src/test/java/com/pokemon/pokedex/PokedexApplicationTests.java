package com.pokemon.pokedex;

import com.pokemon.pokedex.controller.PokemonController;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PokedexApplicationTests {

	@Autowired
	PokemonController pokemonController;

	@Test
	void contextLoads() {
		Assertions.assertThat(pokemonController).isNotNull();
	}

}
