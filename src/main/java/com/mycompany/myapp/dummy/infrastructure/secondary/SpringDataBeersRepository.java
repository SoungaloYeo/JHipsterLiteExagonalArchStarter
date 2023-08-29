package com.mycompany.myapp.dummy.infrastructure.secondary;

import java.util.Optional;
import org.springframework.stereotype.Repository;
import com.mycompany.myapp.dummy.domain.BeerId;
import com.mycompany.myapp.dummy.domain.beer.Beer;
import com.mycompany.myapp.dummy.domain.beer.BeerSellingState;
import com.mycompany.myapp.dummy.domain.beer.Beers;
import com.mycompany.myapp.dummy.domain.beer.BeersRepository;
import com.mycompany.myapp.shared.error.domain.Assert;

@Repository
class SpringDataBeersRepository implements BeersRepository {

  private final JpaBeersRepository beersRepository;

  public SpringDataBeersRepository(JpaBeersRepository beersRepository) {
    this.beersRepository = beersRepository;
  }

  @Override
  public void save(Beer beer) {
    Assert.notNull("beer", beer);

    beersRepository.save(BeerEntity.from(beer));
  }

  @Override
  public Beers catalog() {
    return new Beers(beersRepository.findBySellingState(BeerSellingState.SOLD).stream().map(BeerEntity::toDomain).toList());
  }

  @Override
  public Optional<Beer> get(BeerId beer) {
    Assert.notNull("beer", beer);

    return beersRepository.findById(beer.get()).map(BeerEntity::toDomain);
  }
}
