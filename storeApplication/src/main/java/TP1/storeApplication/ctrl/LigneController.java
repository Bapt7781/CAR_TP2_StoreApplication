package TP1.storeApplication.ctrl;

import TP1.storeApplication.service.LigneService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class LigneController {

    private LigneService ligneService;

    public LigneController(LigneService ligneService) {
        this.ligneService = ligneService;
    }

    @PostMapping("/store/commande/{id}/addLigne")
    public RedirectView addLigne(
            @PathVariable Long id,
            @RequestParam String libelle,
            @RequestParam int quantite,
            @RequestParam double prixUnitaire) {

        ligneService.createLigne(id, libelle, quantite, prixUnitaire);

        return new RedirectView("/store/commande/" + id);
    }

    @GetMapping("/store/commande/{commandeId}/deleteLigne/{ligneId}")
    public RedirectView deleteLigne(
            @PathVariable Long commandeId,
            @PathVariable Long ligneId) {

        ligneService.deleteLigne(ligneId);

        return new RedirectView("/store/commande/" + commandeId);
    }
}