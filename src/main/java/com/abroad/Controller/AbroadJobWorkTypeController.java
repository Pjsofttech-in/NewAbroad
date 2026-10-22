package com.abroad.Controller;

import com.abroad.Entity.AbroadJobWorkType;
import com.abroad.Service.AbroadJobWorkTypeService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://wayabroad.in")
@RequestMapping("/admin/job-settings/work-type")
public class AbroadJobWorkTypeController {

    private final AbroadJobWorkTypeService service;

    public AbroadJobWorkTypeController(
            AbroadJobWorkTypeService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AbroadJobWorkType> create(
            @RequestBody AbroadJobWorkType workType,
            @RequestParam String role,
            @RequestParam String email) {

        return ResponseEntity.ok(
                service.create(workType, role, email));
    }

    @GetMapping
    public ResponseEntity<List<AbroadJobWorkType>> getAll(
            @RequestParam String role,
            @RequestParam String email) {

        return ResponseEntity.ok(
                service.getAll(role, email));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AbroadJobWorkType> getById(
            @PathVariable Long id,
            @RequestParam String role,
            @RequestParam String email) {

        return ResponseEntity.ok(
                service.getById(id, role, email));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AbroadJobWorkType> update(
            @PathVariable Long id,
            @RequestBody AbroadJobWorkType workType,
            @RequestParam String role,
            @RequestParam String email) {

        return ResponseEntity.ok(
                service.update(id, workType, role, email));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Long id,
            @RequestParam String role,
            @RequestParam String email) {

        service.delete(id, role, email);

        return ResponseEntity.ok("Work Type deleted successfully");
    }
}