package com.abroad.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "abroad_job_country",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_abroad_job_country_name",
                        columnNames = "name"
                )
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AbroadJobCountry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(nullable = false)
    private Boolean active = true;
}

