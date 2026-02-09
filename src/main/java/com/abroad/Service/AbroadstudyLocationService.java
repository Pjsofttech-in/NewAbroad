package com.abroad.Service;

import com.abroad.Entity.AbroadstudyLocation;
import org.springframework.stereotype.Service;

import java.util.List;


public interface AbroadstudyLocationService {


    AbroadstudyLocation createScholarshipLocation(AbroadstudyLocation abroadstudyLocation);

    List<AbroadstudyLocation> getAllScholarshiplocations();


    AbroadstudyLocation GetByIdScholarshiplocations(Long id);

    AbroadstudyLocation updateScholarshiplocations(Long id, AbroadstudyLocation  studyLocation);

     void deleteScholarshipLocation(Long id);


}
