package fr.norsys.Formation.service;

import fr.norsys.Formation.entity.Formation;
import fr.norsys.Formation.entity.Participant;
import fr.norsys.Formation.exception.FormationException;
import fr.norsys.Formation.repository.FormationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class FormationService  {

    FormationRepository formationRepository;

    @Autowired
    public FormationService(FormationRepository formationRepository){
        this.formationRepository=formationRepository;
    }


    public Formation SaveFormation(Formation formation) {
        return formationRepository.save(formation);
    }

    public List<Formation> getAllFormations() {
        return formationRepository.findAll();
    }

    public List<Formation> getFormationsFiltredByTitle(String title){
        return formationRepository.filterByTitle(title);
    }

    public Optional<Formation> getFormationById(Long id) {
        Optional<Formation> formation =formationRepository.findById(id);
        if (formation.isEmpty()) {
            throw new FormationException("Formation not found with ID: " + id);
        }
        return formation;
    }

    public void deleteFormation(Long id) {
        Optional<Formation> formationDeleted = formationRepository.findById(id);

        if (formationDeleted.isEmpty()) {
            throw new FormationException("Formation not found with ID: " + id);
        }
        formationRepository.delete(formationDeleted.get());
    }

    public Set<Participant> getAllParticipantsForFormation(Long id) {
        Formation formation = formationRepository.findById(id).get();

        return formation.getParticipants();

    }
}
