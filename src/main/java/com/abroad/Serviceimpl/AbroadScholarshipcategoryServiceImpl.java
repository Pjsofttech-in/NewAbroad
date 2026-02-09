package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadScholarshipcategory;
import com.abroad.Repository.AbroadScholarshipcategoryRepository;
import com.abroad.Service.AbroadScholarshipcategoryService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AbroadScholarshipcategoryServiceImpl implements AbroadScholarshipcategoryService {

    private final AbroadScholarshipcategoryRepository repository;

    public AbroadScholarshipcategoryServiceImpl(AbroadScholarshipcategoryRepository repository) {
        this.repository = repository;
    }


    @Override
    public AbroadScholarshipcategory createCategory(AbroadScholarshipcategory category) {
        if (repository.existsByName(category.getName())) {
            throw new RuntimeException("Category already exists");
        }
        return repository.save(category);
    }

    @Override
    public List<AbroadScholarshipcategory> getAllCategories() {
        return repository.findAll();
    }

    @Override
    public AbroadScholarshipcategory getCategoryById(Long id) {
        return repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Category not Found..!!"));
    }

    @Override
    public AbroadScholarshipcategory updateCategory(Long id, AbroadScholarshipcategory category) {
        AbroadScholarshipcategory  existing = getCategoryById(id);
        existing.setName(category.getName());

        return repository.save(existing);

    }

    @Override
    public void deleteCategory(Long id) {

        repository.deleteById(id);

    }
}
