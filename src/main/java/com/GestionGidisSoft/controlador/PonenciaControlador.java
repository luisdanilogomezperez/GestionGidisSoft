package com.GestionGidisSoft.controlador;


import com.GestionGidisSoft.entidades.Ponencia;
import com.GestionGidisSoft.entidades.Usuario;
import com.GestionGidisSoft.servicios.impl.PonenciaServicioImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/ponencia")
@RequiredArgsConstructor
public class PonenciaControlador {

    @Autowired
    PonenciaServicioImpl ponenciaServicio;


    @GetMapping("/verPonencias")
    public ModelAndView goPonencias(HttpServletRequest request) throws Exception{
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        try {
            if (session.getAttribute("usuario") != null) {
                Usuario usuario = (Usuario) session.getAttribute("usuario");
                List<Ponencia> ponencias = ponenciaServicio.findByUsuarioId(usuario.getIdusuario());
                if (ponencias == null){
                    mav.addObject("mensaje", "Aún no hay registros");
                }
                mav.addObject("listaPonencias", ponencias);
                mav.addObject("usuario", usuario);
                mav.setViewName("listarPonencias");
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

    @GetMapping("/nuevo")
    public ModelAndView nuevoPonencia(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        if (session.getAttribute("usuario") != null) {
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            mav.addObject("usuario", usuario);
            mav.addObject("ponencia", new Ponencia());
            mav.setViewName("registrarPonencia");
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
    public ModelAndView guardarPonencia(HttpServletRequest request, @ModelAttribute("ponencia") Ponencia ponencia) throws Exception {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        if (session.getAttribute("usuario") != null) {

            Usuario usuario = (Usuario) session.getAttribute("usuario");
            /*Ponencia ponencia = Ponencia.builder()
                    .nombrePonencia(ponenciaDTO.getNombrePonencia())
                    .fechaInicio(LocalDate.parse(ponenciaDTO.getFechaInicio()))
                    .fechaFin(LocalDate.parse(ponenciaDTO.getFechaFin()))
                    .lugar(ponenciaDTO.getLugar())
                    .participacion(ponenciaDTO.getParticipacion())
                    .descripcion(ponenciaDTO.getDescripcion())
                    .institucion(ponenciaDTO.getInstitucion())
                    .build();*/
            ponencia.setIdAutor(usuario.getIdusuario());
            String nombreCompleto = usuario.getPrimerNombre() + " " + usuario.getSegundoNombre() + " " +
            usuario.getPrimerApellido() + " " + usuario.getSegundoApellido();
            ponencia.setAutor(nombreCompleto);
            ponenciaServicio.guardarPonencia(ponencia);
            ponenciaServicio.actualizarTablaIntermedia( ponencia.getIdPonencia(), usuario.getIdusuario());
            mav.setViewName("redirect:/ponencia/verPonencias");
            return mav;
        } else {
            System.out.println("error de logueo");
            session.setAttribute("usuario", new Usuario());
            mav.addObject("usuario", session.getAttribute("usuario"));
            mav.setViewName("redirect:/");
            return mav;
        }
    }

    @GetMapping("/verPonencia/{id}")
    public ResponseEntity<Ponencia> buscarPonenciaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ponenciaServicio.buscarPonenciaPorId(id));
    }

    @GetMapping("/detalle/{id}")
    public ModelAndView goDetail(HttpServletRequest request, @PathVariable(value = "id") long id) {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        if (session.getAttribute("usuario") != null) {
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            Ponencia ponencia = ponenciaServicio.buscarPonenciaPorId(id);

            mav.addObject("ponencia", ponencia);
            mav.addObject("usuario", usuario);
            mav.setViewName("verDetallesPonencia");
            return mav;
        } else {
            System.out.println("error de logueo");
            session.setAttribute("usuario", new Usuario());
            mav.addObject("usuario", session.getAttribute("usuario"));
            mav.setViewName("redirect:/");
            return mav;
        }
    }

    @PostMapping("/crear")
    public ResponseEntity<Void> crearPonencia(@RequestBody Ponencia ponencia) {
        Ponencia ponenciaAGuardar = ponencia;
        ponenciaServicio.crearPonencia(ponenciaAGuardar);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/editarPonencia/{id}")
    public ResponseEntity<Void> editarPonencia(@PathVariable Long id, @RequestBody Ponencia ponencia) {
        ponenciaServicio.editarPonencia(id, ponencia);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/editar")
    public ModelAndView actualizarPonencia(HttpServletRequest request, @ModelAttribute("ponencia") Ponencia ponencia) throws Exception {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        if (session.getAttribute("usuario") != null) {
            Usuario usuario = (Usuario) session.getAttribute("usuario");

            /*Ponencia ponencia = Ponencia.builder()
                    .nombrePonencia(ponenciaDTO.getNombrePonencia())
                    .fechaInicio(LocalDate.parse(ponenciaDTO.getFechaInicio()))
                    .fechaFin(LocalDate.parse(ponenciaDTO.getFechaFin()))
                    .lugar(ponenciaDTO.getLugar())
                    .participacion(ponenciaDTO.getParticipacion())
                    .descripcion(ponenciaDTO.getDescripcion())
                    .institucion(ponenciaDTO.getInstitucion())
                    .build();*/
            ponenciaServicio.editarPonencia(ponencia.getIdPonencia(), ponencia);
            mav.setViewName("redirect:/ponencia/verPonencias");
            return mav;
        } else {
            System.out.println("error de logueo");
            session.setAttribute("usuario", new Usuario());
            mav.addObject("usuario", session.getAttribute("usuario"));
            mav.setViewName("redirect:/");
            return mav;
        }
    }

    @RequestMapping("/editar/{idPonencia}")
    public ModelAndView goUpdate(HttpServletRequest request, @PathVariable(value = "idPonencia") long idPonencia) {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();

        if (session.getAttribute("usuario") != null) {
            Ponencia ponencia = ponenciaServicio.buscarPonenciaPorId(idPonencia);

            /*PonenciaDTO ponenciaDTO = PonenciaDTO.builder()
                    .idPonenciaDTO(idPonencia)
                    .nombrePonencia(ponencia.getNombrePonencia())
                    .fechaInicio(ponencia.getFechaInicio().toString())
                    .fechaFin(ponencia.getFechaFin().toString())
                    .lugar(ponencia.getLugar())
                    .participacion(ponencia.getParticipacion())
                    .descripcion(ponencia.getDescripcion())
                    .institucion(ponencia.getInstitucion())
                    .build();*/
            mav.addObject("idponencia", idPonencia);
            mav.addObject("ponencia", ponencia);
            mav.setViewName("editarPonencia");
            return mav;
        } else {
            System.out.println("error de logueo");
            session.setAttribute("usuario", new Usuario());
            mav.addObject("usuario", session.getAttribute("usuario"));
            mav.setViewName("redirect:/");
            return mav;
        }
    }

    @RequestMapping("/eliminar/{idPonencia}")
    public ModelAndView eliminar(HttpServletRequest request, @PathVariable(value = "idPonencia") Long idPonencia) {
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (session.getAttribute("usuario") != null) {
            try {
                ponenciaServicio.eliminarRegistrosUsuarioPonencia(idPonencia, usuario.getIdusuario());
                ponenciaServicio.eliminarPonencia(idPonencia);
                mav.setViewName("redirect:/ponencia/verPonencias");
                return mav;

            } catch (Exception e) {
                e.printStackTrace();
                mav.setViewName("redirect:/ponencia/verPonencias" );
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
}
