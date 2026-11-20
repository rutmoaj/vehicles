package se.yrgo.vehicles.rest;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import se.yrgo.vehicles.domain.*;
import se.yrgo.vehicles.dto.*;
import se.yrgo.vehicles.repository.*;

@RestController
public class CustomerController {
    private CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PostMapping("/customers")
    public ResponseEntity<CustomerDTO> createCustomer(@RequestParam String name, @RequestParam("phone") String phoneNumber) {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setPhoneNumber(phoneNumber);

        Customer saved = customerRepository.save(customer);

        CustomerDTO response = new CustomerDTO(saved.getId(), saved.getName(), saved.getPhoneNumber(), null);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/customers")
    public ResponseEntity<List<CustomerDTO>> allCustomersWithVehicles() {

        List<CustomerDTO> customers = customerRepository.findAllCustomersWithVehicles()
                .stream()
                .map(customer -> new CustomerDTO(
                        customer.getId(),
                        customer.getName(),
                        customer.getPhoneNumber(),
                        customer.getVehicles().stream()
                                .map(vehicle -> new VehicleDTO(
                                        vehicle.getId(),
                                        vehicle.getRegistrationNumber(),
                                        vehicle.getBrand(),
                                        vehicle.getModel(),
                                        vehicle.getProductionYear()))
                                .toList()))
                .toList();

        return ResponseEntity.ok(customers);
    }

    @GetMapping("/customer-id")
    public ResponseEntity<Long> getCustomerIdByName(@RequestParam String name) {
        return customerRepository.findByName(name)
                .map(Customer::getId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
