
package com.abroad.Controller;

import com.abroad.DTO.AbroadJobRequestDTO;
import com.abroad.DTO.AbroadJobResponseDTO;
import com.abroad.Service.AbroadJobService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import java.math.BigDecimal;

import java.util.List;

@RestController
@RequestMapping("/admin/jobs")
@CrossOrigin(origins = "https://wayabroad.in")
public class AbroadJobController {

    private final AbroadJobService jobService;

    public AbroadJobController(AbroadJobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public ResponseEntity<AbroadJobResponseDTO> createJob(
            @RequestBody AbroadJobRequestDTO request,
            @RequestParam String role,
            @RequestParam String email) {

        AbroadJobResponseDTO response =
                jobService.createJob(request, role, email);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<AbroadJobResponseDTO>> getAllJobs(
            @RequestParam String role,
            @RequestParam String email) {

        return ResponseEntity.ok(
                jobService.getAllJobs(role, email)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<AbroadJobResponseDTO> getJobById(
            @PathVariable Long id,
            @RequestParam String role,
            @RequestParam String email) {

        return ResponseEntity.ok(
                jobService.getJobById(id, role, email)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<AbroadJobResponseDTO> updateJob(
            @PathVariable Long id,
            @RequestBody AbroadJobRequestDTO request,
            @RequestParam String role,
            @RequestParam String email) {

        return ResponseEntity.ok(
                jobService.updateJob(id, request, role, email)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(
            @PathVariable Long id,
            @RequestParam String role,
            @RequestParam String email) {

        jobService.deleteJob(id, role, email);

        return ResponseEntity.ok("Job deleted successfully");
    }


    @GetMapping("/search")
    public ResponseEntity<Page<AbroadJobResponseDTO>> searchJobs(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long educationId,
            @RequestParam(required = false) Long sectorId,
            @RequestParam(required = false) Long jobRoleId,
            @RequestParam(required = false) Long locationId,
            @RequestParam(required = false) Long jobTypeId,
            @RequestParam(required = false) BigDecimal minSalary,
            @RequestParam(required = false) BigDecimal maxSalary,
            @RequestParam(required = false) Boolean active,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDir,
            @RequestParam String role,
            @RequestParam String email) {

        return ResponseEntity.ok(
                jobService.searchJobs(
                        keyword,
                        educationId,
                        sectorId,
                        jobRoleId,
                        locationId,
                        jobTypeId,
                        minSalary,
                        maxSalary,
                        active,
                        page,
                        size,
                        sortBy,
                        sortDir,
                        role,
                        email
                )
        );
    }
}