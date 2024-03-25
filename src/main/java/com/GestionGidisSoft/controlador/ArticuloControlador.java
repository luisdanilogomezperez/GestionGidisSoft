package com.GestionGidisSoft.controlador;

import com.GestionGidisSoft.Constantes.Format;
import com.GestionGidisSoft.entidades.*;
import com.GestionGidisSoft.servicios.ArchivosServicio;
import com.GestionGidisSoft.servicios.ArticuloServicio;
import com.GestionGidisSoft.servicios.UsuarioServicio;
import com.GestionGidisSoft.servicios.Utils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.sql.SQLException;
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
                    articulos = articulosAux;
                }
                mav.addObject("listaArticulos", articulos);
                mav.addObject("usuario", usuario);
                mav.setViewName("listarArticulos");
                return mav;
            } else {
                System.out.println(":: error de logueo ::");
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
                articulo.setLugarPublicacion(lugarJson);
                Usuario usuario = (Usuario) session.getAttribute("usuario");
                articuloServicio.guardarArticulo(articulo);
                articuloServicio.agregarRegistroAutorArticulo(articulo.getIdArticulo(), usuario.getIdusuario());
                mav.setViewName("redirect:/articulos/verArticulos");
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

    @RequestMapping("/editar/{idArticulo}")
    public ModelAndView goUpdate(HttpServletRequest request, @PathVariable(value = "idArticulo") Long idArticulo) throws Exception {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();

        if (session.getAttribute("usuario") != null) {
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            Articulo articulo = articuloServicio.buscarPorId(idArticulo);
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> lugarMap;
            try {
                lugarMap = objectMapper.readValue(articulo.getLugarPublicacion(), new TypeReference<HashMap<String, Object>>() {});
            } catch (IOException e) {
                lugarMap = new HashMap<>();
            }
            articulo.setPaisPublicacion((String) lugarMap.get("pais"));
            articulo.setCiudadPublicacion((String) lugarMap.get("ciudad"));
            mav.addObject("articulo", articulo);
            mav.setViewName("editarArticulo");
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
    public ModelAndView actualizarArticulo(HttpServletRequest request, @ModelAttribute("articulo") Articulo articulo,
                                           @RequestParam(value = "pais") String pais,
                                           @RequestParam(value = "ciudad") String ciudad) throws Exception {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        //verifica que no haya un libro con el mismo DOI
        if (session.getAttribute("usuario") != null) {
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
            articulo.setLugarPublicacion(lugarJson);
            articuloServicio.actualizarArticulo(articulo);
            mav.setViewName("redirect:/articulos/verArticulos");
            return mav;

        } else {
            session.setAttribute("usuario", new Usuario());
            mav.addObject("usuario", session.getAttribute("usuario"));
            mav.setViewName("redirect:/");
            return mav;
        }
    }

    @RequestMapping("/detalle/{idArticulo}")
    public ModelAndView goDetail(HttpServletRequest request, @PathVariable(value = "idArticulo") long idArticulo) throws Exception {
        List<Usuario> listaAutores = new ArrayList();
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        try {
            if (session.getAttribute("usuario") != null) {
                Usuario usuario = (Usuario) session.getAttribute("usuario");
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
                mav.addObject("autoresList", listaAutores);
                mav.addObject("listaCoautores", coautores);
                mav.addObject("articulo", articulo);
                mav.addObject("usuario", usuario);
                mav.addObject("usuarioCoautor", new Usuario());
                mav.setViewName("verDetallesArticulo");
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
            mav.setViewName("redirect:/articulos/verArticulos");
            return mav;
        }
    }

    @RequestMapping("/eliminar/{idArticulo}")
    public ModelAndView eliminar(HttpServletRequest request, @PathVariable(value = "idArticulo") Long idArticulo) {
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (session.getAttribute("usuario") != null) {
            try {
                articuloServicio.eliminarRegistroAutoresArticulo(idArticulo, usuario.getIdusuario());
                articuloServicio.eliminarRegistroCoautoresArticulo(idArticulo, usuario.getIdusuario());
                articuloServicio.eliminarArticulo(idArticulo);
                mav.setViewName("redirect:/articulos/verArticulos");
                return mav;
            } catch (Exception e) {
                mav.setViewName("redirect:/articulos/verArticulos" );
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
                                        @RequestParam("idArticulo") Long idArticulo){
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (session.getAttribute("usuario") != null) {
            try {
                articuloServicio.agregarRegistroCoautorArticulo(idArticulo, usuario.getIdusuario(), idCoautor);
                mav.setViewName("redirect:/articulos/detalle/"+idArticulo);
                return mav;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(":: Error inesperado ::" + e.getMessage());
                mav.setViewName("redirect:/articulos/detalle/"+idArticulo);
                return mav;
            }
        } else {
            System.out.println(":: error de logueo ::");
            session.setAttribute("usuario", new Usuario());
            mav.addObject("usuario", session.getAttribute("usuario"));
            mav.setViewName("redirect:/");
            return mav;
        }
    }

    @GetMapping("/eliminarCoautor/{idArticulo}/{idCoautor}")
    public ModelAndView eliminarCoautor(HttpServletRequest request, @PathVariable("idCoautor") Long idCoautor,
                                        @PathVariable("idArticulo") Long idArticulo){
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (session.getAttribute("usuario") != null) {
            articuloServicio.eliminarRegistroCoautorArticulo(idArticulo, idCoautor);
            mav.setViewName("redirect:/articulos/detalle/"+idArticulo);
            return mav;
        } else {
            System.out.println("error de logueo");
            session.setAttribute("usuario", new Usuario());
            mav.addObject("usuario", session.getAttribute("usuario"));
            mav.setViewName("redirect:/");
            return mav;
        }
    }

    @PostMapping("/adjuntarArchivo")
    public ModelAndView subirArchivo(HttpServletRequest request,
                                     @RequestParam("documentoEvidencia") MultipartFile documentoEvidencia,
                                     @RequestParam("idArticulo") Long idArticulo) throws Exception {

        Articulo articulo = articuloServicio.buscarPorId(idArticulo);
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        mav.setViewName("redirect:/articulos/detalle/"+idArticulo);
        if (session.getAttribute("usuario") != null) {
            try {
                if (documentoEvidencia != null && !documentoEvidencia.isEmpty()) {
                    String nombreArchivoActualEvidencia = articulo.getDocumentoEvidencia();
                    if (nombreArchivoActualEvidencia != null && !nombreArchivoActualEvidencia.isEmpty()) {
                        eliminarArchivo(nombreArchivoActualEvidencia);
                    } else {
                        System.out.println("No hay archivos para eliminar");
                    }
                    articulo.setDocumentoEvidencia(archivosServicio.guardarSoloUno(documentoEvidencia));
                }
                articuloServicio.cargarDocumento(articulo);
                return mav;
            }catch (Exception e){
                e.printStackTrace();
                System.out.println(":: Error al subir el archivo :: " + e.getMessage());
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

    @GetMapping("/descargar/archivo/{nombreArchivo}")
    public ResponseEntity<Resource> descargarArchivo(@PathVariable String nombreArchivo) {
        try {
            // Construye la ruta completa al archivo utilizando el nombre almacenado en la base de datos
            Path archivoPath = Paths.get(Format.ARCHIVOS_CARGADOS_PATH).resolve(nombreArchivo).normalize();
            Resource resource = new UrlResource(archivoPath.toUri());

            if (resource.exists() && resource.isReadable()) {
                // Si el archivo existe y es legible, devuelve una respuesta con el archivo
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                // Si el archivo no existe o no es legible, devuelve una respuesta 404
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            // Maneja las excepciones
            return ResponseEntity.badRequest().build();
        }
    }

    private void eliminarArchivo(String nombreArchivo) {
        if (nombreArchivo != null && !nombreArchivo.isEmpty()) {
            try {
                Path archivoEliminar = Paths.get(Format.ARCHIVOS_CARGADOS_PATH, nombreArchivo);
                Files.deleteIfExists(archivoEliminar);
            } catch (IOException e) {
                System.out.println("Error al eliminar el archivo: " + e.getMessage());
            }
        }
    }

}
