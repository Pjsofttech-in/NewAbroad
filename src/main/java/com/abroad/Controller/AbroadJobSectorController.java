package com.abroad.Controller;

import com.abroad.Entity.AbroadJobSector;
import com.abroad.Service.AbroadJobSectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://wayabroad.in")
@RequestMapping("/admin/job-settings/sector")
public class AbroadJobSectorController {

    @Autowired
    private AbroadJobSectorService service;

    @PostMapping
    public ResponseEntity<AbroadJobSector> create(
            @RequestBody AbroadJobSector sector,
            @RequestParam Long educationId,
            @RequestParam String role,
            @RequestParam String email) {

        return ResponseEntity.ok(
                service.create(
                        sector,
                        educationId,
                        role,
                        email
                )
        );
    }

    @GetMapping
    public ResponseEntity<List<AbroadJobSector>> getAll(
            @RequestParam String role,
            @RequestParam String email) {

        return ResponseEntity.ok(
                service.getAll(role, email)
        );
    }

    @GetMapping("/by-education/{educationId}")
    public ResponseEntity<List<AbroadJobSector>> getByEducation(
            @PathVariable Long educationId,
            @RequestParam String role,
            @RequestParam String email) {

        return ResponseEntity.ok(
                service.getByEducation(
                        educationId,
                        role,
                        email
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<AbroadJobSector> getById(
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
    public ResponseEntity<AbroadJobSector> update(
            @PathVariable Long id,
            @RequestBody AbroadJobSector sector,
            @RequestParam Long educationId,
            @RequestParam String role,
            @RequestParam String email) {

        return ResponseEntity.ok(
                service.update(
                        id,
                        sector,
                        educationId,
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
                "Sector deleted successfully"
        );
    }
}