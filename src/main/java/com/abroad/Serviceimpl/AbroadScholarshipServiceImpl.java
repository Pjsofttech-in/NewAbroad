package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadScholarship;
import com.abroad.Repository.AbroadScholarshipRepository;
import com.abroad.Service.AbroadScholarshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AbroadScholarshipServiceImpl implements AbroadScholarshipService {

    private final AbroadScholarshipRepository repository;

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
        existing.setQualification(scholarship.getQualification());
        existing.setScholarshipFor(scholarship.getScholarshipcategory());
        existing.setScholarshipFor(scholarship.getScholarshipFor());
        existing.setScholarshipType(scholarship.getScholarshipType());
        existing.setStudyLocation(scholarship.getStudyLocation());
        existing.setLink(scholarship.getLink());
        existing.setAmount(scholarship.getAmount());
        existing.setBranchCode(scholarship.getBranchCode());

        existing.setTestDate(scholarship.getTestDate());
        existing.setDeadline(scholarship.getDeadline());
        existing.setApplyMonth(scholarship.getApplyMonth());
        existing.setTestResult(scholarship.getTestResult());

        existing.setLogo(scholarship.getLogo());
        existing.setDescription(scholarship.getDescription());
        existing.setFaq(scholarship.getFaq());
        existing.setEligibility(scholarship.getEligibility());
        existing.setSpecialRequirement(scholarship.getSpecialRequirement());
        existing.setBenefits(scholarship.getBenefits());
        existing.setExamDetails(scholarship.getExamDetails());
        existing.setPdf(scholarship.getPdf());

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
