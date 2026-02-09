package com.abroad.Service;

import com.abroad.Entity.AbroadScholarshipcategory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AbroadScholarshipcategoryService {

    AbroadScholarshipcategory createCategory(AbroadScholarshipcategory category);

    List<AbroadScholarshipcategory> getAllCategories();

    AbroadScholarshipcategory getCategoryById(Long id);

    AbroadScholarshipcategory updateCategory(Long id, AbroadScholarshipcategory category);

    void deleteCategory(Long id);


}
