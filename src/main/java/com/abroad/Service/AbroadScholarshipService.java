package com.abroad.Service;

import com.abroad.Entity.AbroadScholarship;

import java.util.List;


public interface AbroadScholarshipService {


    AbroadScholarship saveScholarship(AbroadScholarship scholarship);

    List<AbroadScholarship> getAllScholarships();

    AbroadScholarship getScholarshipById(Long id);

    AbroadScholarship updateScholarship(Long id, AbroadScholarship scholarship);

    void deleteScholarship(Long id);

    List<AbroadScholarship> getScholarshipsByBranchCode(String branchCode);
}
