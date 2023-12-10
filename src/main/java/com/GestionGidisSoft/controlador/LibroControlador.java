package com.GestionGidisSoft.controlador;

import com.GestionGidisSoft.DTO.LoginResponseDto;
import com.GestionGidisSoft.entidades.Libro;
import com.GestionGidisSoft.entidades.Usuario;
import com.GestionGidisSoft.servicios.ArchivosServicio;
import com.GestionGidisSoft.servicios.LibroServico;
import com.GestionGidisSoft.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
            List<Libro> libros = libroServico.findByUsuarioId(usuario.getUsuarioId());
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
            mav.addObject("usuarioId", usuario.getUsuarioId());
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
    @PostMapping("/save")
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
                libroServico.actualizarTablaIntermedia( libro.getLibroId(), usuario.getUsuarioId());
                List<Libro> libros = libroServico.findByUsuarioId(usuario.getUsuarioId());
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
            System.out.println("año del libro ::::::::: " + libro.getAnio());
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

    @RequestMapping("/detalle/{libroId}")
    public ModelAndView goDetail(HttpServletRequest request, @PathVariable(value = "libroId") long libroId) {

        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        if (session.getAttribute("usuario") != null) {
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            Libro libro = libroServico.buscarPorId(libroId);
            mav.addObject("libro", libro);
            mav.addObject("usuario", usuario);
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

    @RequestMapping("/eliminar/{usuarioId}/{libroId}")
    public ModelAndView eliminar(@PathVariable(value = "libroId") Long libroId, @PathVariable(value = "usuarioId") Long usuarioId) {
        ModelAndView mav = new ModelAndView();
        System.out.println(":: Eliminando libro de :: " + libroId + " - " + usuarioId);
        try {
            libroServico.eliminarRelacionLibroUsuario(libroId, usuarioId);
            mav.setViewName("redirect:/libros/verLibros/" + usuarioId);
            libroServico.eliminar(libroId);
            return mav;

        } catch (Exception e) {
            e.printStackTrace();
            mav.setViewName("redirect:/libros/verLibros/" + usuarioId);
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
            List<Libro> libros = libroServico.findByUsuarioId(usuario.getUsuarioId());
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
    @PostMapping("/subirArchivo")
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
