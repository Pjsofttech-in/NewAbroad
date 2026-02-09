package com.abroad.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "abroad_scholarship")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AbroadScholarship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Basic info
    private String sname;
    private String qualification;
    private String link;
    private Long amount;
    private String branchCode;

    // Dynamic fields (can be converted to @ManyToOne later)
    private String scholarshipcategory;
    private String scholarshipFor;
    private String scholarshipType;
    private String studyLocation;

    // Dates (correct types)
    private String testDate;
    private LocalDate deadline;
    private String applyMonth;

    // Results
    private String testResult;

    // Long text fields
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String logo;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String description;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String faq;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String eligibility;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String specialRequirement;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String benefits;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String examDetails;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String pdf;
}
