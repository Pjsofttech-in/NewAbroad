package com.abroad.DTO;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

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

    private String skill;
    private Integer openings;

    private Long locationId;
    private String locationName;

    private BigDecimal salary;

    private Long currencyId;
    private String currencyName;

    private Long jobTypeId;
    private String jobTypeName;

    private String description;
    private Boolean active;
}
