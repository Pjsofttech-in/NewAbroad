package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadstudyLocation;
import com.abroad.Repository.AbroadstudyLocationRepository;
import com.abroad.Service.AbroadstudyLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbroadstudyLocationServiceImpl implements AbroadstudyLocationService {


    @Autowired
    private final AbroadstudyLocationRepository repository;

    public AbroadstudyLocationServiceImpl(AbroadstudyLocationRepository repository) {
        this.repository = repository;
    }


    @Override
    public AbroadstudyLocation createScholarshipLocation(AbroadstudyLocation abroadstudyLocation) {
        return repository.save(abroadstudyLocation);
    }

    @Override
    public List<AbroadstudyLocation> getAllScholarshiplocations() {
        return repository.findAll();
    }

    @Override
    public AbroadstudyLocation GetByIdScholarshiplocations(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ScholarshipLocation not Found by Id " + id));

    }

    @Override
    public AbroadstudyLocation updateScholarshiplocations(Long id, AbroadstudyLocation studyLocation) {
        AbroadstudyLocation existing = repository.findById(id)
                .orElseThrow(()-> new RuntimeException("ScholarshipLocation not Found by id " + id));

        existing.setName(studyLocation.getName());

        return  repository.save(existing);
    }


    @Override
    public void deleteScholarshipLocation(Long id) {

        if(!repository.existsById(id))
        {
            throw new RuntimeException("ScholarshipStudyLocation id deleted Succesfully..!!"+id);
        }

        repository.deleteById(id);

    }
}
