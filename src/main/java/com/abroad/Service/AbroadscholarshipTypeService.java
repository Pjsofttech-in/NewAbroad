package com.abroad.Service;

import com.abroad.Entity.AbroadscholarshipType;
import org.springframework.stereotype.Service;

import java.util.List;


public interface AbroadscholarshipTypeService {


    AbroadscholarshipType createScholarshipType(AbroadscholarshipType abroadscholarshipType);

    List<AbroadscholarshipType> getAllScholarshipType();

    AbroadscholarshipType GetByIdSchlarshipType(Long id);

    AbroadscholarshipType updateScholarshipType(Long id, AbroadscholarshipType scholarshipType);

    void deleteScholarshipType(Long id);
}
