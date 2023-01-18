package fr.ensim.info.tp3aymaneouhadi;

import fr.ensim.info.tp3aymaneouhadi.model.Address;
import fr.ensim.info.tp3aymaneouhadi.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class Tp3AymaneOuhadiApplication implements ApplicationRunner {

    @Autowired
    AddressRepository addressRepository;

    public static void main(String[] args) {
        SpringApplication.run(Tp3AymaneOuhadiApplication.class, args);

    }

    //Fonction qui se lance au demarrage de l'application qui nous permettra d'ajouter des lignes à la table
    @Override
    public void run(ApplicationArguments arg0) throws Exception {
        Address address1 = new Address(
                1L,
                new Date(),
                "57 boulevard demorieux",
                "Amine"
        );

        Address address2 = new Address(
                2L,
                new Date(),
                "51 allee du gamay, 34080 montpellier",
                "Meriem"
        );

        addressRepository.save(address1);
        addressRepository.save(address2);
    }

}