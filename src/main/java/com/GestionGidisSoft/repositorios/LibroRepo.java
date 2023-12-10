package com.GestionGidisSoft.repositorios;

import com.GestionGidisSoft.entidades.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface LibroRepo extends JpaRepository<Libro, Long> {

    @Query(value = "SELECT lib.* FROM libro lib "
            + " INNER JOIN usuario_libro usualib on (usualib.libro_id = lib.libro_id) "
            + " WHERE usualib.usuario_id = :usuarioId", nativeQuery = true)
    List<Libro> librosPorUsuario(Long usuarioId);

    @Query(value = "SELECT * FROM libro "
            + " WHERE libro_id = :libroId", nativeQuery = true)
    Libro buscarPorId(Long libroId);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO usuario_libro (libro_id, usuario_id) VALUES (:libroId, :usuarioId)", nativeQuery = true)
    void actualizarTablaIntermedia(Long libroId, Long usuarioId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM usuario_libro WHERE libro_id = :libroId AND usuario_id = :usuarioId", nativeQuery = true)
    void deleteByLibroIdAndUsuarioId(Long libroId, Long usuarioId);

    Optional<Libro> findByIsbn(String isbn);
}

