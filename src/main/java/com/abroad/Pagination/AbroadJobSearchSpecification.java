package com.abroad.Pagination;

import com.abroad.Entity.AbroadJob;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.Locale;

public final class AbroadJobSearchSpecification {

    private AbroadJobSearchSpecification() {
    }

    public static Specification<AbroadJob> build(
            String keyword,
            Long educationId,
            Long sectorId,
            Long jobRoleId,
            Long locationId,
            Long jobTypeId,
            Long workTypeId,
            String experienceLevel,
            BigDecimal minSalary,
            BigDecimal maxSalary,
            Boolean active) {

        Specification<AbroadJob> spec =
                (root, query, cb) -> cb.conjunction();

        if (keyword != null && !keyword.isBlank()) {
            String pattern = "%"
                    + keyword.trim().toLowerCase(Locale.ROOT)
                    + "%";

            spec = spec.and((root, query, cb) ->
                    cb.or(
                            cb.like(
                                    cb.lower(root.get("jobTitle")),
                                    pattern
                            ),
                            cb.like(
                                    cb.lower(root.get("skill")),
                                    pattern
                            )
                    )
            );
        }

        if (educationId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("education").get("id"), educationId));
        }

        if (sectorId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("sector").get("id"), sectorId));
        }

        if (jobRoleId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("jobRole").get("id"), jobRoleId));
        }

        if (locationId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("location").get("id"), locationId));
        }

        if (jobTypeId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("jobType").get("id"), jobTypeId));
        }

        if (workTypeId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("workType").get("id"), workTypeId));
        }

        if (experienceLevel != null && !experienceLevel.isBlank()) {
            String level = experienceLevel.trim().toLowerCase(Locale.ROOT);

            spec = spec.and((root, query, cb) ->
                    cb.equal(
                            cb.lower(root.get("experienceLevel")),
                            level
                    ));
        }

        if (minSalary != null) {
            spec = spec.and((root, query, cb) ->
                    cb.greaterThanOrEqualTo(
                            root.get("salary"), minSalary));
        }

        if (maxSalary != null) {
            spec = spec.and((root, query, cb) ->
                    cb.lessThanOrEqualTo(
                            root.get("salary"), maxSalary));
        }

        if (active != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("active"), active));
        }

        return spec;
    }
}