package com.example.Task_3.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Task_3.Model.Register;

@Repository
public interface RegisterRepository extends JpaRepository<Register, Long> {
    Optional<Register> findByStoreIdAndRegisterId(Long storeId, Long registerId);
}