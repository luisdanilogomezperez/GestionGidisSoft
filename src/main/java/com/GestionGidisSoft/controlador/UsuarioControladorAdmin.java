package com.GestionGidisSoft.controlador;

import com.GestionGidisSoft.DTO.LoginRequestDto;
import com.GestionGidisSoft.DTO.LoginResponseDto;
import com.GestionGidisSoft.DTO.RegistroResponseDto;
import com.GestionGidisSoft.entidades.*;
import com.GestionGidisSoft.servicios.*;
import com.GestionGidisSoft.servicios.impl.DemasTrabajoServicioImpl;
import com.GestionGidisSoft.servicios.impl.EventoServicioImpl;
import com.GestionGidisSoft.servicios.impl.PonenciaServicioImpl;
import com.GestionGidisSoft.servicios.impl.ProyectoDirigidoServicioImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/usuarios/admin")
public class UsuarioControladorAdmin {

    @Autowired
    private Utils util;
    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private ArticuloServicio articuloServicio;
    @Autowired
    private LibroServico libroServico;
    @Autowired
    private CapituloLibroServicio capituloLibroServicio;
    @Autowired
    private ProyectoInvestigacionServicio proyectoServicio;
    @Autowired
    private ProyectoDirigidoServicioImpl proyectoDirigidoServicio;
    @Autowired
    private EventoServicioImpl eventoServicio;
    @Autowired
    private PonenciaServicioImpl ponenciaServicio;
    @Autowired
    private DemasTrabajoServicioImpl demasTrabajoServicio;


    @GetMapping("/listarUsuarios")
    public ModelAndView listarUsuarios(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null) {
            mav.addObject("usuario", usuario);
            List<Usuario> listaUsuarios =  usuarioServicio.listarUsuarios(usuario.getIdusuario());
            if (listaUsuarios.isEmpty()){
                mav.addObject("mensaje", "Aún no hay registros");
            }
            mav.addObject("listaUsuarios", listaUsuarios);
            mav.setViewName("gestionUsuarios/listarUsuarios");
            return mav;
        } else {
            System.out.println("error de logueo");
            request.removeAttribute("usuario");
            mav.setViewName("redirect:/");
            return mav;
        }
    }

    @PostMapping("/cambiarEstado")
    public ModelAndView cambiarEstadoUsuarios(HttpServletRequest request, @RequestParam("idUsuario") Long idUsuario,
                                              @RequestParam("enable") String enable) throws Exception {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null) {
            boolean estado;
            if ("true".equals(enable)) {
                estado = false;
            } else {
                estado = true;
            }
            String estadoUsuario = usuarioServicio.actualizarUsuario(idUsuario, estado);
            mav.addObject("mensajeExito", estadoUsuario);
            List<Usuario> listaUsuarios =  usuarioServicio.listarUsuarios(usuario.getIdusuario());
            mav.addObject("listaUsuarios", listaUsuarios);
            mav.setViewName("gestionUsuarios/listarUsuarios");
            return mav;
        } else {
            System.out.println("error de logueo");
            request.removeAttribute("usuario");
            mav.setViewName("redirect:/");
            return mav;
        }
    }

    @PostMapping("/guardar")
    public ModelAndView guardarUsuario(@ModelAttribute("usuario") Usuario usuario){
        ModelAndView model = new ModelAndView();
        boolean existenUsuarios = false;
        try {
            Rol rol = new Rol();
            if (usuarioServicio.existenUsuarios()) {
                rol.setRolId(2L);
                rol.setNombre("DOCENTE");
            } else {
                rol.setRolId(1L);
                rol.setNombre("ADMIN");
            }
            usuario.setEnable(true);
            usuarioServicio.guardarUsuario(usuario, rol);

            model.setViewName("redirect:/?registroExitoso=true");
            return model;
            //     return new ResponseEntity(new RegistroResponseDto(true),HttpStatus.CREATED);
        }catch (Exception e){
            model.addObject("mensaje", "ha ocurrido un error");
            model.setViewName("redirect:registrarse?error=true");
            return model;
            // return new ResponseEntity(new RegistroResponseDto(false),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/buscar/{username}")
    public Usuario buscarUsuario(@PathVariable("username") String username){
        return  usuarioServicio.buscarUsuario(username);
    }

    @DeleteMapping("/eliminar/{usuarioId}")
    public void eliminarUsuario(@PathVariable("usuarioId")  Long usuarioId){
        usuarioServicio.eliminar(usuarioId);
    }

    @GetMapping("/home")
    public ModelAndView goHomeGidis(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null) {
            mav.addObject("usuario", usuario);
            Rol usuarioRol = usuarioServicio.consultarRolUsuario(usuario.getIdusuario());
            if (usuarioRol.getNombre().equals("ADMIN")) {
                mav.setViewName("inicioAdmin");
            } else {
                mav.setViewName("inicio");
            }
            return mav;
        } else {
            System.out.println("error de logueo");
            request.removeAttribute("usuario");
            mav.setViewName("redirect:/");
            return mav;
        }
    }
    @GetMapping("/verProductos/{idUsuario}")
    public ModelAndView verTodosLosProductos(HttpServletRequest request, @PathVariable("idUsuario")  Long idUsuario) throws Exception {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null) {

            Usuario usuarioConsultado = usuarioServicio.getByUsuarioId(idUsuario);
            List<Articulo> listaArticulos = articuloServicio.findByUsuarioId(idUsuario);
            List<Libro> listaLibros = libroServico.findByUsuarioId(idUsuario);
            List<ProyectoInvestigacion> listaProyectos = proyectoServicio.findByUsuarioId(idUsuario);
            List<CapituloLibro> listacapituloLibros = capituloLibroServicio.findByUsuarioId(idUsuario);
            List<CapituloLibro> listacapituloLibrosVista = new ArrayList<>();
            List<DemasTrabajo> listaDemasTrabajos = demasTrabajoServicio.findByUsuarioId(idUsuario);
            List<Ponencia> listaPonencias = ponenciaServicio.findByUsuarioId(idUsuario);
            List<Evento> listaEventos = eventoServicio.findByUsuarioId(idUsuario);
            List<ProyectoDirigido> listaProyectoDirigidos = proyectoDirigidoServicio.findByUsuarioId(idUsuario);

            if (!listacapituloLibros.isEmpty()){
                for (CapituloLibro capituloLibro : listacapituloLibros) {
                    Libro libro = libroServico.buscarPorId(capituloLibro.getIdLibro());
                    capituloLibro.setTituloLibro(libro.getTitulo());
                    listacapituloLibrosVista.add(capituloLibro);
                }
            }
            if (listaArticulos.isEmpty()){
                mav.addObject("mensajeArticulos", "Aún no hay registros");
            }  else {
                List<Articulo> articulosAux = new ArrayList();
                for (Articulo articulo : listaArticulos) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    Map<String, Object> lugarMap;
                    try {
                        lugarMap = objectMapper.readValue(articulo.getLugarPublicacion(), new TypeReference<HashMap<String, Object>>() {});
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
                listaArticulos = articulosAux;
            }
            if (listaLibros.isEmpty()){
                mav.addObject("mensajeLibros", "Aún no hay registros");
            }
            if (listacapituloLibros.isEmpty()){
                mav.addObject("mensajeCapitulos", "Aún no hay registros");
            }

            mav.addObject("usuarioConsultado", usuarioConsultado);
            mav.addObject("listaArticulos", listaArticulos);
            mav.addObject("listaLibros", listaLibros);
            mav.addObject("listaCapitulosLibros", listacapituloLibrosVista);
            mav.addObject("listaProyectos", listaProyectos);
            mav.addObject("listaProyectoDirigidos", listaProyectoDirigidos);
            mav.addObject("listaEventos", listaEventos);
            mav.addObject("listaPonencias", listaPonencias);
            mav.addObject("listaDemasTrabajos", listaDemasTrabajos);
            mav.setViewName("gestionUsuarios/listarProductosUsuario");

            return mav;
        } else {
            System.out.println("error de logueo");
            request.removeAttribute("usuario");
            mav.setViewName("redirect:/");
            return mav;
        }
    }
    @RequestMapping("/detallesArticulo/{idArticulo}/{idUsuario}")
    public ModelAndView verDetallesArticulo(HttpServletRequest request, @PathVariable(value = "idArticulo") long idArticulo,
                                            @PathVariable(value = "idUsuario") long idUsuario) throws Exception {
        List<Usuario> listaAutores = new ArrayList();
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        try {
            if (session.getAttribute("usuario") != null) {
                Usuario usuario = (Usuario) session.getAttribute("usuario");
                Usuario usuarioConsultado = usuarioServicio.getByUsuarioId(idUsuario);
                List<Usuario> coautores = usuarioServicio.listarCoautoresArticulos(idArticulo, usuario.getIdusuario());
                List<Usuario> autores = usuarioServicio.listarAutoresArticulos(idArticulo, usuario.getIdusuario());
                Articulo articulo = articuloServicio.buscarPorId(idArticulo);
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Object> lugarMap;
                try {
                    lugarMap = objectMapper.readValue(articulo.getLugarPublicacion(), new TypeReference<HashMap<String, Object>>() {});
                } catch (IOException e) {
                    lugarMap = new HashMap<>();
                }
                Pais pais = util.obtenerPais((String) lugarMap.get("pais"));
                String idioma = util.obtenerNombreIdioma(articulo.getIdioma());
                articulo.setIdioma(idioma);
                articulo.setPaisPublicacion(pais.getName());
                articulo.setCiudadPublicacion((String) lugarMap.get("ciudad"));
                if (autores != null) {
                    for (Usuario usuario1: autores) {
                        if (usuario1.getIdusuario() != usuario.getIdusuario()) {
                            listaAutores.add(usuario1);
                        }
                    }
                }

                mav.addObject("usuarioConsultado", usuarioConsultado);
                mav.addObject("autoresList", listaAutores);
                mav.addObject("listaCoautores", coautores);
                mav.addObject("articulo", articulo);
                mav.addObject("usuario", usuario);
                mav.addObject("usuarioCoautor", new Usuario());
                mav.setViewName("gestionUsuarios/layout/verDetallesArticulo");
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
            System.out.println(":: Error inesperado :: " + e.getMessage());
            mav.setViewName("redirect:/usuarios/admin/listarUsuarios");
            return mav;
        }
    }

    @RequestMapping("/detallesLibro/{idLibro}/{idUsuario}")
    public ModelAndView verDetallesLibro(HttpServletRequest request, @PathVariable(value = "idLibro") long idLibro,
                                 @PathVariable(value = "idUsuario") long idUsuario) {
        List<Usuario> listaAutores = new ArrayList();
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        if (session.getAttribute("usuario") != null) {
            Usuario usuario = (Usuario) session.getAttribute("usuario");

            Usuario usuarioConsultado = usuarioServicio.getByUsuarioId(idUsuario);
            List<Usuario> coautores = usuarioServicio.listarCoautoresLibros(idLibro, idUsuario);
            List<Usuario> autores = usuarioServicio.listarAutoresLibros(idLibro, idUsuario);
            Libro libro = libroServico.buscarPorId(idLibro);
            for (Usuario usuario1: autores) {
                if (usuario1.getIdusuario() != idUsuario) {
                    listaAutores.add(usuario1);
                }
            }
            mav.addObject("usuarioConsultado", usuarioConsultado);
            mav.addObject("autoresList", listaAutores);
            mav.addObject("listaCoautores", coautores);
            mav.addObject("libro", libro);
            mav.addObject("usuario", usuario);
            mav.addObject("usuarioCoautor", new Usuario());
            mav.setViewName("gestionUsuarios/layout/verDetallesLibro");
            return mav;
        } else {
            System.out.println("error de logueo");
            session.setAttribute("usuario", new Usuario());
            mav.addObject("usuario", session.getAttribute("usuario"));
            mav.setViewName("redirect:/");
            return mav;
        }
    }

    @RequestMapping("/detalleCapituloLibro/{idCapituloLibro}/{idUsuario}")
    public ModelAndView verDetallesCapituloLibro(HttpServletRequest request, @PathVariable(value = "idCapituloLibro") long idCapituloLibro,
                                @PathVariable(value = "idUsuario") long idUsuario) {
        List<Usuario> listaAutores = new ArrayList();
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        if (session.getAttribute("usuario") != null) {
            Usuario usuario = (Usuario) session.getAttribute("usuario");

            Usuario usuarioConsultado = usuarioServicio.getByUsuarioId(idUsuario);
            List<Usuario> coautores = usuarioServicio.listarCoautoresCapitulosLibros(idCapituloLibro, idUsuario);
            List<Usuario> autores = usuarioServicio.listarAutoresCapitulosLibros(idCapituloLibro, idUsuario);
            CapituloLibro capituloLibro = capituloLibroServicio.buscarPorId(idCapituloLibro);
            Libro libro = libroServico.buscarPorId(capituloLibro.getIdLibro());
            capituloLibro.setTituloLibro(libro.getTitulo());
            if (autores != null) {
                for (Usuario usuario1: autores) {
                    if (usuario1.getIdusuario() != idUsuario) {
                        listaAutores.add(usuario1);
                    }
                }
            }
            mav.addObject("usuarioConsultado", usuarioConsultado);
            mav.addObject("autoresList", listaAutores);
            mav.addObject("listaCoautores", coautores);
            mav.addObject("capituloLibro", capituloLibro);
            mav.addObject("usuario", usuario);
            mav.addObject("usuarioCoautor", new Usuario());
            mav.setViewName("gestionUsuarios/layout/verDetallesCapituloLibro");
            return mav;
        } else {
            System.out.println("error de logueo");
            session.setAttribute("usuario", new Usuario());
            mav.addObject("usuario", session.getAttribute("usuario"));
            mav.setViewName("redirect:/");
            return mav;
        }
    }

    @RequestMapping("/detalleProyectoInvestigacion/{idProyectoInvestigacion}/{idUsuario}")
    public ModelAndView verDetallesProyectoInvestigacion(HttpServletRequest request, @PathVariable(value = "idProyectoInvestigacion") long idProyectoInvestigacion,
                                 @PathVariable(value = "idUsuario") long idUsuario) throws Exception {
        List<Usuario> listaAutores = new ArrayList();
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        if (session.getAttribute("usuario") != null) {
            Usuario usuario = (Usuario) session.getAttribute("usuario");

            Usuario usuarioConsultado = usuarioServicio.getByUsuarioId(idUsuario);
            List<Usuario> coautores = usuarioServicio.listarCoautoresProyectosInvestigacion(idProyectoInvestigacion, idUsuario);
            List<Usuario> autores = usuarioServicio.listarAutoresProyectosInvestigacion(idProyectoInvestigacion, idUsuario);
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
            List<Libro> listaLibros = libroServico.findByUsuarioId(idUsuario);
            List<Articulo> listaArticulos = articuloServicio.findByUsuarioId(idUsuario);
            List<CapituloLibro> listaCapitulos = capituloLibroServicio.findByUsuarioId(idUsuario);
            List<DemasTrabajo> listademasTrabajos = demasTrabajoServicio.findByUsuarioId(idUsuario);

            for (Usuario usuarioAux: autores) {
                if (usuarioAux.getIdusuario() != idUsuario) {
                    listaAutores.add(usuarioAux);
                }
            }
            for (Libro libro : listaLibros) {
                Map<String, String> item = new HashMap<>();
                if (idsLibroSet.contains(libro.getIdLibro().toString())) {
                    item.put("tipo", "Libro");
                    item.put("ruta", "libro/" + proyectoInvestigacion.getIdProyectoInvestigacion().toString()
                            + "/" + idUsuario + "/" + libro.getIdLibro().toString());
                    item.put("titulo", libro.getTitulo());
                    item.put("anio", libro.getAnio());
                    produccionesConsolidadas.add(item);
                }
            }
            // Consolidar capítulos de libros
            for (CapituloLibro capitulo : listaCapitulos) {
                if (idsCapitulosLibroSet.contains(capitulo.getIdCapitulo().toString())) {
                    Map<String, String> item = new HashMap<>();
                    item.put("tipo", "Capítulo de libro");
                    item.put("ruta", "capLibro/" + proyectoInvestigacion.getIdProyectoInvestigacion().toString()
                            + "/" + idUsuario + "/" + capitulo.getIdCapitulo().toString());
                    item.put("titulo", capitulo.getTitulo());
                    item.put("anio", capitulo.getAnio().toString());
                    produccionesConsolidadas.add(item);
                }
            }

            // Consolidar artículos
            for (Articulo articulo : listaArticulos) {
                if (idsArticuloSet.contains(articulo.getIdArticulo().toString())) {
                    Map<String, String> item = new HashMap<>();
                    item.put("tipo", "Artículo");
                    item.put("ruta", "articulo/" + proyectoInvestigacion.getIdProyectoInvestigacion().toString()
                            + "/" + idUsuario + "/" + articulo.getIdArticulo().toString());
                    item.put("titulo", articulo.getTitulo());
                    item.put("anio", articulo.getAnio().toString());
                    produccionesConsolidadas.add(item);
                }
            }

            // Consolidar demás trabajos
            for (DemasTrabajo demasTrabajo : listademasTrabajos) {
                if (idsDemasTrabajosSet.contains(demasTrabajo.getIdDemasTrabajo().toString())) {
                    Map<String, String> item = new HashMap<>();
                    item.put("tipo", "Demás Trabajos");
                    item.put("ruta", "demTrabajo/" + proyectoInvestigacion.getIdProyectoInvestigacion().toString()
                            + "/" + idUsuario + "/" + demasTrabajo.getIdDemasTrabajo().toString());
                    item.put("titulo", demasTrabajo.getNombreProducto()); // Valor por defecto para título
                    item.put("anio", demasTrabajo.getAnio().toString());
                    produccionesConsolidadas.add(item);
                }
            }
            session.setAttribute("produccionesConsolidadas", produccionesConsolidadas);
            mav.addObject("autoresList", listaAutores);
            mav.addObject("listaCoautores", coautores);
            mav.addObject("proyectoInvestigacion", proyectoInvestigacion);
            mav.addObject("usuario", usuario);
            mav.addObject("usuarioConsultado", usuarioConsultado);
            mav.addObject("usuarioCoautor", new Usuario());
            mav.setViewName("gestionUsuarios/layout/verDetallesProyectoInvestigacion");
            return mav;
        } else {
            System.out.println("error de logueo");
            session.setAttribute("usuario", new Usuario());
            mav.addObject("usuario", session.getAttribute("usuario"));
            mav.setViewName("redirect:/");
            return mav;
        }
    }

    @GetMapping("/detalleProyectoDirigido/{idProyectoDirigido}/{idUsuario}")
    public ModelAndView verDetallesProyectoDirigido(HttpServletRequest request, @PathVariable(value = "idProyectoDirigido") long idProyectoDirigido,
                                                    @PathVariable(value = "idUsuario") long idUsuario) {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        if (session.getAttribute("usuario") != null) {
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            Usuario usuarioConsultado = usuarioServicio.getByUsuarioId(idUsuario);
            ProyectoDirigido proyectoDirigido = proyectoDirigidoServicio.buscarProyectoDirigidoPorId(idProyectoDirigido);

            mav.addObject("usuarioConsultado", usuarioConsultado);
            mav.addObject("proyectoDirigido", proyectoDirigido);
            mav.addObject("usuario", usuario);
            mav.setViewName("gestionUsuarios/layout/verDetallesProyectoDirigido");
            return mav;
        } else {
            System.out.println("error de logueo");
            session.setAttribute("usuario", new Usuario());
            mav.addObject("usuario", session.getAttribute("usuario"));
            mav.setViewName("redirect:/");
            return mav;
        }
    }

    @GetMapping("/detalleEvento/{idEvento}/{idUsuario}")
    public ModelAndView verDetallesEvento(HttpServletRequest request, @PathVariable(value = "idEvento") long idEvento,
                                          @PathVariable(value = "idUsuario") long idUsuario) {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        if (session.getAttribute("usuario") != null) {
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            Usuario usuarioConsultado = usuarioServicio.getByUsuarioId(idUsuario);
            Evento evento = eventoServicio.buscarEventoPorId(idEvento);

            mav.addObject("usuarioConsultado", usuarioConsultado);
            mav.addObject("evento", evento);
            mav.addObject("usuario", usuario);
            mav.setViewName("gestionUsuarios/layout/verDetallesEvento");
            return mav;
        } else {
            System.out.println("error de logueo");
            session.setAttribute("usuario", new Usuario());
            mav.addObject("usuario", session.getAttribute("usuario"));
            mav.setViewName("redirect:/");
            return mav;
        }
    }

    @GetMapping("/detallePonencia/{idPonencia}/{idUsuario}")
    public ModelAndView verDetallesPonencia(HttpServletRequest request, @PathVariable(value = "idPonencia") long idPonencia,
                                            @PathVariable(value = "idUsuario") long idUsuario) {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        if (session.getAttribute("usuario") != null) {
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            Usuario usuarioConsultado = usuarioServicio.getByUsuarioId(idUsuario);
            Ponencia ponencia = ponenciaServicio.buscarPonenciaPorId(idPonencia);

            mav.addObject("usuarioConsultado", usuarioConsultado);
            mav.addObject("ponencia", ponencia);
            mav.addObject("usuario", usuario);
            mav.setViewName("gestionUsuarios/layout/verDetallesPonencia");
            return mav;
        } else {
            System.out.println("error de logueo");
            session.setAttribute("usuario", new Usuario());
            mav.addObject("usuario", session.getAttribute("usuario"));
            mav.setViewName("redirect:/");
            return mav;
        }
    }

    @GetMapping("/detalleDemasTrabajo/{idDemasTrabajo}/{idUsuario}")
    public ModelAndView verDetalleDemasTrabajo(HttpServletRequest request, @PathVariable(value = "idDemasTrabajo") long idDemasTrabajo,
                                 @PathVariable(value = "idUsuario") long idUsuario) {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        if (session.getAttribute("usuario") != null) {
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            Usuario usuarioConsultado = usuarioServicio.getByUsuarioId(idUsuario);
            DemasTrabajo demasTrabajo = demasTrabajoServicio.buscarDemasTrabajoPorId(idDemasTrabajo);

            mav.addObject("usuarioConsultado", usuarioConsultado);
            mav.addObject("demasTrabajo", demasTrabajo);
            mav.addObject("usuario", usuario);
            mav.setViewName("gestionUsuarios/layout/verDetallesDemasTrabajo");
            return mav;
        } else {
            System.out.println("error de logueo");
            session.setAttribute("usuario", new Usuario());
            mav.addObject("usuario", session.getAttribute("usuario"));
            mav.setViewName("redirect:/");
            return mav;
        }
    }
}
