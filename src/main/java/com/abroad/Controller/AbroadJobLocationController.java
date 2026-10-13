package com.abroad.Controller;

import com.abroad.Entity.AbroadJobLocation;
import com.abroad.Service.AbroadJobLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://wayabroad.in")
@RequestMapping("/admin/job-settings/location")
public class AbroadJobLocationController {

    @Autowired
    private AbroadJobLocationService service;

    @PostMapping
    public ResponseEntity<AbroadJobLocation> create(
            @RequestBody AbroadJobLocation location,
            @RequestParam String role,
            @RequestParam String email) {

        return ResponseEntity.ok(
                service.create(
                        location,
                        role,
                        email
                )
        );
    }

    @GetMapping
    public ResponseEntity<List<AbroadJobLocation>> getAll(
            @RequestParam String role,
            @RequestParam String email) {

        return ResponseEntity.ok(
                service.getAll(role, email)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<AbroadJobLocation> getById(
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
    public ResponseEntity<AbroadJobLocation> update(
            @PathVariable Long id,
            @RequestBody AbroadJobLocation location,
            @RequestParam String role,
            @RequestParam String email) {

        return ResponseEntity.ok(
                service.update(
                        id,
                        location,
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
                "Location deleted successfully"
        );
    }
}