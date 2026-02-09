package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadscholarshipType;
import com.abroad.Repository.AbroadscholarshipTypeRepository;
import com.abroad.Service.AbroadscholarshipTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbroadscholarshipTypeServiceImpl implements AbroadscholarshipTypeService {


    @Autowired
    private  final AbroadscholarshipTypeRepository repository;

    public AbroadscholarshipTypeServiceImpl(AbroadscholarshipTypeRepository repository) {
        this.repository = repository;
    }



    @Override
    public AbroadscholarshipType createScholarshipType(AbroadscholarshipType abroadscholarshipType) {
        return repository.save(abroadscholarshipType);
    }

    @Override
    public List<AbroadscholarshipType> getAllScholarshipType() {
        return repository.findAll();
    }

    @Override
    public AbroadscholarshipType GetByIdSchlarshipType(Long id) {
        return repository.findById(id)
                .orElseThrow(()-> new RuntimeException("ScholarshipType not found by Id "+id));
    }

    @Override
    public AbroadscholarshipType updateScholarshipType(Long id, AbroadscholarshipType scholarshipType) {
        AbroadscholarshipType existing = repository.findById(id)
                .orElseThrow(()-> new RuntimeException("ScholarshipType not Found By id "+id));


        existing.setName(scholarshipType.getName());
        return  repository.save(existing);
    }

    @Override
    public void deleteScholarshipType(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Scholarshiptype not found by id..!!" + id);
        }
        repository.deleteById(id);

    }
}
