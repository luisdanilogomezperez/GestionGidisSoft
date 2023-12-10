package com.GestionGidisSoft.controlador;

import com.GestionGidisSoft.DTO.LoginResponseDto;
import com.GestionGidisSoft.entidades.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class InicioControlador {
    @GetMapping("/")
    public ModelAndView goHome(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        session.setAttribute("usuario", null);
        mav.addObject("usuario", new Usuario());
        mav.setViewName("index");
        return mav;
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        request.removeAttribute("usuario");
        session.setAttribute("usuario", null);
        ModelAndView model = new ModelAndView();
        model.setViewName("redirect:/?logout=true");
        return model;
    }

    @GetMapping("/registrarse")
    public ModelAndView mostrarFormularioRegistroUsuario() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("usuario", new Usuario());
        mav.setViewName("registro");
        return mav;
    }
}
