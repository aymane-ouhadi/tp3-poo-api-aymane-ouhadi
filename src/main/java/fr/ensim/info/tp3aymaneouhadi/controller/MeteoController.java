package fr.ensim.info.tp3aymaneouhadi.controller;


import fr.ensim.info.tp3aymaneouhadi.model.EtalabAPIAddress;
import fr.ensim.info.tp3aymaneouhadi.model.Feature;
import fr.ensim.info.tp3aymaneouhadi.model.MeteoConceptResponse;
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


        //élimination des espaces et les remplacer par des +
        String query = address.toLowerCase().replace(" ", "+");

        //Géo API
        EtalabAPIAddress etalabAPIAddress = rt.getForObject("https://api-adresse.data.gouv.fr/search/?q=" + query + "&limit=1", EtalabAPIAddress.class);


        Feature feature = etalabAPIAddress.features.get(0);


        float longitude = feature.getGeometry().getCoordinates().get(0);

        float latitude = feature.getGeometry().getCoordinates().get(1);



        //Méteo API
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);


        String meteo_url = "https://api.meteo-concept.com/api/forecast/daily/0?token=" + API_KEY + "&latlng=" + latitude + "," + longitude;

        ResponseEntity<MeteoConceptResponse> response = rt.exchange(meteo_url, HttpMethod.GET, requestEntity, MeteoConceptResponse.class);

        //Passage Model-View
        model.addAttribute("address", address);
        model.addAttribute("info", etalabAPIAddress);

        model.addAttribute("longitude", longitude);
        model.addAttribute("latitude", latitude);

        //Passage Model-View
        model.addAttribute("body", response.getBody().getForecast());
        model.addAttribute("wind", response.getBody().getForecast().get("wind10m") + " %");
        model.addAttribute("rain", response.getBody().getForecast().get("probarain") + " %");
        model.addAttribute("tmin", response.getBody().getForecast().get("tmin") + " °C");
        model.addAttribute("tmax", response.getBody().getForecast().get("tmax") + " °C");
        model.addAttribute("sun_hours", response.getBody().getForecast().get("sun_hours") + " heures");

        return "meteo";
    }

}
