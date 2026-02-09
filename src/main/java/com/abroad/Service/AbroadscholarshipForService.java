package com.abroad.Service;

import com.abroad.Entity.AbroadscholarshipFor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AbroadscholarshipForService {


    AbroadscholarshipFor create(AbroadscholarshipFor abroadscholarshipFor);

    List<AbroadscholarshipFor> getAll();

    AbroadscholarshipFor GetById(Long id);

    AbroadscholarshipFor updateScholarship(AbroadscholarshipFor abroadscholarshipFor, Long id);

    void deleteScholarship (Long id);

}
