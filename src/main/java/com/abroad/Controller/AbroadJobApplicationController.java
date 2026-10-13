
package com.abroad.Controller;

import com.abroad.DTO.AbroadJobApplicationRequestDTO;
import com.abroad.DTO.AbroadJobApplicationResponseDTO;
import com.abroad.Enum.JobApplicationStatus;
import com.abroad.Service.AbroadJobApplicationService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/job-applications")
@CrossOrigin(origins = "https://wayabroad.in")
public class AbroadJobApplicationController {

    private final AbroadJobApplicationService jobApplicationService;

    public AbroadJobApplicationController(
            AbroadJobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    // 1. Submit a job application with resume
    @PostMapping(
            value = "/apply",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<AbroadJobApplicationResponseDTO> createApplication(
            @Valid @RequestPart("application")
            AbroadJobApplicationRequestDTO request,

            @RequestPart("resume")
            MultipartFile resume) {

        AbroadJobApplicationResponseDTO response =
                jobApplicationService.createApplication(request, resume);

        return ResponseEntity.ok(response);
    }

    // 2. Get all applications with pagination
    @GetMapping("/admin")
    public ResponseEntity<Page<AbroadJobApplicationResponseDTO>> getAllApplications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam String role,
            @RequestParam String email) {

        Page<AbroadJobApplicationResponseDTO> response =
                jobApplicationService.getAllApplications(
                        page, size, role, email);

        return ResponseEntity.ok(response);
    }

    // 3. Get application by ID
    @GetMapping("/admin/{id}")
    public ResponseEntity<AbroadJobApplicationResponseDTO> getApplicationById(
            @PathVariable Long id,
            @RequestParam String role,
            @RequestParam String email) {

        AbroadJobApplicationResponseDTO response =
                jobApplicationService.getApplicationById(id, role, email);

        return ResponseEntity.ok(response);
    }

    // 4. Update application status
    @PutMapping("/admin/{id}/status")
    public ResponseEntity<AbroadJobApplicationResponseDTO> updateStatus(
            @PathVariable Long id,
            @RequestParam JobApplicationStatus status,
            @RequestParam String role,
            @RequestParam String email) {

        AbroadJobApplicationResponseDTO response =
                jobApplicationService.updateStatus(
                        id, status, role, email);

        return ResponseEntity.ok(response);
    }

    // 5. Soft-delete (deactivate) an application
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<String> deactivateApplication(
            @PathVariable Long id,
            @RequestParam String role,
            @RequestParam String email) {

        jobApplicationService.deactivateApplication(id, role, email);

        return ResponseEntity.ok(
                "Job application deactivated successfully");
    }


    @GetMapping("/admin/search")
    public ResponseEntity<Page<AbroadJobApplicationResponseDTO>> searchApplications(
            @RequestParam(required = false) Long jobId,
            @RequestParam(required = false) JobApplicationStatus status,
            @RequestParam(required = false) String applicantEmail,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam String role,
            @RequestParam String email) {

        return ResponseEntity.ok(
                jobApplicationService.searchApplications(
                        jobId, status, applicantEmail,
                        page, size, role, email));
    }
}