package com.abroad.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "abroad_job_work_type",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_abroad_job_work_type_name",
                        columnNames = "name"
                )
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AbroadJobWorkType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private Boolean active = true;
}