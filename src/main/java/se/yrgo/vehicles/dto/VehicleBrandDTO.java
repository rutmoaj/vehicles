package se.yrgo.vehicles.dto;

public class VehicleBrandDTO {
    private String registrationNumber;
    private String brand;

    public VehicleBrandDTO(String registrationNumber, String brand) {
        this.registrationNumber = registrationNumber;
        this.brand = brand;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getBrand() {
        return brand;
    }
}
