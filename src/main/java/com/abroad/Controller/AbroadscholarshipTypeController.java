package com.abroad.Controller;

import com.abroad.Entity.AbroadscholarshipType;
import com.abroad.Service.AbroadscholarshipTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://wayabroad.in")
public class AbroadscholarshipTypeController {

    private final AbroadscholarshipTypeService service;

    public AbroadscholarshipTypeController(AbroadscholarshipTypeService service) {
        this.service = service;
    }

    // CREATE
    @PostMapping("/createScholarshipType")
    public ResponseEntity<AbroadscholarshipType> createScholarshipType(
            @RequestBody AbroadscholarshipType abroadscholarshipType) {

        AbroadscholarshipType saved = service.createScholarshipType(abroadscholarshipType);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // GET ALL
    @GetMapping("/getAllScholarshipType")
    public ResponseEntity<List<AbroadscholarshipType>> getAllScholarshipType() {
        return ResponseEntity.ok(service.getAllScholarshipType());
    }

    // GET BY ID
    @GetMapping("/getByIdScholarshipType/{id}")
    public ResponseEntity<AbroadscholarshipType> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.GetByIdSchlarshipType(id));
    }

    // UPDATE
    @PutMapping("/updateScholarshipType/{id}")
    public ResponseEntity<AbroadscholarshipType> updateScholarshipType(
            @PathVariable Long id,
            @RequestBody AbroadscholarshipType scholarshipType) {

        return ResponseEntity.ok(
                service.updateScholarshipType(id, scholarshipType)
        );
    }

    // DELETE
    @DeleteMapping("/deleteScholarshiptype/{id}")
    public ResponseEntity<String> deleteScholarshipType(@PathVariable Long id) {
        service.deleteScholarshipType(id);
        return ResponseEntity.ok("Scholarship Type deleted successfully");
    }
}
