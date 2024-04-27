package com.GestionGidisSoft.servicios.impl;

import com.GestionGidisSoft.DTO.LoginRequestDto;
import com.GestionGidisSoft.entidades.Rol;
import com.GestionGidisSoft.entidades.Usuario;
import com.GestionGidisSoft.repositorios.RolRepo;
import com.GestionGidisSoft.repositorios.UsuarioRepo;
import com.GestionGidisSoft.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServicioImpl implements UsuarioServicio {

    @Autowired
    private UsuarioRepo usuarioRepo;

    @Autowired
    private RolRepo rolRepo;

    @Override
    public Usuario guardarUsuario(Usuario usuario, Rol rol) throws Exception {
        Usuario usuarioLocal = usuarioRepo.findByUsername(usuario.getUsername());

        if (usuarioLocal != null) {
            throw new Exception("El usuario ya esta presente");
        } else {
            if (rolRepo.existenRoles(rol.getRolId()) > 0 ) {
                System.out.println("el rol ya existe");
            } else {
                rolRepo.save(rol);
                System.out.println("creando nuevo rol");
            }
            usuarioLocal = usuarioRepo.save(usuario);
            usuarioRepo.actualizarTablaIntermedia(usuario.getIdusuario(), rol.getRolId());
        }
        return usuarioLocal;
    }

    @Override
    public String actualizarUsuario(Long idUsuario, boolean enable) throws Exception {
        String mensaje = "";
        try {
            if (usuarioRepo.actualizarEstado(idUsuario, enable) == 1) {
                mensaje = "Estado del usuario actualizado correctamente";
            } else {
                mensaje = "Hubo un error actualizando";
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("::: Error inesperado ::: " + e.getMessage());
        }
        return mensaje;
    }

    @Override
    public Usuario buscarUsuario(String username) {
        return usuarioRepo.findByUsername(username);
    }

    @Override
    public Usuario getByUsuarioId(Long id) {
        Usuario user = usuarioRepo.findByusuarioId(id);
        return user;
    }

    @Override
    public Usuario buscarUsuarioByEmail(String email) {
        Usuario user = usuarioRepo.findByEmail(email);
        return user;
    }

    @Override
    public void eliminar(long idUsuario) {
        usuarioRepo.deleteById(idUsuario);
    }

    @Override
    public boolean loginUsuario(LoginRequestDto loginRequestDto) {
        return usuarioRepo.existsUsuarioByEmailAndClave(loginRequestDto.getEmail(), loginRequestDto.getClave());
    }

    @Override
    public Rol consultarRolUsuario(Long idUsuario) {
        return rolRepo.findByusuarioId(idUsuario);
    }

    @Override
    public List<Usuario> listarAutoresLibros(Long idLibro, Long idAutor) {
        List<Usuario> listaAutores = usuarioRepo.listarNoCoautoresLibros(idLibro, idAutor);
        return listaAutores;
    }
    @Override
    public List<Usuario> listarCoautoresLibros(Long idLibro, Long idAutor) {
        List<Usuario> coautores =  usuarioRepo.listarCoautores(idLibro, idAutor);
        return coautores;
    }

    @Override
    public List<Usuario> listarUsuarios(Long idUsuarioAdmin) {
        List<Usuario> listaUsuarios =  usuarioRepo.listarUsuarios(idUsuarioAdmin);
        return listaUsuarios;
    }

    @Override
    public List<Usuario> listarAutoresCapitulosLibros(Long idCapituloLibro, Long idAutor) {
        List<Usuario> autores =  usuarioRepo.listarNoCoautoresCapituloLibros(idCapituloLibro, idAutor);
        return autores;
    }

    @Override
    public List<Usuario> listarCoautoresCapitulosLibros(Long idCapituloLibro, Long idAutor) {
        List<Usuario> coautores =  usuarioRepo.listarCoautoresCapituloLibro(idCapituloLibro, idAutor);
        return coautores;
    }

    @Override
    public List<Usuario> listarAutoresArticulos(Long idArticulo, Long idAutor) {
        List<Usuario> autores =  usuarioRepo.listarNoCoautoresArticulos(idArticulo, idAutor);
        return autores;
    }

    @Override
    public List<Usuario> listarCoautoresArticulos(Long idArticulo, Long idAutor) {
        List<Usuario> coautores =  usuarioRepo.listarCoautoresArticulos(idArticulo, idAutor);
        return coautores;
    }

    @Override
    public List<Usuario> listarAutoresProyectosInvestigacion(Long idProyectoInvestigacion, Long idAutor) {
        List<Usuario> autoresProyecInv = usuarioRepo.listarNoCoautoresProyectoInvestigacion(idProyectoInvestigacion, idAutor);
        return autoresProyecInv;
    }

    @Override
    public List<Usuario> listarCoautoresProyectosInvestigacion(Long idProyectoInvestigacion, Long idAutor) {
        List<Usuario> coautoresProyecInv = usuarioRepo.listarCoautoresProyectoInvestigacion(idProyectoInvestigacion, idAutor);
        return coautoresProyecInv;
    }

    @Override
    public boolean existenUsuarios() {
        boolean existen = false;

        if ( usuarioRepo.existenUsuarios() > 0) {
            System.out.println("existen");
            existen = true;
        } else {
            System.out.println("no existen");
        }
        return existen;
    }

}
