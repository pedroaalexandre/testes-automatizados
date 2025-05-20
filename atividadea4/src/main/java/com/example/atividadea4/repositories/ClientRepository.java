package com.example.atividadea4.repositories;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.atividadea4.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByNameContainingIgnoreCase(String name);
    List<Client> findByIncomeGreaterThan(Double income);
    List<Client> findByIncomeLessThan(Double income);
    List<Client> findByIncomeBetween(Double min, Double max);
    List<Client> findClientBybirthDateBetween(Instant DataInicio, Instant DataTermino);
    Optional<Client> findById(Long id);
}