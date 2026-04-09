package com.ecotravel.controller;

import com.ecotravel.dtos.AdventureRequestDTO;
import com.ecotravel.dtos.AdventureResponseDTO;
import com.ecotravel.facade.AdventureFacade;
import com.ecotravel.service.AdventureService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/adventures")
@RequiredArgsConstructor
public class AdventureController {

    private final AdventureService adventureService;
    private final AdventureFacade adventureFacade; // Injektujemo fasadu

    // Lista svih avantura (Fasada spaja podatke o vodičima iz drugog servisa)
    @GetMapping
    public ResponseEntity<List<AdventureResponseDTO>> getAll() {
        return ResponseEntity.ok(adventureFacade.getAllAdventuresWithGuides());
    }

    // Detalji jedne avanture (Fasada povlači "Milana" preko Feign-a)
    @GetMapping("/{id}")
    public ResponseEntity<AdventureResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(adventureFacade.getAdventureFullDetails(id));
    }

    // Kreiranje (Može preko servisa, jer je to čist upis u bazu)
    @PostMapping
    public ResponseEntity<AdventureResponseDTO> create(@Valid @RequestBody AdventureRequestDTO dto) {
        return new ResponseEntity<>(adventureService.createAdventure(dto), HttpStatus.CREATED);
    }

    // Ažuriranje
    @PutMapping("/{id}")
    public ResponseEntity<AdventureResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody AdventureRequestDTO dto) {
        return ResponseEntity.ok(adventureService.updateAdventure(id, dto));
    }

    // Brisanje
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        adventureService.deleteAdventure(id);
        return ResponseEntity.noContent().build();
    }

    // Provera vodiča (Složena logika delegirana fasadi)
    @GetMapping("/{id}/verify-guide")
    public ResponseEntity<String> verifyGuide(
            @PathVariable Long id,
            @RequestParam String requiredLanguage) {
        return ResponseEntity.ok(adventureFacade.verifyGuide(id, requiredLanguage));
    }

    // Generisanje izveštaja (Fasada koristi Factory unutar sebe)
    @GetMapping("/{id}/report")
    public ResponseEntity<String> getReport(@PathVariable Long id) {
        return ResponseEntity.ok(adventureFacade.generateAdventureReport(id));
    }

    // Pretraga jeftinih avantura
    @GetMapping("/budget-friendly")
    public ResponseEntity<List<AdventureResponseDTO>> getBudgetAdventures() {
        // I ovde koristimo fasadu ako želimo da i u ovoj listi vidimo podatke o vodičima
        return ResponseEntity.ok(adventureService.getBudgetAdventuresRaw().stream()
                .map(adv -> adventureFacade.getAdventureFullDetails(adv.getId()))
                .toList());
    }

    // Pretraga po kategoriji
    @GetMapping("/category/{name}")
    public ResponseEntity<List<AdventureResponseDTO>> getByCategory(@PathVariable String name) {
        return ResponseEntity.ok(adventureService.getByCategoryRaw(name).stream()
                .map(adv -> adventureFacade.getAdventureFullDetails(adv.getId()))
                .toList());
    }
}