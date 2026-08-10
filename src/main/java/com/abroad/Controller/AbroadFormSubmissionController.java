package com.abroad.Controller;

import com.abroad.DTO.AbroadFormSubmissionDTO;
import com.abroad.DTO.AbroadFormSubmissionResponseDTO;
import com.abroad.Enum.FormType;
import com.abroad.Service.AbroadFormSubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/form-submission")
@RequiredArgsConstructor
public class AbroadFormSubmissionController {

    private final AbroadFormSubmissionService formSubmissionService;


    @PostMapping
    public ResponseEntity<AbroadFormSubmissionResponseDTO> saveFormSubmission(
            @RequestBody AbroadFormSubmissionDTO dto) {

        return ResponseEntity.ok(formSubmissionService.saveFormSubmission(dto));
    }


    @GetMapping
    public ResponseEntity<List<AbroadFormSubmissionResponseDTO>> getFormSubmissions(
            @RequestParam(required = false) FormType formType,
            @RequestParam String role,
            @RequestParam String email) {

        if (formType != null) {
            return ResponseEntity.ok(
                    formSubmissionService.getFormSubmissionByType(formType, role, email));
        }

        return ResponseEntity.ok(
                formSubmissionService.getAllFormSubmissions(role, email));
    }


    @GetMapping("/{id}")
    public ResponseEntity<AbroadFormSubmissionResponseDTO> getFormSubmissionById(
            @PathVariable Long id,
            @RequestParam String role,
            @RequestParam String email) {

        return ResponseEntity.ok(
                formSubmissionService.getFormSubmissionById(id, role, email));
    }


    @PutMapping("/{id}")
    public ResponseEntity<AbroadFormSubmissionResponseDTO> updateFormSubmission(
            @PathVariable Long id,
            @RequestBody AbroadFormSubmissionDTO dto,
            @RequestParam String role,
            @RequestParam String email) {

        return ResponseEntity.ok(
                formSubmissionService.updateFormSubmission(id, dto, role, email));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFormSubmission(
            @PathVariable Long id,
            @RequestParam String role,
            @RequestParam String email) {

        formSubmissionService.deleteFormSubmission(id, role, email);

        return ResponseEntity.ok("Form Submission deleted successfully.");
    }
}