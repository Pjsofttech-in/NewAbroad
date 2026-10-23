package com.abroad.Service;

import com.abroad.Entity.AbroadJobWorkType;
import java.util.List;

public interface AbroadJobWorkTypeService {

    AbroadJobWorkType create(AbroadJobWorkType workType, String role, String email);

    List<AbroadJobWorkType> getAll();

    AbroadJobWorkType getById(Long id);

    AbroadJobWorkType update(Long id, AbroadJobWorkType workType, String role, String email);

    void delete(Long id, String role, String email);
}