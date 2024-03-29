package fr.norsys.Formation.controller;

import fr.norsys.Formation.entity.Formation;
import fr.norsys.Formation.entity.Participant;
import fr.norsys.Formation.service.FormationService;
import fr.norsys.Formation.service.ParticipantService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RestController
@RequestMapping("/api/v1/participant")
@CrossOrigin("*")
@AllArgsConstructor
public class ParticipantController {
    
    @Autowired
    private ParticipantService participantService ;
    @Autowired
    private FormationService formationService;


    @PostMapping
    public ResponseEntity<Participant> addParticipant(@RequestBody Participant Participant){
        Participant savedParticipant = participantService.saveParticipant(Participant);
        return new ResponseEntity<>(savedParticipant, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Participant>> getAllParticipants() {
        List<Participant> Participants = participantService.getAllParticipants();
        return new ResponseEntity<>(Participants, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Participant> getParticipantById(@PathVariable Long id) {
        Optional<Participant> Participant = participantService.getParticipantById(id);
        return new ResponseEntity<>(Participant.get(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParticipant(@PathVariable Long id) {
        participantService.deleteParticipant(id);
        return ResponseEntity.ok(null);
    }
    @GetMapping("/filter")
    public ResponseEntity<List<Participant>> getParticipantByName(@RequestParam String name){
        List<Participant> participants = participantService.getparticipantByName(name);
        return new ResponseEntity<>(participants, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Participant> updateParticipant(@PathVariable Long id,@RequestBody Participant updatedFormatio) {
        Participant existingParticipant = participantService.getParticipantById(id).get();
        existingParticipant.setName(updatedFormatio.getName());
        existingParticipant.setEmail(updatedFormatio.getEmail());

        Participant savedParticipant= participantService.saveParticipant(existingParticipant);
        return new ResponseEntity<>(savedParticipant, HttpStatus.OK);
    }

    @PostMapping("/{participantId}/addFormation/{formationId}")
    public ResponseEntity<Void> addParticipantToFormation(@PathVariable Long participantId, @PathVariable Long formationId) {
        Participant participant = participantService.getParticipantById(participantId).orElse(null);
        Formation formation = formationService.getFormationById(formationId).orElse(null);

        if (participant == null || formation == null) {
            return ResponseEntity.notFound().build();
        }
        participant.getFormations().add(formation);
        participantService.saveParticipant(participant);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{participantId}/removeFormation/{formationId}")
    public ResponseEntity<Void> removeParticipantFromFormation(@PathVariable Long participantId, @PathVariable Long formationId) {
        Participant participant = participantService.getParticipantById(participantId).orElse(null);
        Formation formation = formationService.getFormationById(formationId).orElse(null);

        if (participant == null || formation == null) {
            return ResponseEntity.notFound().build();
        }
        participant.getFormations().remove(formation);
        participantService.saveParticipant(participant);

        return ResponseEntity.ok().build();
    }


}
