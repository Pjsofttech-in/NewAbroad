package com.abroad.Service;

import com.abroad.Entity.AbroadJobWorkType;
import java.util.List;

public interface AbroadJobWorkTypeService {

    AbroadJobWorkType create(AbroadJobWorkType workType, String role, String email);

    List<AbroadJobWorkType> getAll(String role, String email);

    AbroadJobWorkType getById(Long id, String role, String email);

    AbroadJobWorkType update(Long id, AbroadJobWorkType workType, String role, String email);

    void delete(Long id, String role, String email);
}