package com.abroad.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "abroad_job_role",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name", "sector_id"})
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AbroadJobRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sector_id", nullable = false)
    private AbroadJobSector sector;

    @Column(nullable = false)
    private Boolean active = true;
}