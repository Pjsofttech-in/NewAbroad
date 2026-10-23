package com.abroad.Controller;

import com.abroad.Entity.AbroadJobType;
import com.abroad.Service.AbroadJobTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://wayabroad.in")
@RequestMapping("/admin/job-settings/job-type")
public class AbroadJobTypeController {

    @Autowired
    private AbroadJobTypeService service;

    @PostMapping
    public ResponseEntity<AbroadJobType> create(
            @RequestBody AbroadJobType jobType,
            @RequestParam String role,
            @RequestParam String email) {

        return ResponseEntity.ok(
                service.create(
                        jobType,
                        role,
                        email
                )
        );
    }

    @GetMapping
    public ResponseEntity<List<AbroadJobType>> getAll() {

        return ResponseEntity.ok(
                service.getAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<AbroadJobType> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.getById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<AbroadJobType> update(
            @PathVariable Long id,
            @RequestBody AbroadJobType jobType,
            @RequestParam String role,
            @RequestParam String email) {

        return ResponseEntity.ok(
                service.update(
                        id,
                        jobType,
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
                "Job Type deleted successfully"
        );
    }
}