package com.abroad.Entity;

import com.abroad.Enum.FormType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "abroad_form_submission")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AbroadFormSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FormType formType;

    private String name;

    private String email;

    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "continent_id")
    @JsonIgnore
    private AbroadContinent abroadContinent;

    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonIgnore
    private AbroadCourse abroadCourse;

    // Used only for SIGNUP
    private String password;

    private String createdByEmail;

    private String role;

    private String branchCode;

    private String status;
}