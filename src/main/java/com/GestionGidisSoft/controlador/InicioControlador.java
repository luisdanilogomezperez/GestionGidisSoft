package com.GestionGidisSoft.controlador;

import com.GestionGidisSoft.entidades.PasswordResetToken;
import com.GestionGidisSoft.entidades.Usuario;
import com.GestionGidisSoft.servicios.PasswordResetService;
import com.GestionGidisSoft.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.UUID;

@Controller
public class InicioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private PasswordResetService resetService;

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

    @GetMapping("/recuperarContrasena")
    public ModelAndView mostrarFormularioSolicitarCambioContrasena () {
        ModelAndView mav = new ModelAndView();
        mav.addObject("usuario", new Usuario());
        mav.setViewName("recuperacion/solicitarCambioContrasena");
        return mav;
    }

    @PostMapping("/solitudCodigoVerificacion")
    public ModelAndView mostrarFormularioCambioContrasena (@ModelAttribute("usuario") Usuario usuario) {
        ModelAndView mav = new ModelAndView();
        Usuario usuarioAux = usuarioServicio.buscarUsuarioByEmail(usuario.getEmail());
        PasswordResetToken resetToken = new PasswordResetToken();
        System.out.println(usuarioAux.getEmail());
        if (usuarioAux != null) {
            String codigoVerificacion = generarCodigoVerificacion();
            LocalDateTime expirationTime = LocalDateTime.now().plus(5, ChronoUnit.MINUTES);
            resetToken.setEmail(usuarioAux.getEmail());
            resetToken.setFechaexpiracion(expirationTime);
            resetToken.setCodigoVerificacion(codigoVerificacion);
            resetToken.setIdUsuario(usuarioAux.getIdusuario());
            resetService.guardar(resetToken);
            // Envío del correo electrónico con el código de verificación
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(usuarioAux.getEmail());
            message.setFrom("noreply@gidissoft.com");
            message.setSubject("Código de verificación de recuperación de contraseña");
            message.setText("Utilice este código para recuperar su contraseña: " + codigoVerificacion);
            emailSender.send(message);

            mav.setViewName("redirect:/verificarCodigo?exito=true");
        } else {
            mav.setViewName("redirect:/recuperarContrasena?error=true");
        }
        return mav;
    }

    @GetMapping("/verificarCodigo")
    public ModelAndView mostrarFormularioVerificacionCodigo () {
        ModelAndView mav = new ModelAndView();
        mav.addObject("CODIGO_ENVIADO", "CODIGO_ENVIADO");
        mav.addObject("passwordResetToken", new PasswordResetToken());
        mav.setViewName("recuperacion/verificacionCodigo");
        return mav;
    }

    @PostMapping("/validarCodigo")
    public ModelAndView resetPasswordForm(@ModelAttribute("passwordResetToken") PasswordResetToken passwordReset) {
        ModelAndView mav = new ModelAndView();
        Object resultado = resetService.consultar(passwordReset.getCodigoVerificacion());
        if (resultado instanceof java.lang.String) {
            mav.setViewName("redirect:/verificarCodigo?error=true");
        } else {
            PasswordResetToken passwordResetToken = (PasswordResetToken) resultado;
            Usuario usuario = usuarioServicio.buscarUsuarioByEmail(passwordResetToken.getEmail());
            mav.addObject("usuario", usuario);
            mav.setViewName("recuperacion/recuperarContrasena");
        }
        return mav;
    }

    private String generarCodigoVerificacion() {
        Random random = new Random();
        int codigo = 100000 + random.nextInt(900000); // Genera un número aleatorio de 6 dígitos
        return String.valueOf(codigo);
    }

    @PostMapping("/cambiarContrasena")
    public ModelAndView passwordResetProcess(@ModelAttribute Usuario usuario,
                         @RequestParam(value = "clave") String clave) {
        ModelAndView mav = new ModelAndView();
        Usuario user = usuarioServicio.buscarUsuarioByEmail(usuario.getEmail());
        user.setClave(clave);
        String mensaje = "";
        if(user != null) {
            mensaje = usuarioServicio.recuperarContrasena(user);
            System.out.println(mensaje);
            mav.setViewName("redirect:/claveActualizada?exito=true");
        } else {
            mav.setViewName("redirect:/claveActualizada?error=true");
        }
        return mav;
    }

    @GetMapping("/claveActualizada")
    public ModelAndView login(@ModelAttribute Usuario usuario) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("usuario", new Usuario());
        mav.setViewName("recuperacion/recuperarContrasena");
        return mav;
    }
}
