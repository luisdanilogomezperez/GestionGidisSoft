package com.GestionGidisSoft.controlador;

import com.GestionGidisSoft.Constantes.Format;
import com.GestionGidisSoft.entidades.*;
import com.GestionGidisSoft.servicios.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/proyectosInvestigacion")
public class ProyectoInvestControlador {

    @Autowired
    ProyectoInvestigacionServicio proyectoServicio;
    @Autowired
    UsuarioServicio usuarioServicio;
    @Autowired
    LibroServico libroServico;
    @Autowired
    ArticuloServicio articuloServicio;
    @Autowired
    CapituloLibroServicio capituloLibroServicio;

    @Autowired
    DemasTrabajoServicio demasTrabajoServicio;


    @GetMapping("/verProyectosInvestigacion")
    public ModelAndView goProyectosInvestigacion(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        session.removeAttribute("listaProduccionesAsociados");
        session.removeAttribute("listaLibros");
        session.removeAttribute("listaArticulos");
        session.removeAttribute("listaCapitulos");
        if (session.getAttribute("usuario") != null) {
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            List<ProyectoInvestigacion> listaProyectos = proyectoServicio.findByUsuarioId(usuario.getIdusuario());
            if (listaProyectos.isEmpty()){
                mav.addObject("mensaje", "Aún no hay registros");
            }
            mav.addObject("listaProyectos", listaProyectos);
            mav.addObject("usuario", usuario);
            mav.setViewName("listarProyectosInvestigacion");
            return mav;
        } else {
            System.out.println("error de logueo");
            session.setAttribute("usuario", new Usuario());
            mav.addObject("usuario", session.getAttribute("usuario"));
            mav.setViewName("redirect:/");
            return mav;
        }
    }

    @GetMapping("/nuevo")
    public ModelAndView nuevoLibro(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        if (session.getAttribute("usuario") != null) {
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            mav.addObject("idUsuario", usuario.getIdusuario());
            mav.addObject("proyectoInvestigacion", new ProyectoInvestigacion());
            mav.setViewName("registrarProyectoInvestigacion");
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
    public ModelAndView guardarLibro(HttpServletRequest request,
                                     @ModelAttribute("proyectoInvestigacion") ProyectoInvestigacion proyectoInvestigacion,
                                     @RequestParam(value = "tipo_proyecto") String tipoProyecto,
                                     @RequestParam(value = "dta_acto_admString") String fechaActoAdministrativoString,
                                     @RequestParam(value = "tipo_financiacion_proyecto") String tipoFinanciacionProyecto,
                                     @RequestParam(value = "fuente_financiacion") String fuenteFinanciacion,
                                     @RequestParam(value = "financiacion") String financiacion) throws Exception {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        if (session.getAttribute("usuario") != null) {

            Usuario usuario = (Usuario) session.getAttribute("usuario");
            Date fechaActoAdministrativo = Utils.formatDate(fechaActoAdministrativoString, "yyyy-MM-dd");
            proyectoInvestigacion.setFechaActoAdministrativo(fechaActoAdministrativo);
            proyectoInvestigacion.setTipoProyecto(tipoProyecto);
            if (tipoFinanciacionProyecto.equals("Solidario")) {
                proyectoInvestigacion.setSolidario("SI");
                proyectoInvestigacion.setFinanciado("NO");
            } else {
                proyectoInvestigacion.setSolidario("NO");
                proyectoInvestigacion.setFinanciado("SI");
                proyectoInvestigacion.setFuenteFinanciacion(fuenteFinanciacion);
                proyectoInvestigacion.setTipoFinanciacion(financiacion);
            }
            proyectoServicio.guardarProyectoInvestigacion(proyectoInvestigacion);
            proyectoServicio.agregarRegistroAutorProyectoInvestigacion( proyectoInvestigacion.getIdProyectoInvestigacion(), usuario.getIdusuario());
            List<ProyectoInvestigacion> listaProyectos = proyectoServicio.findByUsuarioId(usuario.getIdusuario());
            if (listaProyectos.isEmpty()){
                mav.addObject("mensaje", "Aún no hay registros");
            }
            mav.addObject("listaProyectos", listaProyectos);
            mav.addObject("usuario", usuario);
            mav.setViewName("listarProyectosInvestigacion");
            return mav;
        } else {
            System.out.println("error de logueo");
            session.setAttribute("usuario", new Usuario());
            mav.addObject("usuario", session.getAttribute("usuario"));
            mav.setViewName("redirect:/");
            return mav;
        }
    }

    @RequestMapping("/editar/{idProyectoInvestigacion}")
    public ModelAndView goUpdate(HttpServletRequest request, @PathVariable(value = "idProyectoInvestigacion") Long idProyectoInvestigacion) throws Exception {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();

        if (session.getAttribute("usuario") != null) {
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            ProyectoInvestigacion proyectoInvestigacion = proyectoServicio.buscarPorId(idProyectoInvestigacion);
            int cantidadPalabras = proyectoInvestigacion.getResumen().length();
            mav.addObject("cantidadPalabras", 4000 - cantidadPalabras);
            mav.addObject("proyectoInvestigacion", proyectoInvestigacion);
            mav.setViewName("editarProyectoInvestigacion");
            return mav;
        } else {
            System.out.println("error de logueo");
            session.setAttribute("usuario", new Usuario());
            mav.addObject("usuario", session.getAttribute("usuario"));
            mav.setViewName("redirect:/");
            return mav;
        }
    }
    @PostMapping("/editar")
    public ModelAndView actualizarProyectoInvestigacion(HttpServletRequest request,
            @ModelAttribute("proyectoInvestigacion") ProyectoInvestigacion proyectoInvestigacion,
            @RequestParam(value = "tipo_proyecto") String tipoProyecto,
            @RequestParam(value = "dta_acto_admString") String fechaActoAdministrativoString,
            @RequestParam(value = "tipo_financiacion_proyecto") String tipoFinanciacionProyecto,
            @RequestParam(value = "fuente_financiacion") String fuenteFinanciacion,
            @RequestParam(value = "financiacion") String financiacion) throws Exception {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        if (session.getAttribute("usuario") != null) {
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            Date fechaActoAdministrativo = Utils.formatDate(fechaActoAdministrativoString, "yyyy-MM-dd");
            proyectoInvestigacion.setFechaActoAdministrativo(fechaActoAdministrativo);
            proyectoInvestigacion.setTipoProyecto(tipoProyecto);
            if (tipoFinanciacionProyecto.equals("Solidario")) {
                proyectoInvestigacion.setSolidario("SI");
                proyectoInvestigacion.setFinanciado("NO");
            } else {
                proyectoInvestigacion.setSolidario("NO");
                proyectoInvestigacion.setFinanciado("SI");
                proyectoInvestigacion.setFuenteFinanciacion(fuenteFinanciacion);
                proyectoInvestigacion.setTipoFinanciacion(financiacion);
            }
            String mensaje = proyectoServicio.actualizarProyectoInvestigacion(proyectoInvestigacion);
            List<ProyectoInvestigacion> listaProyectos = proyectoServicio.findByUsuarioId(usuario.getIdusuario());
            mav.addObject("listaProyectos", listaProyectos);
            mav.addObject("usuario", usuario);
            mav.setViewName("listarProyectosInvestigacion");
            return mav;
        } else {
            System.out.println("error de logueo");
            session.setAttribute("usuario", new Usuario());
            mav.addObject("usuario", session.getAttribute("usuario"));
            mav.setViewName("redirect:/");
            return mav;
        }
    }
    @RequestMapping("/detalle/{idProyectoInvestigacion}")
    public ModelAndView goDetail(HttpServletRequest request, @PathVariable(value = "idProyectoInvestigacion") long idProyectoInvestigacion) throws Exception {
        List<Usuario> listaAutores = new ArrayList();
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        if (session.getAttribute("usuario") != null) {
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            List<Usuario> coautores = usuarioServicio.listarCoautoresProyectosInvestigacion(idProyectoInvestigacion, usuario.getIdusuario());
            List<Usuario> autores = usuarioServicio.listarAutoresProyectosInvestigacion(idProyectoInvestigacion, usuario.getIdusuario());
            List<Map<String, String>> produccionesConsolidadas = new ArrayList<>();
            ProyectoInvestigacion proyectoInvestigacion = proyectoServicio.buscarPorId(idProyectoInvestigacion);
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> produccionesMap;
            try {
                produccionesMap = objectMapper.readValue(proyectoInvestigacion.getJsonProducciones(), new TypeReference<HashMap<String, Object>>() {});
            } catch (IOException e) {
                produccionesMap = new HashMap<>();
            }
            String idsLibros = (String) produccionesMap.get("idsLibros");
            String idsCapitulosLibros = (String) produccionesMap.get("idsCapitulosLibros");
            String idsArticulos = (String) produccionesMap.get("idsArticulos");
            String idsDemasTrabajo = (String) produccionesMap.get("idsDemasTrabajo");
            Set<String> idsLibroSet = Arrays.stream(idsLibros.split(","))
                    .map(String::toString)
                    .collect(Collectors.toSet());
            Set<String> idsCapitulosLibroSet = Arrays.stream(idsCapitulosLibros.split(","))
                    .map(String::toString)
                    .collect(Collectors.toSet());
            Set<String> idsArticuloSet = Arrays.stream(idsArticulos.split(","))
                    .map(String::toString)
                    .collect(Collectors.toSet());
            Set<String> idsDemasTrabajosSet = Arrays.stream(idsDemasTrabajo.split(","))
                    .map(String::toString)
                    .collect(Collectors.toSet());
            List<Libro> listaLibros = libroServico.findByUsuarioId(usuario.getIdusuario());
            List<Articulo> listaArticulos = articuloServicio.findByUsuarioId(usuario.getIdusuario());
            List<CapituloLibro> listaCapitulos = capituloLibroServicio.findByUsuarioId(usuario.getIdusuario());
            List<DemasTrabajo> listademasTrabajos = demasTrabajoServicio.findByUsuarioId(usuario.getIdusuario());
            List<Libro> listaLibrosAux = new ArrayList();
            List<Articulo> listaArticulosAux =  new ArrayList();
            List<CapituloLibro> listaCapitulosAux =  new ArrayList();
            List<DemasTrabajo> listademasTrabajosAux =  new ArrayList();
            for (Usuario usuarioAux: autores) {
                if (usuarioAux.getIdusuario() != usuario.getIdusuario()) {
                    listaAutores.add(usuarioAux);
                    System.out.println();
                }
            }
            for (Libro libro : listaLibros) {
                Map<String, String> item = new HashMap<>();
                if (idsLibroSet.contains(libro.getIdLibro().toString())) {
                    item.put("tipo", "Libro");
                    item.put("ruta", "libro/" + proyectoInvestigacion.getIdProyectoInvestigacion().toString()
                            + "/" + usuario.getIdusuario().toString() + "/" + libro.getIdLibro().toString());
                    item.put("titulo", libro.getTitulo());
                    item.put("anio", libro.getAnio());
                    produccionesConsolidadas.add(item);
                } else {
                    listaLibrosAux.add(libro);
                }
            }
            // Consolidar capítulos de libros
            for (CapituloLibro capitulo : listaCapitulos) {
                if (idsCapitulosLibroSet.contains(capitulo.getIdCapitulo().toString())) {
                    Map<String, String> item = new HashMap<>();
                    item.put("tipo", "Capítulo de libro");
                    item.put("ruta", "capLibro/" + proyectoInvestigacion.getIdProyectoInvestigacion().toString()
                            + "/" + usuario.getIdusuario().toString() + "/" + capitulo.getIdCapitulo().toString());
                    item.put("titulo", capitulo.getTitulo());
                    item.put("anio", capitulo.getAnio().toString());
                    produccionesConsolidadas.add(item);
                } else {
                    listaCapitulosAux.add(capitulo);
                }
            }

            // Consolidar artículos
            for (Articulo articulo : listaArticulos) {
                if (idsArticuloSet.contains(articulo.getIdArticulo().toString())) {
                    Map<String, String> item = new HashMap<>();
                    item.put("tipo", "Artículo");
                    item.put("ruta", "articulo/" + proyectoInvestigacion.getIdProyectoInvestigacion().toString()
                            + "/" + usuario.getIdusuario().toString() + "/" + articulo.getIdArticulo().toString());
                    item.put("titulo", articulo.getTitulo());
                    item.put("anio", articulo.getAnio().toString());
                    produccionesConsolidadas.add(item);
                } else {
                    listaArticulosAux.add(articulo);
                }
            }

            // Consolidar demás trabajos
            for (DemasTrabajo demasTrabajo : listademasTrabajos) {
                if (idsDemasTrabajosSet.contains(demasTrabajo.getIdDemasTrabajo().toString())) {
                    Map<String, String> item = new HashMap<>();
                    item.put("tipo", "Demás Trabajos");
                    item.put("ruta", "demTrabajo/" + proyectoInvestigacion.getIdProyectoInvestigacion().toString()
                            + "/" + usuario.getIdusuario().toString() + "/" + demasTrabajo.getIdDemasTrabajo().toString());
                    item.put("titulo", demasTrabajo.getNombreProducto()); // Valor por defecto para título
                    item.put("anio", demasTrabajo.getAnio().toString());
                    produccionesConsolidadas.add(item);
                } else {
                    listademasTrabajosAux.add(demasTrabajo);
                }
            }
            session.setAttribute("produccionesConsolidadas", produccionesConsolidadas);
            session.setAttribute("listaLibros", listaLibrosAux);
            session.setAttribute("listaCapitulos", listaCapitulosAux);
            session.setAttribute("listaArticulos", listaArticulosAux);
            session.setAttribute("listademasTrabajos", listademasTrabajosAux);
            mav.addObject("autoresList", listaAutores);
            mav.addObject("listaCoautores", coautores);
            mav.addObject("proyectoInvestigacion", proyectoInvestigacion);
            mav.addObject("usuario", usuario);
            mav.addObject("usuarioCoautor", new Usuario());
            mav.setViewName("verDetallesProyectoInvestigacion");
            return mav;
        } else {
            System.out.println("error de logueo");
            session.setAttribute("usuario", new Usuario());
            mav.addObject("usuario", session.getAttribute("usuario"));
            mav.setViewName("redirect:/");
            return mav;
        }
    }

    @RequestMapping("/eliminar/{idProyectoInvestigacion}")
    public ModelAndView eliminar(HttpServletRequest request, @PathVariable(value = "idProyectoInvestigacion") Long idProyectoInvestigacion) {
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (session.getAttribute("usuario") != null) {
            try {
                proyectoServicio.eliminarRegistroAutoresProyectoInvestigacion(idProyectoInvestigacion, usuario.getIdusuario());
                proyectoServicio.eliminarRegistroCoautoresProyectoInvestigacion(idProyectoInvestigacion, usuario.getIdusuario());
                proyectoServicio.eliminarProyectoInvestigacion(idProyectoInvestigacion);
                mav.setViewName("redirect:/proyectosInvestigacion/verProyectosInvestigacion");
                return mav;
            } catch (Exception e) {
                e.printStackTrace();
                mav.setViewName("redirect:/proyectosInvestigacion/verProyectosInvestigacion" );
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
    @PostMapping("/agregarCoautor")
    public ModelAndView insertarCoautor(HttpServletRequest request, @RequestParam("idCoautor") Long idCoautor,
                                        @RequestParam("idProyectoInvestigacion") Long idProyectoInvestigacion) throws Exception {
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (session.getAttribute("usuario") != null) {
            try {
                proyectoServicio.agregarRegistroCoautorProyectoInvestigacion(idProyectoInvestigacion, usuario.getIdusuario(), idCoautor);
                mav.setViewName("redirect:/proyectosInvestigacion/detalle/"+idProyectoInvestigacion);
                return mav;
            } catch (Exception e) {
                e.printStackTrace();
                mav.setViewName("redirect:/proyectosInvestigacion/verProyectosInvestigacion" );
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

    @GetMapping("/eliminarCoautor/{idProyectoInvestigacion}/{idCoautor}")
    public ModelAndView eliminarCoautor(HttpServletRequest request, @PathVariable("idCoautor") Long idCoautor,
                                        @PathVariable("idProyectoInvestigacion") Long idProyectoInvestigacion) throws Exception {
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        if (session.getAttribute("usuario") != null) {
            proyectoServicio.eliminarRegistroCoautorProyectoInvestigacion(idProyectoInvestigacion, idCoautor);
            mav.setViewName("redirect:/proyectosInvestigacion/detalle/"+idProyectoInvestigacion);
            return mav;
        } else {
            System.out.println("error de logueo");
            session.setAttribute("usuario", new Usuario());
            mav.addObject("usuario", session.getAttribute("usuario"));
            mav.setViewName("redirect:/");
            return mav;
        }
    }
    @PostMapping("/vincularProduccion")
    public ModelAndView vincularProduccion(HttpServletRequest request, @RequestParam("idProyectoInvestigacion") Long idProyectoInvestigacion,
                                           @RequestParam(value = "tipo_libro", required = false, defaultValue = "") String idsLibros,
                                           @RequestParam(value = "tipo_cap_libro", required = false, defaultValue = "") String idsCapitulosLibros,
                                           @RequestParam(value = "tipo_articulo", required = false, defaultValue = "") String idsArticulos,
                                           @RequestParam(value = "tipo_demas_trabajo", required = false, defaultValue = "") String idsDemasTrabajo) throws Exception {

        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        Map<String, Object> produccionesMap;
        ProyectoInvestigacion proyecto = proyectoServicio.buscarPorId(idProyectoInvestigacion);

        Set<String> idsLibroSet = Arrays.stream(idsLibros.split(","))
                .filter(id -> !id.trim().isEmpty())
                .collect(Collectors.toSet());
        Set<String> idsCapitulosLibroSet = Arrays.stream(idsCapitulosLibros.split(","))
                .filter(id -> !id.trim().isEmpty())
                .collect(Collectors.toSet());
        Set<String> idsArticuloSet = Arrays.stream(idsArticulos.split(","))
                .filter(id -> !id.trim().isEmpty())
                .collect(Collectors.toSet());
        Set<String> idsDemasTrabajosSet = Arrays.stream(idsDemasTrabajo.split(","))
                .filter(id -> !id.trim().isEmpty())
                .collect(Collectors.toSet());

        if (proyecto.getJsonProducciones() != null && !proyecto.getJsonProducciones().isEmpty()) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                produccionesMap = objectMapper.readValue(proyecto.getJsonProducciones(), new TypeReference<HashMap<String, Object>>() {});
            } catch (IOException e) {
                produccionesMap = new HashMap<>();
            }

            // Obtener las cadenas de IDs adicionales del mapa
            String idsLibrosAux = (String) produccionesMap.get("idsLibros");
            String idsCapitulosLibrosAux = (String) produccionesMap.get("idsCapitulosLibros");
            String idsArticulosAux = (String) produccionesMap.get("idsArticulos");
            String idsDemasTrabajoAux = (String) produccionesMap.get("idsDemasTrabajo");

            // Convertir las cadenas adicionales a conjuntos
            Set<String> idsLibrosAuxSet = Arrays.stream(idsLibrosAux.split(","))
                    .filter(id -> !id.trim().isEmpty())
                    .collect(Collectors.toSet());
            Set<String> idsCapitulosLibrosAuxSet = Arrays.stream(idsCapitulosLibrosAux.split(","))
                    .filter(id -> !id.trim().isEmpty())
                    .collect(Collectors.toSet());
            Set<String> idsArticulosAuxSet = Arrays.stream(idsArticulosAux.split(","))
                    .filter(id -> !id.trim().isEmpty())
                    .collect(Collectors.toSet());
            Set<String> idsDemasTrabajoAuxSet = Arrays.stream(idsDemasTrabajoAux.split(","))
                    .filter(id -> !id.trim().isEmpty())
                    .collect(Collectors.toSet());

            // Combinar los conjuntos iniciales con los conjuntos adicionales
            idsLibroSet.addAll(idsLibrosAuxSet);
            idsCapitulosLibroSet.addAll(idsCapitulosLibrosAuxSet);
            idsArticuloSet.addAll(idsArticulosAuxSet);
            idsDemasTrabajosSet.addAll(idsDemasTrabajoAuxSet);
        }

        // Crear el mapa con los conjuntos combinados convertidos a cadenas
        Map<String, Object> idsProduccionesVinculadas = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        idsProduccionesVinculadas.put("idsLibros", String.join(",", idsLibroSet));
        idsProduccionesVinculadas.put("idsCapitulosLibros", String.join(",", idsCapitulosLibroSet));
        idsProduccionesVinculadas.put("idsArticulos", String.join(",", idsArticuloSet));
        idsProduccionesVinculadas.put("idsDemasTrabajo", String.join(",", idsDemasTrabajosSet));
        // Convertir el mapa de nuevo a JSON
        String idsProducciones = objectMapper.writeValueAsString(idsProduccionesVinculadas);

        if (session.getAttribute("usuario") != null) {
            Boolean result = proyectoServicio.vincularProducciones(idProyectoInvestigacion, idsProducciones);
            mav.setViewName("redirect:/proyectosInvestigacion/detalle/" + idProyectoInvestigacion + "?exito=" + result);
            return mav;
        } else {
            System.out.println("error de logueo");
            session.setAttribute("usuario", new Usuario());
            mav.addObject("usuario", session.getAttribute("usuario"));
            mav.setViewName("redirect:/");
            return mav;
        }
    }

    @GetMapping("/desvincular/{tipo}/{idProyecto}/{idUsuario}/{idProduccion}")
    public ModelAndView desvincularProduccion(HttpServletRequest request, @PathVariable("tipo") String tipo,
                                              @PathVariable("idProyecto") Long idProyectoInvestigacion,
                                              @PathVariable("idUsuario") Long idUsuario,
                                              @PathVariable("idProduccion") Long idProduccion) throws Exception {
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        // Obtener el proyecto
        ProyectoInvestigacion proyecto = proyectoServicio.buscarPorId(idProyectoInvestigacion);
        if (session.getAttribute("usuario") != null) {

        }
        // Obtener el JSON de producciones
        String jsonProducciones = proyecto.getJsonProducciones();
        Map<String, Object> produccionesMap;
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            produccionesMap = objectMapper.readValue(jsonProducciones, new TypeReference<HashMap<String, Object>>() {});
        } catch (IOException e) {
            produccionesMap = new HashMap<>();
        }

        // Determinar el conjunto correcto a modificar basado en el tipo
        Set<String> idsSet = new HashSet<>();
        String key = "";

        switch (tipo) {
            case "libro":
                key = "idsLibros";
                break;
            case "capLibro":
                key = "idsCapitulosLibros";
                break;
            case "articulo":
                key = "idsArticulos";
                break;
            case "demasTrabajo":
                key = "idsDemasTrabajo";
                break;
            default:
                mav.setViewName("redirect:/proyectosInvestigacion/detalle/" + idProyectoInvestigacion + "?error=" + "Tipo no válido");
                return mav;
        }

        // Convertir la cadena de IDs a un conjunto
        if (produccionesMap.containsKey(key)) {
            String idsString = (String) produccionesMap.get(key);
            idsSet = Arrays.stream(idsString.split(","))
                    .filter(id -> !id.trim().isEmpty())
                    .collect(Collectors.toSet());
        }

        // Eliminar el ID de la producción
        idsSet.remove(idProduccion.toString());

        // Actualizar el mapa de producciones
        produccionesMap.put(key, String.join(",", idsSet));

        try {
            // Convertir el mapa de nuevo a JSON
            String updatedJsonProducciones = objectMapper.writeValueAsString(produccionesMap);
            System.out.println(updatedJsonProducciones);
            // Actualizar el JSON en el proyecto
            proyecto.setJsonProducciones(updatedJsonProducciones);
            proyectoServicio.vincularProducciones(idProyectoInvestigacion, updatedJsonProducciones);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        mav.setViewName("redirect:/proyectosInvestigacion/detalle/" + idProyectoInvestigacion + "?exito=" + "true");
        return mav;
    }

}
