package com.example.keanuppgift2v4.repository;

import com.example.keanuppgift2v4.model.SecureContainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SecureContainerRepository extends JpaRepository<SecureContainer, Long> {
    List<SecureContainer> findByOwnerId(Long ownerId);
}
