package com.example.demo.domain.distribution.repository;

import com.example.demo.domain.distribution.entity.DisItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DisItemJpaRepository extends JpaRepository<DisItem, Long> {

    Optional<DisItem> findById(Long id);

}
