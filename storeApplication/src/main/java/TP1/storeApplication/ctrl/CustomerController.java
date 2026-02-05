package TP1.storeApplication.ctrl;

import TP1.storeApplication.entity.Customer;
import TP1.storeApplication.service.CustomerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@Controller
public class CustomerController {

    private CustomerService customerService;

    public CustomerController( CustomerService customerService ){
        this.customerService = customerService;
    }

    @PostMapping("/createCustomer")
    public RedirectView createCustomer(@RequestParam String email, @RequestParam String password, @RequestParam String firstName, @RequestParam String lastName ){
        customerService.createCustomer(email, password, firstName, lastName);
        return new RedirectView("/store/home");
    }

    @PostMapping("/connect")
    public RedirectView connect(@RequestParam String email,
                          @RequestParam String password,
                          HttpSession session) {

        var customer = customerService.connect(email, password);

        if (customer == null) {
            return new RedirectView("/store/home");
        } else {
            session.setAttribute("customer", customer);
            return new RedirectView("/store/storeUser");
        }
    }

    @GetMapping("/logout")
    public RedirectView logout(HttpSession session) {
        session.invalidate();

        return new RedirectView("/store/home");
    }

    @GetMapping("/store/home")
    public ModelAndView home(){
        Iterable<Customer> customers = customerService.readAllCustomers();
        var model = Map.of("customers", customers);
        return new ModelAndView("customers", model);
    }
}
