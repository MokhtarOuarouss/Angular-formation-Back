package fr.norsys.Formation.repository;

import fr.norsys.Formation.entity.Formation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormationRepository extends JpaRepository<Formation, Long> {

    @Query("SELECT f FROM Formation f WHERE LOWER(f.titre) LIKE %:titre%")
    List<Formation> filterByTitle(String titre);


}

