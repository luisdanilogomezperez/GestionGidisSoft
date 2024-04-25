package com.GestionGidisSoft.controlador;

import com.GestionGidisSoft.DTO.EventoDTO;
import com.GestionGidisSoft.entidades.Evento;
import com.GestionGidisSoft.entidades.Usuario;
import com.GestionGidisSoft.servicios.impl.EventoServicioImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/evento")
@RequiredArgsConstructor
public class EventoControlador {
    private final EventoServicioImpl eventoServicio;


    @GetMapping("/verEventos")
    public ModelAndView goEventos(HttpServletRequest request) throws Exception{
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        try {
            if (session.getAttribute("usuario") != null) {
                Usuario usuario = (Usuario) session.getAttribute("usuario");
                List<Evento> eventos = eventoServicio.findByUsuarioId(usuario.getIdusuario());
                if (eventos == null){
                    mav.addObject("mensaje", "Aún no hay registros");
                }
                mav.addObject("listaEventos", eventos);
                mav.addObject("usuario", usuario);
                mav.setViewName("listarEventos");
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
    public ModelAndView nuevoEvento(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        if (session.getAttribute("usuario") != null) {
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            mav.addObject("usuarioId", usuario.getIdusuario());
            mav.addObject("evento", new Evento());
            mav.setViewName("registrarEvento");
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
    public ModelAndView guardarEvento(HttpServletRequest request, @ModelAttribute("evento") EventoDTO eventoDTO) throws Exception {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        log.info("Evento es {}", eventoDTO.toString() );
        if (session.getAttribute("usuario") != null) {

                Usuario usuario = (Usuario) session.getAttribute("usuario");
                Evento evento = Evento.builder()
                        .nombreEvento(eventoDTO.getNombreEvento())
                        .fechaInicio(LocalDate.parse(eventoDTO.getFechaInicio()))
                        .fechaFin(LocalDate.parse(eventoDTO.getFechaFin()))
                        .lugar(eventoDTO.getLugar())
                        .participacion(eventoDTO.getParticipacion())
                        .descripcion(eventoDTO.getDescripcion())
                        .institucion(eventoDTO.getInstitucion())
                        .build();
                eventoServicio.guardarEvento(evento);
                eventoServicio.actualizarTablaIntermedia( evento.getIdEvento(), usuario.getIdusuario());
                mav.setViewName("redirect:/evento/verEventos");
                return mav;
        } else {
            System.out.println("error de logueo");
            session.setAttribute("usuario", new Usuario());
            mav.addObject("usuario", session.getAttribute("usuario"));
            mav.setViewName("redirect:/");
            return mav;
        }
    }

    @GetMapping("/verEvento/{id}")
    public ResponseEntity<Evento> buscarEventoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(eventoServicio.buscarEventoPorId(id));
    }

    @GetMapping("/detalle/{id}")
    public ModelAndView goDetail(HttpServletRequest request, @PathVariable(value = "id") long id) {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        if (session.getAttribute("usuario") != null) {
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            Evento evento = eventoServicio.buscarEventoPorId(id);

            mav.addObject("evento", evento);
            mav.addObject("usuario", usuario);
            mav.setViewName("verDetallesEvento");
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
    public ResponseEntity<Void> crearEvento(@RequestBody Evento evento) {
        Evento eventoAGuardar = evento;
        eventoServicio.crearEvento(eventoAGuardar);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/editarEvento/{id}")
    public ResponseEntity<Void> editarEvento(@PathVariable Long id, @RequestBody Evento evento) {
        eventoServicio.editarEvento(id, evento);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/editar")
    public ModelAndView actualizarEvento(HttpServletRequest request, @ModelAttribute("evento") EventoDTO eventoDTO) throws Exception {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        if (session.getAttribute("usuario") != null) {
            Usuario usuario = (Usuario) session.getAttribute("usuario");

            Evento evento = Evento.builder()
                    .nombreEvento(eventoDTO.getNombreEvento())
                    .fechaInicio(LocalDate.parse(eventoDTO.getFechaInicio()))
                    .fechaFin(LocalDate.parse(eventoDTO.getFechaFin()))
                    .lugar(eventoDTO.getLugar())
                    .participacion(eventoDTO.getParticipacion())
                    .descripcion(eventoDTO.getDescripcion())
                    .institucion(eventoDTO.getInstitucion())
                    .build();

            eventoServicio.editarEvento(eventoDTO.getIdEventoDTO(), evento);
            mav.setViewName("redirect:/evento/verEventos");
            return mav;
        } else {
            System.out.println("error de logueo");
            session.setAttribute("usuario", new Usuario());
            mav.addObject("usuario", session.getAttribute("usuario"));
            mav.setViewName("redirect:/");
            return mav;
        }
    }

    @RequestMapping("/editar/{idEvento}")
    public ModelAndView goUpdate(HttpServletRequest request, @PathVariable(value = "idEvento") long idEvento) {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();

        if (session.getAttribute("usuario") != null) {
            Evento evento = eventoServicio.buscarEventoPorId(idEvento);

            EventoDTO eventoDTO = EventoDTO.builder()
                    .idEventoDTO(idEvento)
                    .nombreEvento(evento.getNombreEvento())
                    .fechaInicio(evento.getFechaInicio().toString())
                    .fechaFin(evento.getFechaFin().toString())
                    .lugar(evento.getLugar())
                    .participacion(evento.getParticipacion())
                    .descripcion(evento.getDescripcion())
                    .institucion(evento.getInstitucion())
                    .build();
            mav.addObject("evento", eventoDTO);
            mav.setViewName("editarEvento");
            return mav;
        } else {
            System.out.println("error de logueo");
            session.setAttribute("usuario", new Usuario());
            mav.addObject("usuario", session.getAttribute("usuario"));
            mav.setViewName("redirect:/");
            return mav;
        }
    }

    @RequestMapping("/eliminar/{idEvento}")
    public ModelAndView eliminar(HttpServletRequest request, @PathVariable(value = "idEvento") Long idEvento) {
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (session.getAttribute("usuario") != null) {
            try {
                eventoServicio.eliminarRegistrosUsuarioEvento(idEvento, usuario.getIdusuario());
                eventoServicio.eliminarEvento(idEvento);
                mav.setViewName("redirect:/evento/verEventos");
                return mav;

            } catch (Exception e) {
                e.printStackTrace();
                mav.setViewName("redirect:/evento/verEventos" );
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
