package com.abroad.DTO;


import com.abroad.Enum.FormType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AbroadFormSubmissionResponseDTO {

    private Long id;

    private FormType formType;

    private String name;

    private String email;

    private String phoneNumber;

    private Long continentId;

    private String continentName;

    private Long courseId;

    private String courseName;

    private String status;

    private String createdByEmail;

    private String role;

    private String branchCode;
}
