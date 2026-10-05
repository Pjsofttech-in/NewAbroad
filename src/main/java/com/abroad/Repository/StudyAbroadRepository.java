package com.abroad.Repository;

import com.abroad.Entity.StudyAbroad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudyAbroadRepository extends JpaRepository<StudyAbroad, Long> {

    Optional<StudyAbroad> findByAbroadCountryId(Long countryId);
}