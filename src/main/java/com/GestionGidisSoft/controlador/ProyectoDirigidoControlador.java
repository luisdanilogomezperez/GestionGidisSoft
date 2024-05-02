package com.GestionGidisSoft.controlador;


import com.GestionGidisSoft.entidades.ProyectoDirigido;
import com.GestionGidisSoft.entidades.Usuario;
import com.GestionGidisSoft.servicios.impl.ProyectoDirigidoServicioImpl;
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
@RequestMapping("/proyectoDirigido")
@RequiredArgsConstructor
public class ProyectoDirigidoControlador {

    @Autowired
    ProyectoDirigidoServicioImpl proyectoDirigidoServicio;


    @GetMapping("/verProyectoDirigidos")
    public ModelAndView goProyectoDirigidos(HttpServletRequest request) throws Exception{
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        try {
            if (session.getAttribute("usuario") != null) {
                Usuario usuario = (Usuario) session.getAttribute("usuario");
                List<ProyectoDirigido> proyectoDirigidos = proyectoDirigidoServicio.findByUsuarioId(usuario.getIdusuario());
                if (proyectoDirigidos == null){
                    mav.addObject("mensaje", "Aún no hay registros");
                }
                mav.addObject("listaProyectoDirigidos", proyectoDirigidos);
                mav.addObject("usuario", usuario);
                mav.setViewName("listarProyectoDirigidos");
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
    public ModelAndView nuevoProyectoDirigido(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        if (session.getAttribute("usuario") != null) {
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            mav.addObject("usuario", usuario);
            mav.addObject("proyectoDirigido", new ProyectoDirigido());
            mav.setViewName("registrarProyectoDirigido");
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
    public ModelAndView guardarProyectoDirigido(HttpServletRequest request, @ModelAttribute("proyectoDirigido") ProyectoDirigido proyectoDirigido) throws Exception {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        if (session.getAttribute("usuario") != null) {

            Usuario usuario = (Usuario) session.getAttribute("usuario");
            /*ProyectoDirigido proyectoDirigido = ProyectoDirigido.builder()
                    .nombreProyectoDirigido(proyectoDirigidoDTO.getNombreProyectoDirigido())
                    .fechaInicio(LocalDate.parse(proyectoDirigidoDTO.getFechaInicio()))
                    .fechaFin(LocalDate.parse(proyectoDirigidoDTO.getFechaFin()))
                    .lugar(proyectoDirigidoDTO.getLugar())
                    .participacion(proyectoDirigidoDTO.getParticipacion())
                    .descripcion(proyectoDirigidoDTO.getDescripcion())
                    .institucion(proyectoDirigidoDTO.getInstitucion())
                    .build();*/
            proyectoDirigidoServicio.guardarProyectoDirigido(proyectoDirigido);
            proyectoDirigidoServicio.actualizarTablaIntermedia( proyectoDirigido.getIdProyectoDirigido(), usuario.getIdusuario());
            mav.setViewName("redirect:/proyectoDirigido/verProyectoDirigidos");
            return mav;
        } else {
            System.out.println("error de logueo");
            session.setAttribute("usuario", new Usuario());
            mav.addObject("usuario", session.getAttribute("usuario"));
            mav.setViewName("redirect:/");
            return mav;
        }
    }

    @GetMapping("/verProyectoDirigido/{id}")
    public ResponseEntity<ProyectoDirigido> buscarProyectoDirigidoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(proyectoDirigidoServicio.buscarProyectoDirigidoPorId(id));
    }

    @GetMapping("/detalle/{id}")
    public ModelAndView goDetail(HttpServletRequest request, @PathVariable(value = "id") long id) {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        if (session.getAttribute("usuario") != null) {
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            ProyectoDirigido proyectoDirigido = proyectoDirigidoServicio.buscarProyectoDirigidoPorId(id);

            mav.addObject("proyectoDirigido", proyectoDirigido);
            mav.addObject("usuario", usuario);
            mav.setViewName("verDetallesProyectoDirigido");
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
    public ResponseEntity<Void> crearProyectoDirigido(@RequestBody ProyectoDirigido proyectoDirigido) {
        ProyectoDirigido proyectoDirigidoAGuardar = proyectoDirigido;
        proyectoDirigidoServicio.crearProyectoDirigido(proyectoDirigidoAGuardar);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/editarProyectoDirigido/{id}")
    public ResponseEntity<Void> editarProyectoDirigido(@PathVariable Long id, @RequestBody ProyectoDirigido proyectoDirigido) {
        proyectoDirigidoServicio.editarProyectoDirigido(id, proyectoDirigido);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/editar")
    public ModelAndView actualizarProyectoDirigido(HttpServletRequest request, @ModelAttribute("proyectoDirigido") ProyectoDirigido proyectoDirigido) throws Exception {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        if (session.getAttribute("usuario") != null) {
            Usuario usuario = (Usuario) session.getAttribute("usuario");

            /*ProyectoDirigido proyectoDirigido = ProyectoDirigido.builder()
                    .nombreProyectoDirigido(proyectoDirigidoDTO.getNombreProyectoDirigido())
                    .fechaInicio(LocalDate.parse(proyectoDirigidoDTO.getFechaInicio()))
                    .fechaFin(LocalDate.parse(proyectoDirigidoDTO.getFechaFin()))
                    .lugar(proyectoDirigidoDTO.getLugar())
                    .participacion(proyectoDirigidoDTO.getParticipacion())
                    .descripcion(proyectoDirigidoDTO.getDescripcion())
                    .institucion(proyectoDirigidoDTO.getInstitucion())
                    .build();*/
            proyectoDirigidoServicio.editarProyectoDirigido(proyectoDirigido.getIdProyectoDirigido(), proyectoDirigido);
            mav.setViewName("redirect:/proyectoDirigido/verProyectoDirigidos");
            return mav;
        } else {
            System.out.println("error de logueo");
            session.setAttribute("usuario", new Usuario());
            mav.addObject("usuario", session.getAttribute("usuario"));
            mav.setViewName("redirect:/");
            return mav;
        }
    }

    @RequestMapping("/editar/{idProyectoDirigido}")
    public ModelAndView goUpdate(HttpServletRequest request, @PathVariable(value = "idProyectoDirigido") long idProyectoDirigido) {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();

        if (session.getAttribute("usuario") != null) {
            ProyectoDirigido proyectoDirigido = proyectoDirigidoServicio.buscarProyectoDirigidoPorId(idProyectoDirigido);

            /*ProyectoDirigidoDTO proyectoDirigidoDTO = ProyectoDirigidoDTO.builder()
                    .idProyectoDirigidoDTO(idProyectoDirigido)
                    .nombreProyectoDirigido(proyectoDirigido.getNombreProyectoDirigido())
                    .fechaInicio(proyectoDirigido.getFechaInicio().toString())
                    .fechaFin(proyectoDirigido.getFechaFin().toString())
                    .lugar(proyectoDirigido.getLugar())
                    .participacion(proyectoDirigido.getParticipacion())
                    .descripcion(proyectoDirigido.getDescripcion())
                    .institucion(proyectoDirigido.getInstitucion())
                    .build();*/
            mav.addObject("idproyectoDirigido", idProyectoDirigido);
            mav.addObject("proyectoDirigido", proyectoDirigido);
            mav.setViewName("editarProyectoDirigido");
            return mav;
        } else {
            System.out.println("error de logueo");
            session.setAttribute("usuario", new Usuario());
            mav.addObject("usuario", session.getAttribute("usuario"));
            mav.setViewName("redirect:/");
            return mav;
        }
    }

    @RequestMapping("/eliminar/{idProyectoDirigido}")
    public ModelAndView eliminar(HttpServletRequest request, @PathVariable(value = "idProyectoDirigido") Long idProyectoDirigido) {
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (session.getAttribute("usuario") != null) {
            try {
                proyectoDirigidoServicio.eliminarRegistrosUsuarioProyectoDirigido(idProyectoDirigido, usuario.getIdusuario());
                proyectoDirigidoServicio.eliminarProyectoDirigido(idProyectoDirigido);
                mav.setViewName("redirect:/proyectoDirigido/verProyectoDirigidos");
                return mav;

            } catch (Exception e) {
                e.printStackTrace();
                mav.setViewName("redirect:/proyectoDirigido/verProyectoDirigidos" );
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
