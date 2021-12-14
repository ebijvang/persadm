package nl.ester.persoonsapplicatie.repository;

import nl.ester.persoonsapplicatie.model.Persoon;
import org.springframework.data.repository.CrudRepository;

public interface PersoonsRepository extends CrudRepository<Persoon, Long > {
}
