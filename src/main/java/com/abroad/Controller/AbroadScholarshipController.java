package com.abroad.Controller;


import com.abroad.Entity.AbroadScholarship;
import com.abroad.Service.AbroadScholarshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://wayabroad.in")
public class AbroadScholarshipController {


    private final AbroadScholarshipService service;

    public AbroadScholarshipController(AbroadScholarshipService service) {
        this.service = service;
    }

    @PostMapping("/createScholarship")
    public ResponseEntity<AbroadScholarship> addScholarship(
            @RequestBody AbroadScholarship scholarship) {
        return ResponseEntity.ok(service.saveScholarship(scholarship));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<AbroadScholarship>> getAllScholarships() {
        return ResponseEntity.ok(service.getAllScholarships());
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<AbroadScholarship> getScholarshipById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getScholarshipById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AbroadScholarship> updateScholarship(
            @PathVariable Long id,
            @RequestBody AbroadScholarship scholarship) {
        return ResponseEntity.ok(service.updateScholarship(id, scholarship));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteScholarship(@PathVariable Long id) {
        service.deleteScholarship(id);
        return ResponseEntity.ok("Scholarship deleted successfully");
    }

    @GetMapping("/branch/{branchCode}")
    public ResponseEntity<List<AbroadScholarship>> getByBranchCode(
            @PathVariable String branchCode) {
        return ResponseEntity.ok(service.getScholarshipsByBranchCode(branchCode));
    }
}
