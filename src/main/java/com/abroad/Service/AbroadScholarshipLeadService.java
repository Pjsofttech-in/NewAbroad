package com.abroad.Service;

import com.abroad.Entity.AbroadScholarshipLead;
import com.abroad.Repository.AbroadScholarshipLeadRepository;
import com.abroad.Repository.AbroadScholarshipRepository;

import java.util.List;

public interface AbroadScholarshipLeadService {


    AbroadScholarshipLead saveLead(AbroadScholarshipLead lead);

    List<AbroadScholarshipLead> getAllLeads();

    AbroadScholarshipLead getLeadById(Long id);

    List<AbroadScholarshipLead> getLeadsByScholarship(String scholarship);

    AbroadScholarshipLead updateLead(Long id, AbroadScholarshipLead lead);

    void deleteLead(Long id);

}
