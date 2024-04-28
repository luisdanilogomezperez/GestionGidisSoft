package com.GestionGidisSoft.servicios;

import com.GestionGidisSoft.Constantes.Format;
import com.GestionGidisSoft.entidades.Pais;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class Utils {

    @Autowired
    private RestTemplate restTemplate;

    public List<Pais> obtenerPaises() {
        List<Pais> paisesAux = new ArrayList();
        String apiUrl = Format.API_URL_PAISES;
        RestTemplate restTemplate = new RestTemplate();
        // Hacer la solicitud HTTP
        ResponseEntity<Pais[]> responseEntity = restTemplate.getForEntity(apiUrl, Pais[].class);
        // Obtener la lista de países desde la respuesta
        Pais[] paises = responseEntity.getBody();
        // Hacer algo con la lista de países (por ejemplo, imprimir información)
        for (Pais pais : paises) {
            paisesAux.add(pais);
        }
        return paisesAux;
    }

    public Pais obtenerPais(String codigoPais) {
        Pais pais = new Pais();
        List<Pais> paises = obtenerPaises();
        for (Pais paisAux : paises) {
            if (paisAux.getAlpha2Code().equals(codigoPais)) {
                pais = paisAux;
                break;
            } else {
                pais = null;
            }
        }
        return pais;
    }

    public List<String> obtenerTodosIdiomas() {
        ResponseEntity<String[]> responseEntity = restTemplate.getForEntity(Format.API_URL_IDIOMAS, String[].class);
        String[] idiomas = responseEntity.getBody();
        return Arrays.asList(idiomas);
    }

    public String obtenerNombreIdioma(String codigoIdioma) {
        String idiomaObtenido = "";
        String codigoIdiomaAux = null;
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                Format.API_URL_IDIOMA + "{" + codigoIdioma + "}", String.class, codigoIdioma);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseEntity.getBody());
            for (JsonNode countryNode : jsonNode) {
                for (JsonNode languageNode : countryNode.get("languages")) {
                    if (codigoIdioma.equalsIgnoreCase(languageNode.get("iso639_1").asText())) {
                        idiomaObtenido = languageNode.get("name").asText();
                        codigoIdiomaAux = languageNode.get("iso639_1").asText();
                        break;
                    }
                }
                if (codigoIdiomaAux != null) {
                    break;
                }
            }
        } catch (Exception e){
            return null;
        }
        return idiomaObtenido;
    }

    public static Date formatDate(String fechaString, String formato) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(formato);
        return sdf.parse(fechaString);
    }
}