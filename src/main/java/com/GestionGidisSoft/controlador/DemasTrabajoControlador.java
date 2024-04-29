package com.GestionGidisSoft.controlador;


import com.GestionGidisSoft.entidades.DemasTrabajo;
import com.GestionGidisSoft.entidades.Usuario;
import com.GestionGidisSoft.servicios.impl.DemasTrabajoServicioImpl;
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
@RequestMapping("/demasTrabajo")
@RequiredArgsConstructor
public class DemasTrabajoControlador {

    @Autowired
    DemasTrabajoServicioImpl demasTrabajoServicio;


    @GetMapping("/verDemasTrabajos")
    public ModelAndView goDemasTrabajos(HttpServletRequest request) throws Exception{
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        try {
            if (session.getAttribute("usuario") != null) {
                Usuario usuario = (Usuario) session.getAttribute("usuario");
                List<DemasTrabajo> demasTrabajos = demasTrabajoServicio.findByUsuarioId(usuario.getIdusuario());
                if (demasTrabajos == null){
                    mav.addObject("mensaje", "Aún no hay registros");
                }
                mav.addObject("listaDemasTrabajos", demasTrabajos);
                mav.addObject("usuario", usuario);
                mav.setViewName("listarDemasTrabajos");
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
    public ModelAndView nuevoDemasTrabajo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        if (session.getAttribute("usuario") != null) {
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            mav.addObject("usuario", usuario);
            mav.addObject("demasTrabajo", new DemasTrabajo());
            mav.setViewName("registrarDemasTrabajo");
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
    public ModelAndView guardarDemasTrabajo(HttpServletRequest request, @ModelAttribute("demasTrabajo") DemasTrabajo demasTrabajo) throws Exception {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        if (session.getAttribute("usuario") != null) {

            Usuario usuario = (Usuario) session.getAttribute("usuario");
            /*DemasTrabajo demasTrabajo = DemasTrabajo.builder()
                    .nombreDemasTrabajo(demasTrabajoDTO.getNombreDemasTrabajo())
                    .fechaInicio(LocalDate.parse(demasTrabajoDTO.getFechaInicio()))
                    .fechaFin(LocalDate.parse(demasTrabajoDTO.getFechaFin()))
                    .lugar(demasTrabajoDTO.getLugar())
                    .participacion(demasTrabajoDTO.getParticipacion())
                    .descripcion(demasTrabajoDTO.getDescripcion())
                    .institucion(demasTrabajoDTO.getInstitucion())
                    .build();*/

            demasTrabajoServicio.guardarDemasTrabajo(demasTrabajo);
            demasTrabajoServicio.actualizarTablaIntermedia( demasTrabajo.getIdDemasTrabajo(), usuario.getIdusuario());
            mav.setViewName("redirect:/demasTrabajo/verDemasTrabajos");
            return mav;
        } else {
            System.out.println("error de logueo");
            session.setAttribute("usuario", new Usuario());
            mav.addObject("usuario", session.getAttribute("usuario"));
            mav.setViewName("redirect:/");
            return mav;
        }
    }

    @GetMapping("/verDemasTrabajo/{id}")
    public ResponseEntity<DemasTrabajo> buscarDemasTrabajoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(demasTrabajoServicio.buscarDemasTrabajoPorId(id));
    }

    @GetMapping("/detalle/{id}")
    public ModelAndView goDetail(HttpServletRequest request, @PathVariable(value = "id") long id) {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        if (session.getAttribute("usuario") != null) {
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            DemasTrabajo demasTrabajo = demasTrabajoServicio.buscarDemasTrabajoPorId(id);

            mav.addObject("demasTrabajo", demasTrabajo);
            mav.addObject("usuario", usuario);
            mav.setViewName("verDetallesDemasTrabajo");
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
    public ResponseEntity<Void> crearDemasTrabajo(@RequestBody DemasTrabajo demasTrabajo) {
        DemasTrabajo demasTrabajoAGuardar = demasTrabajo;
        demasTrabajoServicio.crearDemasTrabajo(demasTrabajoAGuardar);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/editarDemasTrabajo/{id}")
    public ResponseEntity<Void> editarDemasTrabajo(@PathVariable Long id, @RequestBody DemasTrabajo demasTrabajo) {
        demasTrabajoServicio.editarDemasTrabajo(id, demasTrabajo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/editar")
    public ModelAndView actualizarDemasTrabajo(HttpServletRequest request, @ModelAttribute("demasTrabajo") DemasTrabajo demasTrabajo) throws Exception {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        if (session.getAttribute("usuario") != null) {
            Usuario usuario = (Usuario) session.getAttribute("usuario");

            /*DemasTrabajo demasTrabajo = DemasTrabajo.builder()
                    .nombreDemasTrabajo(demasTrabajoDTO.getNombreDemasTrabajo())
                    .fechaInicio(LocalDate.parse(demasTrabajoDTO.getFechaInicio()))
                    .fechaFin(LocalDate.parse(demasTrabajoDTO.getFechaFin()))
                    .lugar(demasTrabajoDTO.getLugar())
                    .participacion(demasTrabajoDTO.getParticipacion())
                    .descripcion(demasTrabajoDTO.getDescripcion())
                    .institucion(demasTrabajoDTO.getInstitucion())
                    .build();*/
            demasTrabajoServicio.editarDemasTrabajo(demasTrabajo.getIdDemasTrabajo(), demasTrabajo);
            mav.setViewName("redirect:/demasTrabajo/verDemasTrabajos");
            return mav;
        } else {
            System.out.println("error de logueo");
            session.setAttribute("usuario", new Usuario());
            mav.addObject("usuario", session.getAttribute("usuario"));
            mav.setViewName("redirect:/");
            return mav;
        }
    }

    @RequestMapping("/editar/{idDemasTrabajo}")
    public ModelAndView goUpdate(HttpServletRequest request, @PathVariable(value = "idDemasTrabajo") long idDemasTrabajo) {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();

        if (session.getAttribute("usuario") != null) {
            DemasTrabajo demasTrabajo = demasTrabajoServicio.buscarDemasTrabajoPorId(idDemasTrabajo);

            /*DemasTrabajoDTO demasTrabajoDTO = DemasTrabajoDTO.builder()
                    .idDemasTrabajoDTO(idDemasTrabajo)
                    .nombreDemasTrabajo(demasTrabajo.getNombreDemasTrabajo())
                    .fechaInicio(demasTrabajo.getFechaInicio().toString())
                    .fechaFin(demasTrabajo.getFechaFin().toString())
                    .lugar(demasTrabajo.getLugar())
                    .participacion(demasTrabajo.getParticipacion())
                    .descripcion(demasTrabajo.getDescripcion())
                    .institucion(demasTrabajo.getInstitucion())
                    .build();*/
            mav.addObject("iddemasTrabajo", idDemasTrabajo);
            mav.addObject("demasTrabajo", demasTrabajo);
            mav.setViewName("editarDemasTrabajo");
            return mav;
        } else {
            System.out.println("error de logueo");
            session.setAttribute("usuario", new Usuario());
            mav.addObject("usuario", session.getAttribute("usuario"));
            mav.setViewName("redirect:/");
            return mav;
        }
    }

    @RequestMapping("/eliminar/{idDemasTrabajo}")
    public ModelAndView eliminar(HttpServletRequest request, @PathVariable(value = "idDemasTrabajo") Long idDemasTrabajo) {
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (session.getAttribute("usuario") != null) {
            try {
                demasTrabajoServicio.eliminarRegistrosUsuarioDemasTrabajo(idDemasTrabajo, usuario.getIdusuario());
                demasTrabajoServicio.eliminarDemasTrabajo(idDemasTrabajo);
                mav.setViewName("redirect:/demasTrabajo/verDemasTrabajos");
                return mav;

            } catch (Exception e) {
                e.printStackTrace();
                mav.setViewName("redirect:/demasTrabajo/verDemasTrabajos" );
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
