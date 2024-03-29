package fr.norsys.Formation.service;

import fr.norsys.Formation.entity.Formation;
import fr.norsys.Formation.entity.Participant;
import fr.norsys.Formation.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParticipantService {

    @Autowired
    ParticipantRepository participantRepository ;

    public Participant saveParticipant(Participant participant) {
        return participantRepository.save(participant);
    }



    public List<Participant> getAllParticipants() {
        return participantRepository.findAll();
    }

    public Optional<Participant> getParticipantById(Long id) {

        return participantRepository.findById(id);
    }

    public void deleteParticipant(Long id) {
        Optional<Participant> ParticipantDeleted = participantRepository.findById(id);

        participantRepository.delete(ParticipantDeleted.get());
    }

    public List<Participant> getparticipantByName(String name){
        return participantRepository.filterByname(name);
    }

}
