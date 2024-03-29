package fr.norsys.Formation.repository;

import fr.norsys.Formation.entity.Formation;
import fr.norsys.Formation.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    @Query("SELECT p FROM Participant p WHERE LOWER(p.name) LIKE %:name%")
    List<Participant> filterByname(String name);

}
