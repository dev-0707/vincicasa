package com.tool.vincicasa.estrazioni;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcorsoRepository extends JpaRepository<Concorso, UUID> {

}
