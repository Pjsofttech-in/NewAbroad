package com.abroad.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AbroadScholarship {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sname;

    private String category;

    private String qualification;

    private String scholarshipFor;

    private String link;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String logo;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String description;

    private String scholarshipType;

    private Long amount;

    @Lob
    private String faq;


    private String testDate; // chenged

    private String testResult;

    private String studyLocation;

    private String branchCode;



}
