package com.GestionGidisSoft.servicios.impl;

import com.GestionGidisSoft.DTO.LoginRequestDto;
import com.GestionGidisSoft.entidades.Rol;
import com.GestionGidisSoft.entidades.Usuario;
import com.GestionGidisSoft.entidades.UsuarioRol;
import com.GestionGidisSoft.repositorios.RolRepo;
import com.GestionGidisSoft.repositorios.UsuarioRepo;
import com.GestionGidisSoft.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    public Usuario actualizarUsuario(Usuario usuario) throws Exception {
        return null;
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
    public void eliminar(long usuarioId) {
        usuarioRepo.deleteById(usuarioId);
    }

    @Override
    public boolean loginUsuario(LoginRequestDto loginRequestDto) {
        return usuarioRepo.existsUsuarioByEmailAndClave(loginRequestDto.getEmail(), loginRequestDto.getClave());
    }

    @Override
    public Rol consultarRolUsuario(Long usuarioId) {
        return rolRepo.findByusuarioId(usuarioId);
    }

    @Override
    public List<Usuario> listarAutores(Long idLibro, Long idAutor) {
        List<Usuario> listaAutores = usuarioRepo.listarCoautoresLibros(idLibro, idAutor);
        return listaAutores;
    }
    @Override
    public List<Usuario> listarCoautores(Long idLibro, Long idAutor) {
        List<Usuario> coautores =  usuarioRepo.listarCoautores(idLibro, idAutor);
        return coautores;
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
