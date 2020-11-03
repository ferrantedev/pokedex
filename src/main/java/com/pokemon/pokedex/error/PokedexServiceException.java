package com.pokemon.pokedex.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class PokedexServiceException extends ResponseStatusException {

  public PokedexServiceException(HttpStatus status, String reason, Throwable cause) {
    super(status, reason, cause);
  }

}
