package com.abroad.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "study_abroad")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudyAbroad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String subTitle;

    @Column(columnDefinition = "LONGTEXT")
    private String description;

    private String image;

    private String imageAltText;

    private String slug;

    private Boolean active;

    @Email
    private String createdByEmail;

    private String role;

    @ManyToOne
    @JoinColumn(name = "country_id")
    @JsonIgnore
    private AbroadCountry abroadCountry;

    @OneToMany(
            mappedBy = "studyAbroad",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<StudyAbroadContent> contents;
}