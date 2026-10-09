package com.abroad.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "abroad_job_sector",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name", "education_id"})
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AbroadJobSector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "education_id", nullable = false)
    private AbroadJobEducation education;

    @Column(nullable = false)
    private Boolean active = true;
}