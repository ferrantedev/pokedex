package com.pokemon.pokedex.resolver.shakespeare.http.shakespeareTranslation;

import com.pokemon.pokedex.configuration.ApplicationProperties;
import com.pokemon.pokedex.resolver.shakespeare.http.shakespeareTranslation.model.api.ShakespeareTranslationRequest;
import com.pokemon.pokedex.resolver.shakespeare.http.shakespeareTranslation.model.api.ShakespeareTranslationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ShakespeareTranslationService {

  private final ApplicationProperties properties;
  private final WebClient webClient;

  @Autowired
  public ShakespeareTranslationService(
      ApplicationProperties applicationProperties,
      @Qualifier("spWebClient") WebClient webClient) {
    this.properties = applicationProperties;
    this.webClient = webClient;
  }

  public Mono<String> translate(final String text) {
    return webClient.post()
        .uri(properties.getShakespeareServiceEndpoint())
        .body(BodyInserters.fromValue(new ShakespeareTranslationRequest(text)))
        .retrieve()
        .bodyToMono(ShakespeareTranslationResponse.class)
        .map(response -> response.getContents().getTranslated());
  }

}