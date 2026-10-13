package com.abroad.Controller;

import com.abroad.DTO.AbroadJobRoleResponseDTO;
import com.abroad.Entity.AbroadJobRole;
import com.abroad.Service.AbroadJobRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://wayabroad.in")
@RequestMapping("/admin/job-settings/role")
public class AbroadJobRoleController {

    @Autowired
    private AbroadJobRoleService service;

    @PostMapping
    public ResponseEntity<AbroadJobRoleResponseDTO> create(
            @RequestBody AbroadJobRole jobRole,
            @RequestParam Long sectorId,
            @RequestParam String role,
            @RequestParam String email) {

        return ResponseEntity.ok(
                service.create(
                        jobRole,
                        sectorId,
                        role,
                        email
                )
        );
    }

    @GetMapping
    public ResponseEntity<List<AbroadJobRoleResponseDTO>> getAll(
            @RequestParam String role,
            @RequestParam String email) {

        return ResponseEntity.ok(
                service.getAll(role, email)
        );
    }

    @GetMapping("/by-sector/{sectorId}")
    public ResponseEntity<List<AbroadJobRoleResponseDTO>> getBySector(
            @PathVariable Long sectorId,
            @RequestParam String role,
            @RequestParam String email) {

        return ResponseEntity.ok(
                service.getBySector(
                        sectorId,
                        role,
                        email
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<AbroadJobRoleResponseDTO> getById(
            @PathVariable Long id,
            @RequestParam String role,
            @RequestParam String email) {

        return ResponseEntity.ok(
                service.getById(
                        id,
                        role,
                        email
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<AbroadJobRoleResponseDTO> update(
            @PathVariable Long id,
            @RequestBody AbroadJobRole jobRole,
            @RequestParam Long sectorId,
            @RequestParam String role,
            @RequestParam String email) {

        return ResponseEntity.ok(
                service.update(
                        id,
                        jobRole,
                        sectorId,
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
                "Role deleted successfully"
        );
    }
}