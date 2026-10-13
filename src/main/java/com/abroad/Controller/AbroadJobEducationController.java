package com.abroad.Controller;

import com.abroad.Entity.AbroadJobEducation;
import com.abroad.Service.AbroadJobEducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://wayabroad.in")
@RequestMapping("/admin/job-settings/education")
public class AbroadJobEducationController {

    @Autowired
    private AbroadJobEducationService service;

    @PostMapping
    public ResponseEntity<AbroadJobEducation> create(
            @RequestBody AbroadJobEducation education,
            @RequestParam String role,
            @RequestParam String email) {

        return ResponseEntity.ok(
                service.create(
                        education,
                        role,
                        email
                )
        );
    }

    @GetMapping
    public ResponseEntity<List<AbroadJobEducation>> getAll(
            @RequestParam String role,
            @RequestParam String email) {

        return ResponseEntity.ok(
                service.getAll(role, email)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<AbroadJobEducation> getById(
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
    public ResponseEntity<AbroadJobEducation> update(
            @PathVariable Long id,
            @RequestBody AbroadJobEducation education,
            @RequestParam String role,
            @RequestParam String email) {

        return ResponseEntity.ok(
                service.update(
                        id,
                        education,
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
                "Education deleted successfully"
        );
    }
}