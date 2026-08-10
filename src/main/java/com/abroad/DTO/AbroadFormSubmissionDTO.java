package com.abroad.DTO;

import com.abroad.Enum.FormType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AbroadFormSubmissionDTO {

    private FormType formType;

    private String name;

    private String email;

    private String phoneNumber;

    private Long continentId;

    private Long courseId;

    private String password;

    private String createdByEmail;

    private String role;

    private String branchCode;

    private String status;
}
