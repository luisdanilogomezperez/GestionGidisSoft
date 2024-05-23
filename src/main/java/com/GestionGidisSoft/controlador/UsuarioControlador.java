package com.GestionGidisSoft.controlador;

import com.GestionGidisSoft.DTO.LoginRequestDto;
import com.GestionGidisSoft.entidades.Rol;
import com.GestionGidisSoft.entidades.Usuario;
import com.GestionGidisSoft.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/usuarios")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @PostMapping("/guardar")
    public ModelAndView guardarUsuario(@ModelAttribute("usuario") Usuario usuario){
        ModelAndView model = new ModelAndView();
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
       //     return new ResponseEntity(new RegistroResponseDto(true),HttpStatus.CREATED);
        }catch (Exception e){
            model.setViewName("redirect:/registrarse?error=true");
           // return new ResponseEntity(new RegistroResponseDto(false),HttpStatus.BAD_REQUEST);
        }
            return model;
    }

    @GetMapping("/buscar/{username}")
    public Usuario buscarUsuario(@PathVariable("username") String username){
        return  usuarioServicio.buscarUsuario(username);
    }

    @DeleteMapping("/eliminar/{usuarioId}")
    public void eliminarUsuario(@PathVariable("usuarioId")  Long usuarioId){
        usuarioServicio.eliminar(usuarioId);
    }

    @PostMapping("/login")
    public ModelAndView loginUsuario(HttpServletRequest request,
                                     @ModelAttribute("usuario") LoginRequestDto loginRequestDto) {
        HttpSession session = request.getSession();
        ModelAndView model = new ModelAndView();
        if (usuarioServicio.loginUsuario(loginRequestDto)) {
            Usuario usuario = usuarioServicio.buscarUsuarioByEmail(loginRequestDto.getEmail());
            Rol usuarioRol = usuarioServicio.consultarRolUsuario(usuario.getIdusuario());
            usuario.setUsuarioRol(usuarioRol.getNombre());
            if (usuario.isEnable()) {
                session.setAttribute("usuario", usuario);
                model.setViewName("redirect:/usuarios/home");
            } else {
                session.setAttribute("usuario", null);
                model.addObject("usuario", new Usuario());
                model.addObject("estadoUsuario", "DESHABILITADO");
                model.setViewName("index");
            }
            return model;
        } else {
            System.out.println("error de logueo");
            model.setViewName("redirect:/?error=true");
            return model;
        }
    }

    @GetMapping("/home")
    public ModelAndView goHomeGidis(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        session.removeAttribute("listaProduccionesAsociados");
        session.removeAttribute("listaLibros");
        session.removeAttribute("listaArticulos");
        session.removeAttribute("listaCapitulos");
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

}
