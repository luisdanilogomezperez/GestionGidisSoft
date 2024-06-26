package com.GestionGidisSoft.servicios;

import com.GestionGidisSoft.DTO.LoginRequestDto;
import com.GestionGidisSoft.entidades.Rol;
import com.GestionGidisSoft.entidades.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UsuarioServicio {
    public Usuario guardarUsuario(Usuario usuario, Rol rol) throws Exception;

    public String actualizarUsuario(Long idUsuario, boolean enable) throws Exception;

    public Usuario buscarUsuario(String username);

    public Usuario getByUsuarioId(Long id);

    public Usuario buscarUsuarioByEmail(String email);

    public void eliminar(long usuarioId);

    public boolean loginUsuario(LoginRequestDto loginRequestDto);

    public Rol consultarRolUsuario(Long usuarioId);

    public List<Usuario> listarAutoresLibros(Long idLibro, Long idAutor);

    public List<Usuario> listarCoautoresLibros(Long idLibro, Long idAutor);

    List<Usuario> listarUsuarios(Long idUsuarioAdmin);

    public List<Usuario> listarAutoresCapitulosLibros(Long idCapituloLibro, Long idAutor);

    public List<Usuario> listarCoautoresCapitulosLibros(Long idCapituloLibro, Long idAutor);

    public List<Usuario> listarAutoresArticulos(Long idArticulo, Long idAutor);

    public List<Usuario> listarCoautoresArticulos(Long idArticulo, Long idAutor);


    public List<Usuario> listarAutoresProyectosInvestigacion(Long idProyectoInvestigacion, Long idAutor);

    public List<Usuario> listarCoautoresProyectosInvestigacion(Long idProyectoInvestigacion, Long idAutor);

    public boolean existenUsuarios();

    public String recuperarContrasena(Usuario usuario);
}
