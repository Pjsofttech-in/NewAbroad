package com.abroad.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AbroadJobApplicationRequestDTO {

    private Long jobId;

    private String applicantName;

    private String email;

    private String phone;

    private String coverLetter;
}