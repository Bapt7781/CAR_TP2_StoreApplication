package TP1.storeApplication.service;

import TP1.storeApplication.entity.Commande;
import TP1.storeApplication.entity.CommandeRepository;
import TP1.storeApplication.entity.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommandeService {

    private CommandeRepository commandeRepository;

    public CommandeService( CommandeRepository commandeRepository) {
        this.commandeRepository = commandeRepository;
    }

    public List<Commande> getCommandesByCustomer(Customer customer) {
        return commandeRepository.findByCustomer(customer);
    }

    public Commande getCommandeById(Long id) {
        return commandeRepository.findById(id).orElse(null);
    }

    public void createCommande(String titre, Customer customer){
        var commande = new Commande(titre, customer);
        commandeRepository.save(commande);
    }
}
