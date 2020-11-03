package com.pokemon.pokedex.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {

  @Bean("spWebClient")
  public WebClient shakespeareWebClient(ApplicationProperties properties) {
    return WebClient.create(properties.getShakespeareServiceBaseUrl());
  }

  @Bean("pokeApiWebClient")
  public WebClient pokeapiWebClient(ApplicationProperties properties) {
    return WebClient.create(properties.getPokeapiServiceBaseUrl());
  }

}
