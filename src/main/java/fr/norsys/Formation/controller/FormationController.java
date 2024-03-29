package fr.norsys.Formation.controller;


import fr.norsys.Formation.entity.Formation;
import fr.norsys.Formation.entity.Participant;
import fr.norsys.Formation.exception.FormationException;
import fr.norsys.Formation.service.FormationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RestController
@RequestMapping("/api/v1/formation")
@CrossOrigin("*")
public class FormationController {
    @Autowired
    private FormationService formationService;

    @PostMapping
    public ResponseEntity<Formation> addFormation(@RequestBody Formation formation){
       Formation savedFormation = formationService.SaveFormation(formation);
        return new ResponseEntity<>(savedFormation, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Formation>> getAllFormations() {
        List<Formation> formations = formationService.getAllFormations();
        return new ResponseEntity<>(formations, HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Formation>> getFormationsFiltredByTitle(@RequestParam String titre){
        List<Formation> formations = formationService.getFormationsFiltredByTitle(titre);
        return new ResponseEntity<>(formations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Formation> getFormationById(@PathVariable Long id) {
        Optional<Formation> formation = formationService.getFormationById(id);
        return new ResponseEntity<>(formation.get(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFormation(@PathVariable Long id) {
        formationService.deleteFormation(id);
        return ResponseEntity.ok(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Formation> updateFormation(@PathVariable Long id,@RequestBody Formation updatedFormation) {
        Formation existingFormation = formationService.getFormationById(id)
            .orElseThrow(() -> new FormationException("Formation not found"));

        existingFormation.setTitre(updatedFormation.getTitre());
        existingFormation.setDescription(updatedFormation.getDescription());
        existingFormation.setDateDebut(updatedFormation.getDateDebut());
        existingFormation.setDateFin(updatedFormation.getDateFin());

        Formation savedFormation = formationService.SaveFormation(existingFormation);
        return new ResponseEntity<>(savedFormation, HttpStatus.OK);
    }

    @GetMapping("/{id}/participants")
    public ResponseEntity<Set<Participant>> getAllParticipantsForFormation(@PathVariable Long id) {
        Set<Participant> participants = formationService.getAllParticipantsForFormation(id);
        if (participants != null) {
            return new ResponseEntity<>(participants, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
