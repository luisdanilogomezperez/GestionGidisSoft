package com.GestionGidisSoft.controlador;

import com.GestionGidisSoft.DTO.LoginResponseDto;
import com.GestionGidisSoft.entidades.Libro;
import com.GestionGidisSoft.entidades.Usuario;
import com.GestionGidisSoft.servicios.ArchivosServicio;
import com.GestionGidisSoft.servicios.LibroServico;
import com.GestionGidisSoft.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            System.out.println("usuario en sesión: " + usuario.getPrimerNombre());
            if (libros.isEmpty()){
                mav.addObject("mensaje", "Aun no hay registros");
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
                libroServico.actualizarTablaIntermedia( libro.getIdlibro(), usuario.getIdusuario());
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
            System.out.println("año del libro ::::::::: " + libro.getIdlibro());

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

            System.out.println("Titulo modificado: " + libro.getTitulo() + " / id ::: " + libro.getIdlibro());

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
            List<Usuario> coautores = usuarioServicio.listarCoautores(libroId, usuario.getIdusuario());
            List<Usuario> autores = usuarioServicio.listarAutores(libroId, usuario.getIdusuario());
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
        System.out.println(":: Eliminando libro de :: " + libroId + " - " + usuario.getIdusuario());
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
    }

    @GetMapping("/verCapitulosLibros")
    public ModelAndView goCapitulosLibros(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();

        if (session.getAttribute("usuario") != null) {
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            List<Libro> libros = libroServico.findByUsuarioId(usuario.getIdusuario());
            System.out.println("usuario en sesión: " + usuario.getPrimerNombre());
            if (libros.isEmpty()){
                mav.addObject("mensaje", "Aun no hay registros");
            }
            mav.addObject("listaLibros", libros);
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
    @PostMapping("/agregarCoautor")
    public ModelAndView insertarCoautor(HttpServletRequest request, @RequestParam("coautorId") Long coautorId,
                                        @RequestParam("idLibro") Long idLibro){
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        libroServico.insertarCoautor(idLibro, usuario.getIdusuario(), coautorId);
        mav.setViewName("redirect:/libros/detalle/"+idLibro);
        return mav;
    }

    @GetMapping("/eliminarCoautor/{idlibro}/{idCoautor}")
    public ModelAndView eliminarCoautor(HttpServletRequest request, @PathVariable("idCoautor") Long idCoautor,
                                        @PathVariable("idlibro") Long idLibro){
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        libroServico.eliminarRelacionCoautores(idLibro, idCoautor);
        mav.setViewName("redirect:/libros/detalle/"+idLibro);
        return mav;
    }

    @PostMapping("/adjuntarArchivo")
    public String subirArchivo(@RequestParam("file") MultipartFile file, @RequestParam("nombreCampo") String nombre){
        String error = "";
        String nombreArchivo = "";
        try {
            nombreArchivo = archivosServicio.guardarSoloUno(file);
            return nombreArchivo;
        }catch (Exception e){

            return ":: Error al subir el archivo :: " + e.getCause().getMessage();
        }
    }
}
