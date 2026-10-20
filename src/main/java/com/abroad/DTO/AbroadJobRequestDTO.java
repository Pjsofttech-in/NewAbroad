package com.abroad.DTO;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class AbroadJobRequestDTO {

    private String jobTitle;

    private Long educationId;
    private Long sectorId;
    private Long jobRoleId;

    private List<String> skills;

    private Integer openings;

    private Long locationId;

    private BigDecimal salary;

    private Long currencyId;
    private Long jobTypeId;

    private Long workTypeId;
    private String experienceLevel;

    private String description;

    private Boolean active = true;
}