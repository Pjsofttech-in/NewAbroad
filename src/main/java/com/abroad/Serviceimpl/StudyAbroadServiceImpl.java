package com.abroad.Serviceimpl;

import com.abroad.DTO.StudyAbroadContentDTO;
import com.abroad.DTO.StudyAbroadRequestDTO;
import com.abroad.DTO.StudyAbroadResponseDTO;
import com.abroad.Entity.AbroadCountry;
import com.abroad.Entity.StudyAbroad;
import com.abroad.Entity.StudyAbroadContent;
import com.abroad.Repository.CountryRepository;
import com.abroad.Repository.StudyAbroadRepository;
import com.abroad.Service.StudyAbroadService;
import com.abroad.Service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudyAbroadServiceImpl implements StudyAbroadService {

    @Autowired
    private StudyAbroadRepository studyAbroadRepository;

    @Autowired
    private CountryRepository abroadCountryRepository;

    @Autowired
    private PermissionService permissionService;


    // =========================
    // CREATE
    // =========================

    @Override
    public StudyAbroadResponseDTO createStudyAbroad(
            StudyAbroadRequestDTO request,
            String role,
            String email) {

        // Permission check
        if (!permissionService.hasPermission(role, email, "POST")) {
            throw new AccessDeniedException(
                    "No permission to create Study Abroad"
            );
        }

        // Validate country
        AbroadCountry country = abroadCountryRepository
                .findById(request.getCountryId())
                .orElseThrow(() ->
                        new RuntimeException("Country not found with id: "
                                + request.getCountryId())
                );

        // Check whether Study Abroad already exists for this country
        if (studyAbroadRepository
                .findByAbroadCountryId(request.getCountryId())
                .isPresent()) {

            throw new RuntimeException(
                    "Study Abroad already exists for this country"
            );
        }

        // Create Study Abroad
        StudyAbroad studyAbroad = new StudyAbroad();

        studyAbroad.setTitle(request.getTitle());
        studyAbroad.setSubTitle(request.getSubTitle());
        studyAbroad.setDescription(request.getDescription());
        studyAbroad.setImage(request.getImage());
        studyAbroad.setImageAltText(request.getImageAltText());
        studyAbroad.setSlug(request.getSlug());
        studyAbroad.setActive(request.getActive());
        studyAbroad.setCreatedByEmail(email);
        studyAbroad.setRole(role);

        studyAbroad.setAbroadCountry(country);

        // Create content list
        List<StudyAbroadContent> contentList = new ArrayList<>();

        if (request.getContents() != null) {

            for (StudyAbroadContentDTO contentDTO
                    : request.getContents()) {

                StudyAbroadContent content =
                        new StudyAbroadContent();

                content.setTitle(contentDTO.getTitle());
                content.setContent(contentDTO.getContent());
                content.setDisplayOrder(
                        contentDTO.getDisplayOrder()
                );

                // Important relationship
                content.setStudyAbroad(studyAbroad);

                contentList.add(content);
            }
        }

        studyAbroad.setContents(contentList);

        // Save
        StudyAbroad savedStudyAbroad =
                studyAbroadRepository.save(studyAbroad);

        return mapToResponse(savedStudyAbroad);
    }



    @Override
    public StudyAbroadResponseDTO getStudyAbroadByCountryId(
            Long countryId) {


        StudyAbroad studyAbroad =
                studyAbroadRepository
                        .findByAbroadCountryId(countryId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Study Abroad not found for country id: "
                                                + countryId
                                )
                        );

        return mapToResponse(studyAbroad);
    }


    // =========================
    // GET ALL
    // =========================

    @Override
    public List<StudyAbroadResponseDTO> getAllStudyAbroad(
            String role,
            String email) {

        // Permission check
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException(
                    "No permission to view Study Abroad"
            );
        }

        List<StudyAbroad> studyAbroadList =
                studyAbroadRepository.findAll();

        return studyAbroadList.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }



    @Override
    public StudyAbroadResponseDTO updateStudyAbroad(
            Long id,
            StudyAbroadRequestDTO request,
            String role,
            String email) {

        // Permission check
        if (!permissionService.hasPermission(role, email, "PUT")) {
            throw new AccessDeniedException(
                    "No permission to update Study Abroad"
            );
        }

        // Find existing Study Abroad
        StudyAbroad studyAbroad =
                studyAbroadRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Study Abroad not found with id: "
                                                + id
                                )
                        );

        // Validate country
        AbroadCountry country =
                abroadCountryRepository
                        .findById(request.getCountryId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Country not found with id: "
                                                + request.getCountryId()
                                )
                        );

        // Check if country is being changed
        if (!studyAbroad
                .getAbroadCountry()
                .getId()
                .equals(request.getCountryId())) {

            if (studyAbroadRepository
                    .findByAbroadCountryId(request.getCountryId())
                    .isPresent()) {

                throw new RuntimeException(
                        "Study Abroad already exists for this country"
                );
            }
        }

        // Update main fields
        studyAbroad.setTitle(request.getTitle());
        studyAbroad.setSubTitle(request.getSubTitle());
        studyAbroad.setDescription(request.getDescription());
        studyAbroad.setImage(request.getImage());
        studyAbroad.setImageAltText(request.getImageAltText());
        studyAbroad.setSlug(request.getSlug());
        studyAbroad.setActive(request.getActive());

        studyAbroad.setAbroadCountry(country);

        studyAbroad.getContents().clear();

        if (request.getContents() != null) {

            for (StudyAbroadContentDTO contentDTO
                    : request.getContents()) {

                StudyAbroadContent content =
                        new StudyAbroadContent();

                content.setTitle(contentDTO.getTitle());
                content.setContent(contentDTO.getContent());
                content.setDisplayOrder(
                        contentDTO.getDisplayOrder()
                );

                content.setStudyAbroad(studyAbroad);

                studyAbroad.getContents().add(content);
            }
        }

        // Save updated data
        StudyAbroad updatedStudyAbroad =
                studyAbroadRepository.save(studyAbroad);

        return mapToResponse(updatedStudyAbroad);
    }


    // =========================
    // DELETE
    // =========================

    @Override
    public void deleteStudyAbroad(
            Long id,
            String role,
            String email) {

        // Permission check
        if (!permissionService.hasPermission(role, email, "DELETE")) {
            throw new AccessDeniedException(
                    "No permission to delete Study Abroad"
            );
        }

        StudyAbroad studyAbroad =
                studyAbroadRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Study Abroad not found with id: "
                                                + id
                                )
                        );

        studyAbroadRepository.delete(studyAbroad);
    }


    // =========================
    // ENTITY → RESPONSE DTO
    // =========================

    private StudyAbroadResponseDTO mapToResponse(
            StudyAbroad studyAbroad) {

        StudyAbroadResponseDTO response =
                new StudyAbroadResponseDTO();

        response.setId(studyAbroad.getId());

        // Country information
        if (studyAbroad.getAbroadCountry() != null) {

            response.setCountryId(
                    studyAbroad.getAbroadCountry().getId()
            );

            response.setCountry(
                    studyAbroad.getAbroadCountry().getCountry()
            );
        }

        // Main fields
        response.setTitle(studyAbroad.getTitle());
        response.setSubTitle(studyAbroad.getSubTitle());
        response.setDescription(studyAbroad.getDescription());
        response.setImage(studyAbroad.getImage());
        response.setImageAltText(studyAbroad.getImageAltText());
        response.setSlug(studyAbroad.getSlug());
        response.setActive(studyAbroad.getActive());


        // Content
        List<StudyAbroadContentDTO> contentDTOList =
                new ArrayList<>();

        if (studyAbroad.getContents() != null) {

            contentDTOList =
                    studyAbroad.getContents()
                            .stream()
                            .sorted(
                                    Comparator.comparing(
                                            StudyAbroadContent::getDisplayOrder,
                                            Comparator.nullsLast(
                                                    Integer::compareTo
                                            )
                                    )
                            )
                            .map(content -> {

                                StudyAbroadContentDTO dto =
                                        new StudyAbroadContentDTO();

                                dto.setId(content.getId());
                                dto.setTitle(content.getTitle());
                                dto.setContent(content.getContent());
                                dto.setDisplayOrder(
                                        content.getDisplayOrder()
                                );

                                return dto;
                            })
                            .collect(Collectors.toList());
        }

        response.setContents(contentDTOList);

        return response;
    }
}