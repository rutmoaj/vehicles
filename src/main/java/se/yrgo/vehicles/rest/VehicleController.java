package se.yrgo.vehicles.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import se.yrgo.vehicles.domain.*;
import se.yrgo.vehicles.dto.VehicleBrandDTO;
import se.yrgo.vehicles.dto.VehicleDTO;
import se.yrgo.vehicles.repository.*;

@RestController
public class VehicleController {
    private VehicleRepository vehicleRepository;
    private CustomerRepository customerRepository;

    public VehicleController(VehicleRepository vehicleRepository, CustomerRepository customerRepository) {
        this.vehicleRepository = vehicleRepository;
        this.customerRepository = customerRepository;
    }

    @PostMapping("/vehicles")
    public ResponseEntity<VehicleDTO> createVehicle(@RequestParam String registrationNumber, @RequestParam String brand,
            @RequestParam String model, @RequestParam int productionYear) {
        Vehicle vehicle = new Vehicle();
        vehicle.setRegistrationNumber(registrationNumber);
        vehicle.setBrand(brand);
        vehicle.setModel(model);
        vehicle.setProductionYear(productionYear);

        Vehicle saved = vehicleRepository.save(vehicle);

        VehicleDTO response = new VehicleDTO(
                saved.getId(),
                saved.getRegistrationNumber(),
                saved.getBrand(),
                saved.getModel(),
                saved.getProductionYear());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/addvehicletocustomer")
    public ResponseEntity<String> addVehicleToCustomer(@RequestParam Long customerId, @RequestParam Long vehicleId) {
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        Optional<Vehicle> vehicleOpt = vehicleRepository.findById(vehicleId);

        if (customerOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found: " + customerId);
        }
        if (vehicleOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehicle not found: " + vehicleId);
        }

        Vehicle vehicle = vehicleOpt.get();
        vehicle.setCustomer(customerOpt.get());
        vehicleRepository.save(vehicle);

        return ResponseEntity.ok("Successfully assigned customer to vehicle.");
    }

    // returnerar en lista av alla fordon
    @GetMapping("/vehicles")
    public ResponseEntity<List<VehicleDTO>> getVehicles() {
        List<VehicleDTO> vehicles = vehicleRepository.findAll()
                .stream().map(vehicle -> new VehicleDTO(vehicle.getId(), vehicle.getRegistrationNumber(),
                        vehicle.getBrand(), vehicle.getModel(), vehicle.getProductionYear()))
                .toList();
        return ResponseEntity.ok(vehicles);
    }

    // returnerar alla fordon som tillhör ett visst bilmärke (brand)
    @GetMapping("/vehicles-by-brand")
    public ResponseEntity<List<VehicleDTO>> findByBrand(@RequestParam String brand) {
        List<VehicleDTO> vehiclesWithBrand = vehicleRepository.findByBrand(brand)
                .stream().map(vehicle -> new VehicleDTO(vehicle.getId(), vehicle.getRegistrationNumber(),
                        vehicle.getBrand(), vehicle.getModel(), vehicle.getProductionYear()))
                .toList();
        return ResponseEntity.ok(vehiclesWithBrand);
    }

    // Uppdatera bilmärket för ett fordon.
    @PutMapping("/vehicles/{id}/brand")
    public ResponseEntity<String> updateBrand(@RequestParam String brand, @PathVariable Long id) {

        int updated = vehicleRepository.updateBrandById(brand, id);

        if (updated == 0) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Brand updated successfully.");
    }

    @GetMapping("/vehicle-brand-info")
    public ResponseEntity<List<VehicleBrandDTO>> getVehicleBrandInfo() {

        List<VehicleBrandDTO> vehicles = vehicleRepository.findAllVehicleInfo()
                .stream()
                .map(vehicle -> new VehicleBrandDTO(
                        vehicle.getRegistrationNumber(),
                        vehicle.getBrand()))
                .toList();

        return ResponseEntity.ok(vehicles);
    }
}
