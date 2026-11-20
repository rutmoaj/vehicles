# vehicles

A simple web-application resembling a vehicle workshop.

## How to run

First, give yourself the rights to run:

```
chmod +x run.sh
```

Unix:

```
./run.sh
```

Windows:

```
run.bat
```

### Testing

Please use the following links to use the different endpoints (same as in assignment):

| Function                              | HTTP | URL                                                                              |
| ------------------------------------- | ---- | -------------------------------------------------------------------------------- |
| Create a customer                     | POST | /customers?name=Chevy&phone=0701234567                                           |
| Get customer-id via name              | GET  | /customer-id?name=Chevy                                                          |
| List all customers with vehicles      | GET  | /customers                                                                       |
| Create a vehicle                      | POST | /vehicles?registrationNumber=ABC123&brand=Honda&model=Accord&productionYear=2000 |
| List all vehicles                     | GET  | /vehicles                                                                        |
| List all vehicles with specific brand | GET  | /vehicles-by-brand?brand=Honda                                                   |
| Add vehicle to customer               | POST | /addvehicletocustomer?customerId=1&vehicleId=1                                   |
| Update brand                          | PUT  | /vehicles/1/brand?brand=Kia                                                      |
| List registration number and brand    | GET  | /vehicle-brand-info                                                              |
