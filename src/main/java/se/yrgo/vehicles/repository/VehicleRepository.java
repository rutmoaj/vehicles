package se.yrgo.vehicles.repository;

import java.util.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import se.yrgo.vehicles.domain.*;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    // Hämtar alla fordon som tillhör ett visst bilmärke.
    List<Vehicle> findByBrand(String brand);

    // JPQL-fråga som hämtar alla fordon för en viss kund baserat på kundens ID.
    @Query("SELECT v FROM Vehicle v WHERE v.customer.id = :customerId")
    List<Vehicle> findVehiclesByCustomerId(@Param("customerId") Long id);

    // Uppdatera bilmärket för ett fordon baserat på fordonets ID.
    @Modifying
    @Transactional
    @Query("Update Vehicle v SET v.brand = :brand WHERE v.id = :id")
    int updateBrandById(@Param("brand") String brand, @Param("id") Long id);

    // Hämta registrationNumber och brand
    @Query("SELECT v.registrationNumber AS registrationNumber, v.brand AS brand FROM Vehicle v")
    List<VehicleBrandProjection> findAllVehicleInfo();

}
