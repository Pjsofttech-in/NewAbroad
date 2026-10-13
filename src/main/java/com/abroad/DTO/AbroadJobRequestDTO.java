package com.abroad.DTO;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AbroadJobRequestDTO {

    private String jobTitle;

    private Long educationId;
    private Long sectorId;
    private Long jobRoleId;

    private String skill;
    private Integer openings;

    private Long locationId;
    private BigDecimal salary;
    private Long currencyId;
    private Long jobTypeId;

    private String description;
    private Boolean active = true;
}