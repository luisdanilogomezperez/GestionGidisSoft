package com.GestionGidisSoft.controlador;

import com.GestionGidisSoft.Constantes.Format;
import com.GestionGidisSoft.DTO.LoginResponseDto;
import com.GestionGidisSoft.entidades.Libro;
import com.GestionGidisSoft.entidades.Usuario;
import com.GestionGidisSoft.servicios.ArchivosServicio;
import com.GestionGidisSoft.servicios.LibroServico;
import com.GestionGidisSoft.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/libros")
public class LibroControlador {


    @Autowired
    ArchivosServicio archivosServicio;
    @Autowired
    LibroServico libroServico;

    @Autowired
    UsuarioServicio usuarioServicio;

    @GetMapping("/verLibros")
    public ModelAndView goLibros(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        if (session.getAttribute("usuario") != null) {
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            List<Libro> libros = libroServico.findByUsuarioId(usuario.getIdusuario());
            if (libros.isEmpty()){
                mav.addObject("mensaje", "Aún no hay registros");
            }
            mav.addObject("listaLibros", libros);
            mav.addObject("usuario", usuario);
            mav.setViewName("listarLibros");
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
            mav.addObject("usuarioId", usuario.getIdusuario());
            mav.addObject("libro", new Libro());
            mav.setViewName("registrarLibro");
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
    public ModelAndView guardarLibro(HttpServletRequest request, @ModelAttribute("libro") Libro libro) throws Exception {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        if (session.getAttribute("usuario") != null) {
            //verifica que no haya un libro con el mismo isbn
            Optional<Libro> libroExistente = libroServico.findByIsbn(libro.getIsbn());
            if (libroExistente.isPresent()) {
                mav.addObject("libro", libro);
                mav.addObject("mensaje", "Ya existe un libro con este ISBN.");
                mav.setViewName("registrarLibro"); // Nombre de la vista del formulario
                return mav;
            } else {
                Usuario usuario = (Usuario) session.getAttribute("usuario");
                libroServico.guardarLibro(libro);
                libroServico.actualizarTablaIntermedia( libro.getIdLibro(), usuario.getIdusuario());
                List<Libro> libros = libroServico.findByUsuarioId(usuario.getIdusuario());
                mav.addObject("listaLibros", libros);
                mav.addObject("usuario", usuario);
                mav.setViewName("listarLibros");
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

    @RequestMapping("/editar/{libroId}")
    public ModelAndView goUpdate(HttpServletRequest request, @PathVariable(value = "libroId") int libroId) {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();

        if (session.getAttribute("usuario") != null) {
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            Libro libro = libroServico.buscarPorId(libroId);
            System.out.println("año del libro ::::::::: " + libro.getIdLibro());

            mav.addObject("libro", libro);
            mav.setViewName("editarLibro");
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
    public ModelAndView editarLibro(HttpServletRequest request, @ModelAttribute("libro") Libro libro) throws Exception {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        if (session.getAttribute("usuario") != null) {
                Usuario usuario = (Usuario) session.getAttribute("usuario");
                libro.setDocumentoEvidencia(null);
                libro.setCertificadoCreditos(null);
                libro.setCertificadoInstitucionAvala(null);
                libroServico.actualizarLibro(libro);

                List<Libro> libros = libroServico.findByUsuarioId(usuario.getIdusuario());
                mav.addObject("listaLibros", libros);
                mav.addObject("usuario", usuario);
                mav.setViewName("listarLibros");
                return mav;
        } else {
            System.out.println("error de logueo");
            session.setAttribute("usuario", new Usuario());
            mav.addObject("usuario", session.getAttribute("usuario"));
            mav.setViewName("redirect:/");
            return mav;
        }
    }
    @RequestMapping("/detalle/{libroId}")
    public ModelAndView goDetail(HttpServletRequest request, @PathVariable(value = "libroId") long libroId) {
        List<Usuario> listaAutores = new ArrayList();
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        if (session.getAttribute("usuario") != null) {
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            List<Usuario> coautores = usuarioServicio.listarCoautoresLibros(libroId, usuario.getIdusuario());
            List<Usuario> autores = usuarioServicio.listarAutoresLibros(libroId, usuario.getIdusuario());
            Libro libro = libroServico.buscarPorId(libroId);
            for (Usuario usuario1: autores) {
                if (usuario1.getIdusuario() != usuario.getIdusuario()) {
                    listaAutores.add(usuario1);
                    System.out.println();
                }
            }
            mav.addObject("autoresList", listaAutores);
            mav.addObject("listaCoautores", coautores);
            mav.addObject("libro", libro);
            mav.addObject("usuario", usuario);
            mav.addObject("usuarioCoautor", new Usuario());
            mav.setViewName("verDetallesLibro");
            return mav;
        } else {
            System.out.println("error de logueo");
            session.setAttribute("usuario", new Usuario());
            mav.addObject("usuario", session.getAttribute("usuario"));
            mav.setViewName("redirect:/");
            return mav;
        }
    }

    @RequestMapping("/eliminar/{libroId}")
    public ModelAndView eliminar(HttpServletRequest request, @PathVariable(value = "libroId") Long libroId) {
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (session.getAttribute("usuario") != null) {
            try {
                libroServico.eliminarRegistrosAutorLibro(libroId, usuario.getIdusuario());
                libroServico.eliminarRelacionLibroUsuario(libroId, usuario.getIdusuario());
                libroServico.eliminar(libroId);
                mav.setViewName("redirect:/libros/verLibros");
                return mav;

            } catch (Exception e) {
                e.printStackTrace();
                mav.setViewName("redirect:/libros/verLibros" );
                //return "redirect:/libros/inicio";
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
                                        @RequestParam("idLibro") Long idLibro){
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (session.getAttribute("usuario") != null) {
            libroServico.insertarCoautor(idLibro, usuario.getIdusuario(), idCoautor);
            mav.setViewName("redirect:/libros/detalle/"+idLibro);
            return mav;
        } else {
            System.out.println("error de logueo");
            session.setAttribute("usuario", new Usuario());
            mav.addObject("usuario", session.getAttribute("usuario"));
            mav.setViewName("redirect:/");
            return mav;
        }
    }

    @GetMapping("/eliminarCoautor/{idLibro}/{idCoautor}")
    public ModelAndView eliminarCoautor(HttpServletRequest request, @PathVariable("idCoautor") Long idCoautor,
                                        @PathVariable("idLibro") Long idLibro){
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        if (session.getAttribute("usuario") != null) {
            libroServico.eliminarRelacionCoautores(idLibro, idCoautor);
            mav.setViewName("redirect:/libros/detalle/"+idLibro);
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
                               @RequestParam("certificadoCreditos") MultipartFile certificadoCreditos,
                               @RequestParam("certificadoInstitucionAvala") MultipartFile certificadoInstitucionAvala,
                               @RequestParam("idLibro") String idLibro){
        Libro libro = libroServico.buscarPorId(Integer.parseInt(idLibro));

        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        mav.setViewName("redirect:/libros/detalle/"+idLibro);
        if (session.getAttribute("usuario") != null) {
            try {
                if (documentoEvidencia != null && !documentoEvidencia.isEmpty()) {
                    String nombreArchivoActualEvidencia = libro.getDocumentoEvidencia();
                    if (nombreArchivoActualEvidencia != null && !nombreArchivoActualEvidencia.isEmpty()) {
                        eliminarArchivo(nombreArchivoActualEvidencia);
                    } else {
                        System.out.println("No hay archivos para eliminar");
                    }
                    libro.setDocumentoEvidencia(archivosServicio.guardarSoloUno(documentoEvidencia));
                }
                if (certificadoCreditos != null && !certificadoCreditos.isEmpty()) {
                    String nombreArchivoActualCreditos = libro.getCertificadoCreditos();
                    if (nombreArchivoActualCreditos != null && !nombreArchivoActualCreditos.isEmpty()) {
                        eliminarArchivo(nombreArchivoActualCreditos);
                    } else {
                        System.out.println("No hay archivos para eliminar");
                    }
                    libro.setCertificadoCreditos(archivosServicio.guardarSoloUno(certificadoCreditos));
                }
                if (certificadoInstitucionAvala != null && !certificadoInstitucionAvala.isEmpty()) {
                    String nombreArchivoActualAvala = libro.getCertificadoInstitucionAvala();
                    if (nombreArchivoActualAvala != null && !nombreArchivoActualAvala.isEmpty()) {
                        eliminarArchivo(nombreArchivoActualAvala);
                    } else {
                        System.out.println("No hay archivos para eliminar");
                    }
                    libro.setCertificadoInstitucionAvala(archivosServicio.guardarSoloUno(certificadoInstitucionAvala));
                }
                libroServico.actualizarLibro(libro);

                return mav;
            }catch (Exception e){
                e.printStackTrace();
                System.out.println(":: Error al subir el archivo :: ");
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
