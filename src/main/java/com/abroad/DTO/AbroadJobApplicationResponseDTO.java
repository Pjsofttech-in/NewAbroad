package com.abroad.DTO;

import com.abroad.Enum.JobApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AbroadJobApplicationResponseDTO {

    private Long id;

    private Long jobId;

    private String jobTitle;

    private String applicantName;

    private String email;

    private String phone;

    private String resumeUrl;

    private String coverLetter;

    private JobApplicationStatus status;

    private LocalDateTime appliedAt;

    private Boolean active;

    private String remark;
}