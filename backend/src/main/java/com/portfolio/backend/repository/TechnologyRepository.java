package com.portfolio.backend.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.portfolio.backend.entity.Technology;

@Repository
public interface TechnologyRepository extends JpaRepository<Technology, UUID> {

}
