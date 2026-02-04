package com.abroad.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "abroad_scholarship_lead")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AbroadScholarshipLead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phoneno;

    @Column(nullable = false)
    private String email;


    @Column(nullable = false)
    private String scholarship;

    private String location;
}
