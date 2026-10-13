package com.abroad.Controller;

import com.abroad.DTO.StudyAbroadRequestDTO;
import com.abroad.DTO.StudyAbroadResponseDTO;
import com.abroad.Service.StudyAbroadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://wayabroad.in")
public class StudyAbroadController {

    @Autowired
    private StudyAbroadService service;


    @PostMapping("/createStudyAbroad")
    public ResponseEntity<StudyAbroadResponseDTO> createStudyAbroad(
            @RequestBody StudyAbroadRequestDTO request,
            @RequestParam String role,
            @RequestParam String email) {

        return ResponseEntity.ok(service.createStudyAbroad(request, role, email));
    }


    @GetMapping("/getStudyAbroadByCountry/{countryId}")
    public ResponseEntity<StudyAbroadResponseDTO> getStudyAbroadByCountry(
            @PathVariable Long countryId) {

        return ResponseEntity.ok(service.getStudyAbroadByCountryId(countryId));
    }



    @GetMapping("/getAllStudyAbroad")
    public ResponseEntity<List<StudyAbroadResponseDTO>> getAllStudyAbroad(
            @RequestParam String role,
            @RequestParam String email) {

        return ResponseEntity.ok(service.getAllStudyAbroad(role, email));
    }



    @PutMapping("/updateStudyAbroad/{id}")
    public ResponseEntity<StudyAbroadResponseDTO> updateStudyAbroad(
            @PathVariable Long id,
            @RequestBody StudyAbroadRequestDTO request,
            @RequestParam String role,
            @RequestParam String email) {

        return ResponseEntity.ok(service.updateStudyAbroad(id, request, role, email));
    }



    @DeleteMapping("/deleteStudyAbroad/{id}")
    public ResponseEntity<String> deleteStudyAbroad(
            @PathVariable Long id,
            @RequestParam String role,
            @RequestParam String email) {

        service.deleteStudyAbroad(id, role, email);

        return ResponseEntity.ok(
                "Study Abroad deleted successfully"
        );
    }
}
