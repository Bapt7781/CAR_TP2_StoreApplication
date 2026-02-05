package TP1.storeApplication.service;

import TP1.storeApplication.entity.Commande;
import TP1.storeApplication.entity.CommandeRepository;
import TP1.storeApplication.entity.Ligne;
import TP1.storeApplication.entity.LigneRepository;
import org.springframework.stereotype.Service;

@Service
public class LigneService {

    private LigneRepository ligneRepository;
    private CommandeRepository commandeRepository;

    public LigneService(LigneRepository ligneRepository, CommandeRepository commandeRepository) {
        this.ligneRepository = ligneRepository;
        this.commandeRepository = commandeRepository;
    }

    public void createLigne(Long commandeId, String libelle, int quantite, double prix) {
        Commande commande = commandeRepository.findById(commandeId).orElse(null);
            Ligne ligne = new Ligne(libelle, quantite, prix, commande);
            ligneRepository.save(ligne);
    }

    public void deleteLigne(Long id) {
        ligneRepository.deleteById(id);
    }
}