package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadScholarshipLead;
import com.abroad.Repository.AbroadScholarshipLeadRepository;
import com.abroad.Service.AbroadScholarshipLeadService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbroadScholarshipLeadServiceImpl
        implements AbroadScholarshipLeadService {

    private final AbroadScholarshipLeadRepository leadRepository;

    public AbroadScholarshipLeadServiceImpl(
            AbroadScholarshipLeadRepository leadRepository) {
        this.leadRepository = leadRepository;
    }

    @Override
    public AbroadScholarshipLead saveLead(AbroadScholarshipLead lead) {
        return leadRepository.save(lead);
    }

    @Override
    public List<AbroadScholarshipLead> getAllLeads() {
        return leadRepository.findAll();
    }

    @Override
    public AbroadScholarshipLead getLeadById(Long id) {
        return leadRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Lead not found with id: " + id));
    }

    @Override
    public List<AbroadScholarshipLead> getLeadsByScholarship(String scholarship) {
        return leadRepository.findByScholarship(scholarship);
    }

    @Override
    public AbroadScholarshipLead updateLead(Long id,
                                            AbroadScholarshipLead updatedLead) {

        AbroadScholarshipLead existingLead = getLeadById(id);

        existingLead.setName(updatedLead.getName());
        existingLead.setPhoneno(updatedLead.getPhoneno());
        existingLead.setEmail(updatedLead.getEmail());
        existingLead.setScholarship(updatedLead.getScholarship());
        existingLead.setLocation(updatedLead.getLocation());

        return leadRepository.save(existingLead);
    }

    @Override
    public void deleteLead(Long id) {
        if (!leadRepository.existsById(id)) {
            throw new RuntimeException("Lead not found with id: " + id);
        }
        leadRepository.deleteById(id);
    }
}
