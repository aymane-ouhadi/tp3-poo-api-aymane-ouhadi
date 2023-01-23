package fr.ensim.info.tp3aymaneouhadi.controller;


import fr.ensim.info.tp3aymaneouhadi.model.EtalabAPIAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MeteoController {

    @Autowired
    RestTemplate rt;

    public static String API_KEY = "3ae0ed69ffce2377a3aab50c55e13bc0d8cbce0571e7615ace7ac1b367c39eb0";

    @GetMapping("/meteo")
    public String meteoGet(){
        return "meteo";
    }

    @PostMapping("/meteo")
    public String meteoPost(
        @RequestParam(name="address", required=true) String address,
        Model model
    ){

        String meteo_url = "https://api.meteo-concept.com/api/forecast/daily/0?token=";

        //élimination des espaces et les remplacer par des +
        String query = address.toLowerCase().replace(" ", "+");

        //Géo API
        EtalabAPIAddress etalabAPIAddress = rt.getForObject("https://api-adresse.data.gouv.fr/search/?q=" + query + "&limit=1", EtalabAPIAddress.class);

        //Méteo API

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        rt.exchange(meteo_url + API_KEY, HttpMethod.GET, requestEntity, String.class);

        Map<String, int[]> x = (HashMap<String, int[]>) etalabAPIAddress.features.get(0).get("geometry");

//        String longitude = x.get("coordinates")[0];
//        String latitude = x.get("coordinates")[1];

        model.addAttribute("address", address);
        model.addAttribute("info", etalabAPIAddress);

        model.addAttribute("latitude", x.get("coordinates")[0]);
        model.addAttribute("longitude", x.get("coordinates"));

        return "meteo";
    }

}
