package com.abroad.Controller;

import com.abroad.Entity.AbroadJobCurrency;
import com.abroad.Service.AbroadJobCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://wayabroad.in")
@RequestMapping("/admin/job-settings/currency")
public class AbroadJobCurrencyController {

    @Autowired
    private AbroadJobCurrencyService service;

    @PostMapping
    public ResponseEntity<AbroadJobCurrency> create(
            @RequestBody AbroadJobCurrency currency,
            @RequestParam String role,
            @RequestParam String email) {

        return ResponseEntity.ok(
                service.create(
                        currency,
                        role,
                        email
                )
        );
    }

    @GetMapping
    public ResponseEntity<List<AbroadJobCurrency>> getAll(
            @RequestParam String role,
            @RequestParam String email) {

        return ResponseEntity.ok(
                service.getAll(role, email)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<AbroadJobCurrency> getById(
            @PathVariable Long id,
            @RequestParam String role,
            @RequestParam String email) {

        return ResponseEntity.ok(
                service.getById(
                        id,
                        role,
                        email
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<AbroadJobCurrency> update(
            @PathVariable Long id,
            @RequestBody AbroadJobCurrency currency,
            @RequestParam String role,
            @RequestParam String email) {

        return ResponseEntity.ok(
                service.update(
                        id,
                        currency,
                        role,
                        email
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Long id,
            @RequestParam String role,
            @RequestParam String email) {

        service.delete(id, role, email);

        return ResponseEntity.ok(
                "Currency deleted successfully"
        );
    }
}