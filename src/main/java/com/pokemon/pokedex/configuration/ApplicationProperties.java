package com.pokemon.pokedex.configuration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties
@Configuration
@NoArgsConstructor
@Getter
@Setter
public class ApplicationProperties {


  @Value("${third-party.services.shakespeare.baseUrl}")
  private String shakespeareServiceBaseUrl;

  @Value("${third-party.services.shakespeare.endpoint}")
  private String shakespeareServiceEndpoint;


  @Value("${third-party.services.pokeapi.baseUrl}")
  private String pokeapiServiceBaseUrl;

  @Value("${third-party.services.pokeapi.endpoint}")
  private String pokeapiServiceEndpoint;


}
