package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadScholarship;
import com.abroad.Repository.AbroadScholarshipRepository;
import com.abroad.Service.AbroadScholarshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AbroadScholarshipServiceImpl implements AbroadScholarshipService {

    @Autowired
    private AbroadScholarshipRepository repository;


    @Override
    public AbroadScholarship saveScholarship(AbroadScholarship scholarship) {
        return repository.save(scholarship);
    }

    @Override
    public List<AbroadScholarship> getAllScholarships() {
        return repository.findAll();
    }

    @Override
    public AbroadScholarship getScholarshipById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Scholarship not found with id: " + id));
    }

    @Override
    public AbroadScholarship updateScholarship(Long id, AbroadScholarship scholarship) {
        AbroadScholarship existing = getScholarshipById(id);

        existing.setSname(scholarship.getSname());
        existing.setCategory(scholarship.getCategory());
        existing.setQualification(scholarship.getQualification());
        existing.setScholarshipFor(scholarship.getScholarshipFor());
        existing.setLink(scholarship.getLink());
        existing.setLogo(scholarship.getLogo());
        existing.setDescription(scholarship.getDescription());
        existing.setScholarshipType(scholarship.getScholarshipType());
        existing.setAmount(scholarship.getAmount());
        existing.setFaq(scholarship.getFaq());
        existing.setTestDate(scholarship.getTestDate());
        existing.setTestResult(scholarship.getTestResult());
        existing.setStudyLocation(scholarship.getStudyLocation());
        existing.setBranchCode(scholarship.getBranchCode());

        return repository.save(existing);
    }

    @Override
    public void deleteScholarship(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<AbroadScholarship> getScholarshipsByBranchCode(String branchCode) {
        return repository.findByBranchCode(branchCode);
    }



}
