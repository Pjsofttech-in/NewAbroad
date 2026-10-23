package com.abroad.Controller;

import com.abroad.Entity.AbroadJobCountry;
import com.abroad.Service.AbroadJobCountryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/job-settings/country")
@CrossOrigin(origins = "https://wayabroad.in")
public class AbroadJobCountryController {

    private final AbroadJobCountryService countryService;

    public AbroadJobCountryController(
            AbroadJobCountryService countryService) {
        this.countryService = countryService;
    }

    @PostMapping
    public ResponseEntity<AbroadJobCountry> createCountry(
            @RequestBody AbroadJobCountry country,
            @RequestParam String role,
            @RequestParam String email) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(countryService.createCountry(
                        country, role, email));
    }

    @GetMapping
    public ResponseEntity<List<AbroadJobCountry>> getAllCountries() {

        return ResponseEntity.ok(
                countryService.getAllCountries());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AbroadJobCountry> getCountryById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                countryService.getCountryById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AbroadJobCountry> updateCountry(
            @PathVariable Long id,
            @RequestBody AbroadJobCountry country,
            @RequestParam String role,
            @RequestParam String email) {

        return ResponseEntity.ok(
                countryService.updateCountry(
                        id, country, role, email));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCountry(
            @PathVariable Long id,
            @RequestParam String role,
            @RequestParam String email) {

        countryService.deleteCountry(id, role, email);

        return ResponseEntity.ok(
                "Country deleted successfully");
    }
}