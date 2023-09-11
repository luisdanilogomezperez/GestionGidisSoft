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

import java.util.Set;

@Service
public class UsuarioServicioImpl implements UsuarioServicio {

    @Autowired
    private UsuarioRepo usuarioRepo;

    @Autowired
    private RolRepo rolRepo;

    @Override
    public Usuario guardarUsuario(Usuario usuario, Set<UsuarioRol> usuarioRoles) throws Exception {
        Usuario usuarioLocal = usuarioRepo.findByUsername(usuario.getUsername());

        if (usuarioLocal != null) {
            System.out.println("El usuario ya existe");
            throw new Exception("El usuario ya esta presente");
        } else {
            for (UsuarioRol usuarioRol : usuarioRoles) {
                rolRepo.save(usuarioRol.getRol());
            }
            usuario.getUsuarioRoles().addAll(usuarioRoles);
            usuarioLocal = usuarioRepo.save(usuario);
        }
        return usuarioLocal;
    }

    @Override
    public Usuario actualizarUsuario(Usuario usuario, Set<UsuarioRol> usuarioRoles) throws Exception {
        return null;
    }

    @Override
    public Usuario buscarUsuario(String username) {
        return usuarioRepo.findByUsername(username);
    }

    @Override
    public void eliminar(long usuarioId) {
        usuarioRepo.deleteById(usuarioId);
    }

    @Override
    public boolean loginUsuario(LoginRequestDto loginRequestDto) {
        return usuarioRepo.existsUsuarioByEmailAndClave(loginRequestDto.getEmail(), loginRequestDto.getClave());
    }

}