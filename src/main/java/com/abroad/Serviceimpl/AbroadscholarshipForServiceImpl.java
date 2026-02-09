package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadscholarshipFor;
import com.abroad.Repository.AbroadscholarshipForRepository;
import com.abroad.Service.AbroadscholarshipForService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbroadscholarshipForServiceImpl implements AbroadscholarshipForService {


    private final AbroadscholarshipForRepository repository;

    public AbroadscholarshipForServiceImpl(AbroadscholarshipForRepository repository) {
        this.repository = repository;
    }

    @Override
    public AbroadscholarshipFor create(AbroadscholarshipFor abroadscholarshipFor) {
        return  repository.save(abroadscholarshipFor);
    }

    @Override
    public List<AbroadscholarshipFor> getAll() {
        return repository.findAll();
    }

    @Override
    public AbroadscholarshipFor GetById(Long id) {
        return repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Scholarship not found with id" +id));
    }

    @Override
    public AbroadscholarshipFor updateScholarship(AbroadscholarshipFor abroadscholarshipFor, Long id) {
        AbroadscholarshipFor existing = repository.findById(id)
                .orElseThrow(()-> new RuntimeException("ScholarshipFor not foud with id "+id));
        existing.setName(abroadscholarshipFor.getName());

        return  repository.save(existing);
    }

    @Override
    public void deleteScholarship(Long id) {

        if(!repository.existsById(id))
        {
            throw  new RuntimeException("ScholarshipFor not found by id "+id);
        }
        repository.deleteById(id);

    }
}
