package TP1.storeApplication.ctrl;

import TP1.storeApplication.entity.Commande;
import TP1.storeApplication.entity.Customer;
import TP1.storeApplication.service.CommandeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


import java.util.List;
import java.util.Map;

@Controller
public class CommandeController {

    private CommandeService commandeService;

    public CommandeController( CommandeService commandeService ){
        this.commandeService = commandeService;
    }

    @GetMapping("/store/storeUser")
    public ModelAndView showCommandes(HttpSession session) {

        Customer customer = (Customer) session.getAttribute("customer");

        if (customer == null) {
            return new ModelAndView("/store/home");
        }

            List<Commande> mesCommandes = commandeService.getCommandesByCustomer(customer);
            var model = Map.of("commandes", mesCommandes);
            return new ModelAndView("storeUser", model);

    }

    @GetMapping("/store/commande/{id}")
    public String showCommande(@PathVariable Long id, HttpSession session, Model model) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            return "/store/home";
        }

        Commande commande = commandeService.getCommandeById(id);

        if (commande != null && !commande.getCustomer().getEmail().equals(customer.getEmail())) {
            return "/store/storeUser";
        }

        model.addAttribute("customer", customer);
        model.addAttribute("commande", commande);

        return "commandeDetails";
    }

    @PostMapping("/createCommande")
    public RedirectView createCommande(@RequestParam String titre, HttpSession session){
        Customer customer = (Customer) session.getAttribute("customer");

        if (customer == null) {
            return new RedirectView("/store/home");
        }

        commandeService.createCommande(titre, customer);
        return new RedirectView("/store/storeUser");
    }

    @GetMapping("/store/commande/imprimer/{id}")
    public String printCommande(@PathVariable Long id, HttpSession session, Model model) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            return "/store/home";
        }
        Commande commande = commandeService.getCommandeById(id);
        if (commande == null || !commande.getCustomer().getEmail().equals(customer.getEmail())) {
            return "/store/storeUser";
        }

        double totalGlobal = 0.0;
        for (TP1.storeApplication.entity.Ligne ligne : commande.getLignes()) {
            totalGlobal += ligne.getQuantite() * ligne.getPrixUnitaire();
        }

        model.addAttribute("customer", customer);
        model.addAttribute("commande", commande);
        model.addAttribute("totalGlobal", totalGlobal);

        return "commandePrint";
    }
}
