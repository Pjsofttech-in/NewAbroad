package com.abroad.Controller;

import com.abroad.Entity.AbroadScholarshipLead;
import com.abroad.Service.AbroadScholarshipLeadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leads")
@CrossOrigin(origins = "https://wayabroad.in")
public class AbroadScholarshipLeadController {

    private final AbroadScholarshipLeadService leadService;

    public AbroadScholarshipLeadController(AbroadScholarshipLeadService leadService) {
        this.leadService = leadService;
    }


    @PostMapping("/create")
    public ResponseEntity<AbroadScholarshipLead> createLead(
            @RequestBody AbroadScholarshipLead lead) {

        return new ResponseEntity<>(
                leadService.saveLead(lead),
                HttpStatus.CREATED
        );
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<AbroadScholarshipLead>> getAllLeads() {
        return ResponseEntity.ok(leadService.getAllLeads());
    }


    @GetMapping("/getById/{id}")
    public ResponseEntity<AbroadScholarshipLead> getLeadById(
            @PathVariable Long id) {

        return ResponseEntity.ok(leadService.getLeadById(id));
    }


    @GetMapping("/scholarship/{scholarship}")
    public ResponseEntity<List<AbroadScholarshipLead>> getLeadsByScholarship(
            @PathVariable String scholarship) {

        return ResponseEntity.ok(
                leadService.getLeadsByScholarship(scholarship)
        );
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<AbroadScholarshipLead> updateLead(
            @PathVariable Long id,
            @RequestBody AbroadScholarshipLead lead) {

        return ResponseEntity.ok(
                leadService.updateLead(id, lead)
        );
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteLead(@PathVariable Long id) {
        leadService.deleteLead(id);
        return ResponseEntity.ok("Scholarship lead deleted successfully");
    }
}
