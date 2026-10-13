package com.abroad.Entity;

import com.abroad.Enum.JobApplicationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "abroad_job_application",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_job_application_email_job",
                        columnNames = {"job_id", "email"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AbroadJobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "job_id", nullable = false)
    private AbroadJob job;

    @Column(nullable = false, length = 150)
    private String applicantName;

    @Column(nullable = false, length = 150)
    private String email;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(nullable = false, length = 1000)
    private String resumeUrl;

    @Column(columnDefinition = "TEXT")
    private String coverLetter;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private JobApplicationStatus status = JobApplicationStatus.PENDING;

    @Column(nullable = false)
    private LocalDateTime appliedAt;

    @Column(nullable = false)
    private Boolean active = true;

    @PrePersist
    protected void onCreate() {
        if (appliedAt == null) {
            appliedAt = LocalDateTime.now();
        }

        if (status == null) {
            status = JobApplicationStatus.PENDING;
        }

        if (active == null) {
            active = true;
        }
    }
}