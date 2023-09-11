package com.GestionGidisSoft.controlador;

import com.GestionGidisSoft.DTO.LoginRequestDto;
import com.GestionGidisSoft.DTO.LoginResponseDto;
import com.GestionGidisSoft.DTO.RegistroResponseDto;
import com.GestionGidisSoft.entidades.Rol;
import com.GestionGidisSoft.entidades.Usuario;
import com.GestionGidisSoft.entidades.UsuarioRol;
import com.GestionGidisSoft.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @PostMapping("/guardar")
    public ResponseEntity<RegistroResponseDto> guardarUsuario(@RequestBody Usuario usuario){
        try {
            Set<UsuarioRol> roles = new HashSet<>();

            Rol rol = new Rol();
            rol.setRolId(1L);
            rol.setNombre("Admin");

            UsuarioRol usuarioRol = new UsuarioRol();
            usuarioRol.setUsuario(usuario);
            usuarioRol.setRol(rol);

            roles.add(usuarioRol);

            usuarioServicio.guardarUsuario(usuario, roles);

            return new ResponseEntity(new RegistroResponseDto(true),HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println(":::::::::::Error:::::::::::::::" + e.getMessage());
            return new ResponseEntity(new RegistroResponseDto(false),HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/buscar/{username}")
    public Usuario buscarUsuario(@PathVariable("username") String username){
        return  usuarioServicio.buscarUsuario(username);
    }

    @DeleteMapping("/eliminar/{usuarioId}")
    public void eliminarUsuario(@PathVariable("usuarioId")  Long usuarioId){

        usuarioServicio.eliminar(usuarioId);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginUsuario(
            @RequestBody LoginRequestDto loginRequestDto
    ) {
        if (usuarioServicio.loginUsuario(loginRequestDto)) {
            return ResponseEntity.ok(new LoginResponseDto(true));
        }
        return new ResponseEntity<> (new LoginResponseDto(false), HttpStatus.BAD_REQUEST);

    }
}
