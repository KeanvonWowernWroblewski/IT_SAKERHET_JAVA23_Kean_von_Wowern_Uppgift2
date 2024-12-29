package com.example.keanuppgift2v4.repository;

import com.example.keanuppgift2v4.model.AccountData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountDataRepository extends JpaRepository<AccountData, Long> {
    AccountData findByEmail(String email);
}
