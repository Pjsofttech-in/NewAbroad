
package com.abroad.Repository;

import com.abroad.Entity.AbroadJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AbroadJobRepository
        extends JpaRepository<AbroadJob, Long>,
        JpaSpecificationExecutor<AbroadJob> {
}