package com.GestionGidisSoft.controlador;

import com.GestionGidisSoft.Constantes.Format;
import com.GestionGidisSoft.entidades.CapituloLibro;
import com.GestionGidisSoft.entidades.Libro;
import com.GestionGidisSoft.entidades.Usuario;
import com.GestionGidisSoft.servicios.ArchivosServicio;
import com.GestionGidisSoft.servicios.CapituloLibroServicio;
import com.GestionGidisSoft.servicios.LibroServico;
import com.GestionGidisSoft.servicios.UsuarioServicio;
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
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/capitulosLibro")
public class CapituloLibroControlador {


    @Autowired
    ArchivosServicio archivosServicio;
    @Autowired
    LibroServico libroServico;

    @Autowired
    CapituloLibroServicio capituloLibroServicio;

    @Autowired
    UsuarioServicio usuarioServicio;

    @GetMapping("/verCapitulosLibros")
    public ModelAndView verCapitulosLibros(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();

        if (session.getAttribute("usuario") != null) {
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            List<CapituloLibro> listacapituloLibros = capituloLibroServicio.findByUsuarioId(usuario.getIdusuario());
            List<CapituloLibro> listacapituloLibrosVista = new ArrayList<>();

            if (!listacapituloLibros.isEmpty()){
                for (CapituloLibro capituloLibro : listacapituloLibros) {
                    System.out.println("capitulo :::: " + capituloLibro.getTitulo());
                    Libro libro = libroServico.buscarPorId(capituloLibro.getIdLibro());
                    capituloLibro.setTituloLibro(libro.getTitulo());
                    listacapituloLibrosVista.add(capituloLibro);
                }
            }
            if (listacapituloLibros.isEmpty()){
                mav.addObject("mensaje", "Aún no hay registros");
            }
            mav.addObject("listaCapitulosLibros", listacapituloLibrosVista);
            mav.addObject("usuario", usuario);
            mav.setViewName("listarCapitulosLibros");
            return mav;
        } else {
            System.out.println("error de logueo");
            session.setAttribute("usuario", new Usuario());
            mav.addObject("usuario", session.getAttribute("usuario"));
            mav.setViewName("redirect:/");
            return mav;
        }
    }

    @GetMapping("/nuevoCapituloLibro")
    public ModelAndView nuevoCapituloLibro(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        if (session.getAttribute("usuario") != null) {
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            List<Libro> libros = libroServico.findByUsuarioId(usuario.getIdusuario());
            mav.addObject("usuarioId", usuario.getIdusuario());
            mav.addObject("listaLibros", libros);
            mav.addObject("capituloLibro", new CapituloLibro());
            mav.setViewName("registrarCapituloLibro");
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
    public ModelAndView guardarCapituloLibro(HttpServletRequest request, @ModelAttribute("capituloLibro") CapituloLibro capituloLibro) throws Exception {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        if (session.getAttribute("usuario") != null) {
            //verifica que no haya un libro con el mismo isbn
            capituloLibro.setDocumentoEvidencia(null);
            capituloLibro.setCertificadoCreditos(null);
            capituloLibro.setCertificadoInstitucionAvala(null);
            Libro librox = libroServico.buscarPorId(capituloLibro.getIdLibro());
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            capituloLibroServicio.guardarCapituloLibro(capituloLibro);
            capituloLibroServicio.agregarRegistroAutorCapitulo(capituloLibro.getIdCapitulo(), usuario.getIdusuario());
            List<CapituloLibro> listacapituloLibros = capituloLibroServicio.findByUsuarioId(usuario.getIdusuario());
            List<CapituloLibro> listacapituloLibrosVista = new ArrayList<>();
            Libro libro = null;
            if (!listacapituloLibros.isEmpty()){
                for (CapituloLibro capLibro : listacapituloLibros) {
                    libro = libroServico.buscarPorId(capLibro.getIdLibro());
                    capituloLibro.setTituloLibro(libro.getTitulo());
                    listacapituloLibrosVista.add(capituloLibro);
                }
            }
            mav.addObject("listaCapitulosLibros", listacapituloLibrosVista);
            mav.addObject("usuario", usuario);
            mav.setViewName("listarCapitulosLibros");
            return mav;

        } else {
            System.out.println("error de logueo");
            session.setAttribute("usuario", new Usuario());
            mav.addObject("usuario", session.getAttribute("usuario"));
            mav.setViewName("redirect:/");
            return mav;
        }
    }

    @RequestMapping("/editar/{idCapitiloLibro}")
    public ModelAndView goUpdate(HttpServletRequest request, @PathVariable(value = "idCapitiloLibro") long idCapitiloLibro) {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();

        if (session.getAttribute("usuario") != null) {
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            CapituloLibro capituloLibro = capituloLibroServicio.buscarPorId(idCapitiloLibro);
            List<Libro> libros = libroServico.findByUsuarioId(usuario.getIdusuario());
            mav.addObject("listaLibros", libros);
            mav.addObject("capituloLibro", capituloLibro);
            mav.setViewName("editarCapituloLibro");
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
    public ModelAndView editarCapituloLibro(HttpServletRequest request, @ModelAttribute("capituloLibro") CapituloLibro capituloLibro) throws Exception {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        if (session.getAttribute("usuario") != null) {
                Usuario usuario = (Usuario) session.getAttribute("usuario");
                capituloLibroServicio.actualizarCapituloLibro(capituloLibro);
                List<CapituloLibro> listaCapituloLibros = capituloLibroServicio.findByUsuarioId(usuario.getIdusuario());
                mav.addObject("listaCapitulosLibros", listaCapituloLibros);
                mav.addObject("usuario", usuario);
                mav.setViewName("listarCapitulosLibros");
                return mav;
        } else {
            System.out.println("error de logueo");
            session.setAttribute("usuario", new Usuario());
            mav.addObject("usuario", session.getAttribute("usuario"));
            mav.setViewName("redirect:/");
            return mav;
        }
    }
    @RequestMapping("/detalle/{idCapituloLibro}")
    public ModelAndView goDetail(HttpServletRequest request, @PathVariable(value = "idCapituloLibro") long idCapituloLibro) {
        List<Usuario> listaAutores = new ArrayList();
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        if (session.getAttribute("usuario") != null) {
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            List<Usuario> coautores = usuarioServicio.listarCoautoresCapitulosLibros(idCapituloLibro, usuario.getIdusuario());
            List<Usuario> autores = usuarioServicio.listarAutoresCapitulosLibros(idCapituloLibro, usuario.getIdusuario());
            CapituloLibro capituloLibro = capituloLibroServicio.buscarPorId(idCapituloLibro);
            Libro libro = libroServico.buscarPorId(capituloLibro.getIdLibro());
            capituloLibro.setTituloLibro(libro.getTitulo());
            if (autores != null) {
                for (Usuario usuario1: autores) {
                    if (usuario1.getIdusuario() != usuario.getIdusuario()) {
                        listaAutores.add(usuario1);
                        System.out.println();
                    }
                }
            }
            mav.addObject("autoresList", listaAutores);
            mav.addObject("listaCoautores", coautores);
            mav.addObject("capituloLibro", capituloLibro);
            mav.addObject("usuario", usuario);
            mav.addObject("usuarioCoautor", new Usuario());
            mav.setViewName("verDetallesCapituloLibro");
            return mav;
        } else {
            System.out.println("error de logueo");
            session.setAttribute("usuario", new Usuario());
            mav.addObject("usuario", session.getAttribute("usuario"));
            mav.setViewName("redirect:/");
            return mav;
        }
    }

    @RequestMapping("/eliminar/{idCapituloLibro}")
    public ModelAndView eliminar(HttpServletRequest request, @PathVariable(value = "idCapitulo") Long idCapitulo) {
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (session.getAttribute("usuario") != null) {
            try {
                capituloLibroServicio.eliminarRegistrosAutorCapitulo(idCapitulo, usuario.getIdusuario());
                capituloLibroServicio.eliminarRegistrosCoautoresCapituloLibro(idCapitulo, usuario.getIdusuario());
                capituloLibroServicio.eliminar(idCapitulo);
                mav.setViewName("redirect:/capitulosLibro/verLibros");
                return mav;
            } catch (Exception e) {
                mav.setViewName("redirect:/capitulosLibro/verLibros" );
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
    public ModelAndView insertarCoautor(HttpServletRequest request, @RequestParam("idCoautor") Long coautorId,
                                        @RequestParam("idCapituloLibro") Long idCapituloLibro){
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (session.getAttribute("usuario") != null) {
            try {
                capituloLibroServicio.insertarCoautor(usuario.getIdusuario(), coautorId, idCapituloLibro);
                mav.setViewName("redirect:/capitulosLibro/detalle/"+idCapituloLibro);
                return mav;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("" + e.getMessage());
                mav.setViewName("redirect:/capitulosLibro/detalle/"+idCapituloLibro);
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

    @GetMapping("/eliminarCoautor/{idCapituloLibro}/{idCoautor}")
    public ModelAndView eliminarCoautor(HttpServletRequest request, @PathVariable("idCoautor") Long idCoautor,
                                        @PathVariable("idCapituloLibro") Long idCapituloLibro){
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (session.getAttribute("usuario") != null) {
            capituloLibroServicio.eliminarRelacionCoautores(idCapituloLibro, idCoautor);
            mav.setViewName("redirect:/capitulosLibro/detalle/"+idCapituloLibro);
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
                               @RequestParam("idLibro") String idCapituloLibro){

    CapituloLibro capituloLibro = capituloLibroServicio.buscarPorId(Long.parseLong(idCapituloLibro));
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        mav.setViewName("redirect:/libros/detalle/"+idCapituloLibro);
        if (session.getAttribute("usuario") != null) {

            try {
                if (documentoEvidencia != null && !documentoEvidencia.isEmpty()) {
                    String nombreArchivoActualEvidencia = capituloLibro.getDocumentoEvidencia();
                    if (nombreArchivoActualEvidencia != null && !nombreArchivoActualEvidencia.isEmpty()) {
                        eliminarArchivo(nombreArchivoActualEvidencia);
                    } else {
                        System.out.println("No hay archivos para eliminar");
                    }
                    capituloLibro.setDocumentoEvidencia(archivosServicio.guardarSoloUno(documentoEvidencia));
                }
                if (certificadoCreditos != null && !certificadoCreditos.isEmpty()) {
                    String nombreArchivoActualCreditos = capituloLibro.getCertificadoCreditos();
                    if (nombreArchivoActualCreditos != null && !nombreArchivoActualCreditos.isEmpty()) {
                        eliminarArchivo(nombreArchivoActualCreditos);
                    } else {
                        System.out.println("No hay archivos para eliminar");
                    }
                    capituloLibro.setCertificadoCreditos(archivosServicio.guardarSoloUno(certificadoCreditos));
                }
                if (certificadoInstitucionAvala != null && !certificadoInstitucionAvala.isEmpty()) {
                    String nombreArchivoActualAvala = capituloLibro.getCertificadoInstitucionAvala();
                    if (nombreArchivoActualAvala != null && !nombreArchivoActualAvala.isEmpty()) {
                        eliminarArchivo(nombreArchivoActualAvala);
                    } else {
                        System.out.println("No hay archivos para eliminar");
                    }
                    capituloLibro.setCertificadoInstitucionAvala(archivosServicio.guardarSoloUno(certificadoInstitucionAvala));
                }
                capituloLibroServicio.actualizarCapituloLibro(capituloLibro);

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
