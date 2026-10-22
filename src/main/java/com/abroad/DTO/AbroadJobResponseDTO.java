package com.abroad.DTO;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class AbroadJobResponseDTO {

    private Long id;
    private String jobTitle;

    private Long educationId;
    private String educationName;

    private Long sectorId;
    private String sectorName;

    private Long jobRoleId;
    private String jobRoleName;

    private List<String> skills;

    private Integer openings;

    private Long locationId;
    private String locationName;

    private Long countryId;
    private String countryName;

    private BigDecimal salary;

    private Long currencyId;
    private String currencyName;

    private Long jobTypeId;
    private String jobTypeName;

    private Long workTypeId;
    private String workTypeName;

    private String experienceLevel;

    private String description;
    private Boolean active;
}