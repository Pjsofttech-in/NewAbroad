package com.abroad.Controller;

import com.abroad.Entity.AbroadScholarshipcategory;
import com.abroad.Service.AbroadScholarshipcategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://wayabroad.in")
public class AbroadScholarshipcategoryController {


    private final AbroadScholarshipcategoryService service;

    public AbroadScholarshipcategoryController(AbroadScholarshipcategoryService service) {
        this.service = service;
    }


    @PostMapping("/Categorycreate")
    public ResponseEntity<AbroadScholarshipcategory> createCategory(
            @RequestBody AbroadScholarshipcategory category) {
        return ResponseEntity.ok(service.createCategory(category));
    }


    @GetMapping("/CategorygetAll")
    public ResponseEntity<List<AbroadScholarshipcategory>> getAllCategories() {
        return ResponseEntity.ok(service.getAllCategories());
    }

    @GetMapping("/CategorygetById/{id}")
    public ResponseEntity<AbroadScholarshipcategory> getCategoryById(
            @PathVariable Long id) {
        return ResponseEntity.ok(service.getCategoryById(id));
    }

    @PutMapping("/Categoryupdate/{id}")
    public ResponseEntity<AbroadScholarshipcategory> updateCategory(
            @PathVariable Long id,
            @RequestBody AbroadScholarshipcategory category) {
        return ResponseEntity.ok(service.updateCategory(id, category));
    }

    @DeleteMapping("/Categorydelete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        service.deleteCategory(id);
        return ResponseEntity.ok("Category deleted successfully");
    }

}
