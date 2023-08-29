package com.mycompany.myapp.dummy.domain.beer;

import com.mycompany.myapp.dummy.domain.BeerId;
import com.mycompany.myapp.shared.error.domain.Assert;

public class BeersRemover {

  private final BeersRepository beersRepository;

  public BeersRemover(BeersRepository beersRepository) {
    Assert.notNull("beers", beersRepository);

    this.beersRepository = beersRepository;
  }

  public void remove(BeerId id) {
    Assert.notNull("id", id);

    Beer beer = beersRepository.get(id).orElseThrow(() -> new UnknownBeerException(id));

    beer.removeFromSell();
    beersRepository.save(beer);
  }
}
