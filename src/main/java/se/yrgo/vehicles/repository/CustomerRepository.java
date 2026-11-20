package se.yrgo.vehicles.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import se.yrgo.vehicles.domain.*;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // Hämta en kund baserat på namn
    Optional<Customer> findByName(String name);

    // JPQL-fråga som hämtar alla kunder tillsammans med deras fordon.
    @Query("SELECT DISTINCT c FROM Customer c LEFT JOIN FETCH c.vehicles v")
    List<Customer> findAllCustomersWithVehicles();

}
