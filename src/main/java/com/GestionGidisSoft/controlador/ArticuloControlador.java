package com.GestionGidisSoft.controlador;

import com.GestionGidisSoft.entidades.Articulo;
import com.GestionGidisSoft.entidades.Libro;
import com.GestionGidisSoft.entidades.Pais;
import com.GestionGidisSoft.entidades.Usuario;
import com.GestionGidisSoft.servicios.ArchivosServicio;
import com.GestionGidisSoft.servicios.ArticuloServicio;
import com.GestionGidisSoft.servicios.UsuarioServicio;
import com.GestionGidisSoft.servicios.Utils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/articulos")
public class ArticuloControlador {

    @Autowired
    private ArticuloServicio articuloServicio;

    @Autowired
    private Utils util;

    @Autowired
    private ArchivosServicio archivosServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/verArticulos")
    public ModelAndView goArticulos(HttpServletRequest request) throws Exception{
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        try {
            if (session.getAttribute("usuario") != null) {
                Usuario usuario = (Usuario) session.getAttribute("usuario");
                List<Articulo> articulos = articuloServicio.findByUsuarioId(usuario.getIdusuario());
                if (articulos == null){
                    mav.addObject("mensaje", "Aún no hay registros");
                } else {
                    List<Articulo> articulosAux = new ArrayList();
                    for (Articulo articulo : articulos) {
                        ObjectMapper objectMapper = new ObjectMapper();
                        Map<String, Object> lugarMap;
                        try {
                            lugarMap = objectMapper.readValue(articulo.getCiudad(), new TypeReference<HashMap<String, Object>>() {});
                        } catch (IOException e) {
                            lugarMap = new HashMap<>();
                        }
                        Pais pais = util.obtenerPais((String) lugarMap.get("pais"));
                        String idioma = util.obtenerNombreIdioma(articulo.getIdioma());
                        articulo.setIdioma(idioma);
                        articulo.setPaisPublicacion(pais.getName());
                        articulo.setCiudadPublicacion((String) lugarMap.get("ciudad"));

                        articulosAux.add(articulo);
                    }
                    articulos = articulosAux;
                }

                mav.addObject("listaArticulos", articulos);
                mav.addObject("usuario", usuario);
                mav.setViewName("listarArticulos");
                return mav;
            } else {
                System.out.println("error de logueo");
                session.setAttribute("usuario", new Usuario());
                mav.addObject("usuario", session.getAttribute("usuario"));
                mav.setViewName("redirect:/");
                return mav;
            }
        } catch (Exception e) {
            e.printStackTrace();
            mav.setViewName("redirect:/usuarios/home");
            return mav;
        }
    }

    @GetMapping("/nuevoArticulo")
    public ModelAndView nuevoArticulo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        if (session.getAttribute("usuario") != null) {
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            mav.addObject("usuarioId", usuario.getIdusuario());
            mav.addObject("articulo", new Articulo());
            mav.setViewName("registrarArticulo");
            return mav;
        } else {
            System.out.println("error de logueo");
            session.setAttribute("usuario", new Usuario());
            mav.addObject("usuario", session.getAttribute("usuario"));
            mav.setViewName("redirect:/");
            return mav;
        }
    }

    @PostMapping("/guardar")
    public ModelAndView guardarArticulo(HttpServletRequest request, @ModelAttribute("articulo") Articulo articulo,
                                        @RequestParam(value = "pais") String pais,
                                        @RequestParam(value = "ciudad") String ciudad) throws Exception {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        if (session.getAttribute("usuario") != null) {
            //verifica que no haya un libro con el mismo DOI
            Optional<Articulo> articuloExistente = articuloServicio.findByDoi(articulo.getIdentificadorDigitalDoi());
            if (articuloExistente.isPresent()) {
                mav.addObject("articulo", articulo);
                mav.addObject("mensaje", "Ya existe un articulo con este identificador DOI.");
                mav.setViewName("registrarArticulo"); // Nombre de la vista del formulario
                return mav;
            } else {
                Map<String, Object> lugarPublicacion = new HashMap<>();
                lugarPublicacion.put("pais", pais);
                lugarPublicacion.put("ciudad", ciudad);
                ObjectMapper objectMapper = new ObjectMapper();
                String lugarJson;
                try {
                    lugarJson = objectMapper.writeValueAsString(lugarPublicacion);
                } catch (Exception e) {
                    lugarJson = "";
                    System.out.println("error asignando pais y ciudad: " + e.getMessage());
                }
                articulo.setCiudad(lugarJson);
                Usuario usuario = (Usuario) session.getAttribute("usuario");
                articuloServicio.guardarArticulo(articulo);
                articuloServicio.agregarRegistroAutorArticulo(articulo.getIdArticulo(), usuario.getIdusuario());
                System.out.println(articulo.getIdArticulo());
                List<Articulo> listaArticulos = articuloServicio.findByUsuarioId(usuario.getIdusuario());
                mav.addObject("listaArticulos", listaArticulos);
                mav.addObject("usuario", usuario);
                mav.setViewName("listarArticulos");
                return mav;
            }
        } else {
            System.out.println("error de logueo");
            session.setAttribute("usuario", new Usuario());
            mav.addObject("usuario", session.getAttribute("usuario"));
            mav.setViewName("redirect:/");
            return mav;
        }
    }
}
