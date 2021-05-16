package hu.bme.aut.freelancer_spring.controller;

import hu.bme.aut.freelancer_spring.dto.VehicleDto;
import hu.bme.aut.freelancer_spring.model.Transfer;
import hu.bme.aut.freelancer_spring.model.Vehicle;
import hu.bme.aut.freelancer_spring.service.VehicleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@AllArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @GetMapping
    public ResponseEntity<List<Vehicle>> findAll() {
        return ResponseEntity.ok(vehicleService.findAll());
    }

    @PostMapping
    public ResponseEntity<Long> save(@RequestBody VehicleDto vehicleDto) {
       return ResponseEntity.ok(vehicleService.save(vehicleDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return ResponseEntity.ok(vehicleService.delete(id));
    }

    @GetMapping("/transfers/{id}")
    public ResponseEntity<List<Transfer>> getTransfers(@PathVariable Long id) {
        return ResponseEntity.ok(vehicleService.getTransfers(id));
    }
}
