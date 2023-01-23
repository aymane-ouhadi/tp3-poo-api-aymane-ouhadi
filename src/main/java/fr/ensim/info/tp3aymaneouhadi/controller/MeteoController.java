package fr.ensim.info.tp3aymaneouhadi.controller;

import fr.ensim.info.tp3aymaneouhadi.model.EtalabAPIAddress;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class MeteoController {

    @GetMapping("/meteo")
    public String meteoGet(){
        return "meteo";
    }

    @PostMapping("/meteo")
    public String meteoPost(
        @RequestParam(name="address", required=true) String address,
        Model model
    ){

        String query = address.toLowerCase().replace(" ", "+");

        RestTemplate rt = new RestTemplate();
        EtalabAPIAddress etalabAPIAddress = rt.getForObject("https://api-adresse.data.gouv.fr/search/?q=" + query + "&limit=1", EtalabAPIAddress.class);


        model.addAttribute("address", address);
        return "meteo";
    }

}
