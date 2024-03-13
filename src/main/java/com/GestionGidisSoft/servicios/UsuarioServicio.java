package com.GestionGidisSoft.servicios;

import com.GestionGidisSoft.DTO.LoginRequestDto;
import com.GestionGidisSoft.entidades.Rol;
import com.GestionGidisSoft.entidades.Usuario;
import com.GestionGidisSoft.entidades.UsuarioRol;

import java.util.List;
import java.util.Set;

public interface UsuarioServicio {
    public Usuario guardarUsuario(Usuario usuario, Rol rol) throws Exception;

    public Usuario actualizarUsuario(Usuario usuario) throws Exception;

    public Usuario buscarUsuario(String username);

    public Usuario getByUsuarioId(Long id);

    public Usuario buscarUsuarioByEmail(String email);

    public void eliminar(long usuarioId);

    public boolean loginUsuario(LoginRequestDto loginRequestDto);

    public Rol consultarRolUsuario(Long usuarioId);

    public List<Usuario> listarAutoresLibros(Long idLibro, Long idAutor);

    public List<Usuario> listarCoautoresLibros(Long idLibro, Long idAutor);

    public List<Usuario> listarAutoresCapitulosLibros(Long idCapituloLibro, Long idAutor);

    public List<Usuario> listarCoautoresCapitulosLibros(Long idCapituloLibro, Long idAutor);

    public boolean existenUsuarios();
}
