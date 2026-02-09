package com.abroad.Controller;


import com.abroad.Entity.AbroadstudyLocation;
import com.abroad.Service.AbroadstudyLocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://wayabroad.in")
public class AbroadstudyLocationController {

    private final AbroadstudyLocationService service;

    public AbroadstudyLocationController(AbroadstudyLocationService service) {
        this.service = service;
    }

    // CREATE
    @PostMapping("/createScholarshipStudyLocation")
    public ResponseEntity<AbroadstudyLocation> createStudyLocation(
            @RequestBody AbroadstudyLocation abroadstudyLocation) {

        AbroadstudyLocation saved = service.createScholarshipLocation(abroadstudyLocation);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // GET ALL
    @GetMapping("/getAllScholarshipStudyLocation")
    public ResponseEntity<List<AbroadstudyLocation>> getAllStudyLocations() {
        return ResponseEntity.ok(service.getAllScholarshiplocations());
    }

    // GET BY ID
    @GetMapping("/ScholarshipStudyLocation/{id}")
    public ResponseEntity<AbroadstudyLocation> getStudyLocationById(
            @PathVariable Long id) {

        return ResponseEntity.ok(service.GetByIdScholarshiplocations(id));
    }

    // UPDATE
    @PutMapping("/ScholarshipStudyLocation/{id}")
    public ResponseEntity<AbroadstudyLocation> updateStudyLocation(
            @PathVariable Long id,
            @RequestBody AbroadstudyLocation studyLocation) {

        return ResponseEntity.ok(
                service.updateScholarshiplocations(id, studyLocation)
        );
    }

    // DELETE
    @DeleteMapping("/ScholarshipStudyLocation/{id}")
    public ResponseEntity<String> deleteStudyLocation(@PathVariable Long id) {
        service.deleteScholarshipLocation(id);
        return ResponseEntity.ok("Study Location deleted successfully");
    }
}
