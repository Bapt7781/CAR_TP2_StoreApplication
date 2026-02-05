package TP1.storeApplication.entity;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommandeRepository extends CrudRepository<Commande, Long> {

    List<Commande> findByCustomer(Customer customer);
}
