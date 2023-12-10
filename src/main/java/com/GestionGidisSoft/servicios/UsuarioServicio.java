package com.GestionGidisSoft.servicios;

import com.GestionGidisSoft.DTO.LoginRequestDto;
import com.GestionGidisSoft.entidades.Rol;
import com.GestionGidisSoft.entidades.Usuario;
import com.GestionGidisSoft.entidades.UsuarioRol;

import java.util.Set;

public interface UsuarioServicio {
    public Usuario guardarUsuario(Usuario usuario, Set<UsuarioRol> usuarioRoles) throws Exception;

    public Usuario actualizarUsuario(Usuario usuario) throws Exception;

    public Usuario buscarUsuario(String username);

    public Usuario getByUsuarioId(Long id);

    public Usuario buscarUsuarioByEmail(String email);

    public void eliminar(long usuarioId);

    boolean loginUsuario(LoginRequestDto loginRequestDto);

    Rol consultarRolUsuario(Long usuarioId);
}
