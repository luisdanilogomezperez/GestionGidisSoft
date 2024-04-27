package com.GestionGidisSoft.controlador;

import com.GestionGidisSoft.Constantes.Format;
import com.GestionGidisSoft.entidades.Libro;
import com.GestionGidisSoft.entidades.ProyectoInvestigacion;
import com.GestionGidisSoft.entidades.Usuario;
import com.GestionGidisSoft.servicios.ProyectoInvestigacionServicio;
import com.GestionGidisSoft.servicios.UsuarioServicio;
import com.GestionGidisSoft.servicios.Utils;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/proyectosInvestigacion")
public class ProyectoInvestControlador {

    @Autowired
    ProyectoInvestigacionServicio proyectoServicio;
    @Autowired
    UsuarioServicio usuarioServicio;

    @GetMapping("/verProyectosInvestigacion")
    public ModelAndView goProyectosInvestigacion(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
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
            ProyectoInvestigacion proyectoInvestigacion = proyectoServicio.buscarPorId(idProyectoInvestigacion);
            for (Usuario usuario1: autores) {
                if (usuario1.getIdusuario() != usuario.getIdusuario()) {
                    listaAutores.add(usuario1);
                    System.out.println();
                }
            }
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

//    @PostMapping("/adjuntarArchivo")
//    public ModelAndView subirArchivo(HttpServletRequest request,
//                                     @RequestParam("documentoEvidencia") MultipartFile documentoEvidencia,
//                                     @RequestParam("certificadoCreditos") MultipartFile certificadoCreditos,
//                                     @RequestParam("certificadoInstitucionAvala") MultipartFile certificadoInstitucionAvala,
//                                     @RequestParam("idLibro") String idLibro){
//        Libro libro = libroServico.buscarPorId(Integer.parseInt(idLibro));
//
//        ModelAndView mav = new ModelAndView();
//        HttpSession session = request.getSession();
//        mav.setViewName("redirect:/libros/detalle/"+idLibro);
//        if (session.getAttribute("usuario") != null) {
//            try {
//                if (documentoEvidencia != null && !documentoEvidencia.isEmpty()) {
//                    String nombreArchivoActualEvidencia = libro.getDocumentoEvidencia();
//                    if (nombreArchivoActualEvidencia != null && !nombreArchivoActualEvidencia.isEmpty()) {
//                        eliminarArchivo(nombreArchivoActualEvidencia);
//                    } else {
//                        System.out.println("No hay archivos para eliminar");
//                    }
//                    libro.setDocumentoEvidencia(archivosServicio.guardarSoloUno(documentoEvidencia));
//                }
//                if (certificadoCreditos != null && !certificadoCreditos.isEmpty()) {
//                    String nombreArchivoActualCreditos = libro.getCertificadoCreditos();
//                    if (nombreArchivoActualCreditos != null && !nombreArchivoActualCreditos.isEmpty()) {
//                        eliminarArchivo(nombreArchivoActualCreditos);
//                    } else {
//                        System.out.println("No hay archivos para eliminar");
//                    }
//                    libro.setCertificadoCreditos(archivosServicio.guardarSoloUno(certificadoCreditos));
//                }
//                if (certificadoInstitucionAvala != null && !certificadoInstitucionAvala.isEmpty()) {
//                    String nombreArchivoActualAvala = libro.getCertificadoInstitucionAvala();
//                    if (nombreArchivoActualAvala != null && !nombreArchivoActualAvala.isEmpty()) {
//                        eliminarArchivo(nombreArchivoActualAvala);
//                    } else {
//                        System.out.println("No hay archivos para eliminar");
//                    }
//                    libro.setCertificadoInstitucionAvala(archivosServicio.guardarSoloUno(certificadoInstitucionAvala));
//                }
//                libroServico.cargarDocumentos(libro);
//
//                return mav;
//            }catch (Exception e){
//                e.printStackTrace();
//                System.out.println(":: Error al subir el archivo :: ");
//                return mav;
//            }
//        } else {
//            System.out.println("error de logueo");
//            session.setAttribute("usuario", new Usuario());
//            mav.addObject("usuario", session.getAttribute("usuario"));
//            mav.setViewName("redirect:/");
//            return mav;
//        }
//    }

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
