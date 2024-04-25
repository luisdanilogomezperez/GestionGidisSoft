package com.GestionGidisSoft.repositorios;

import com.GestionGidisSoft.entidades.Ponencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface PonenciaRepo extends JpaRepository<Ponencia, Long> {

    @Modifying
    @Query("UPDATE Ponencia p SET " +
            "p.titulo = COALESCE(:#{#ponencia.titulo}, p.titulo), " +
            "p.autor = COALESCE(:#{#ponencia.autor}, p.autor), " +
            "p.idAutor = COALESCE(:#{#ponencia.idAutor}, p.idAutor), " +
            "p.ambito = COALESCE(:#{#ponencia.ambito}, p.ambito), " +
            "p.institucion = COALESCE(:#{#ponencia.institucion}, p.institucion), " +
            "p.anio = COALESCE(:#{#ponencia.anio}, p.anio), " +
            "p.mes = COALESCE(:#{#ponencia.mes}, p.mes), " +
            "p.lugar = COALESCE(:#{#ponencia.lugar}, p.lugar) " +
            "WHERE p.idPonencia = :#{#id}"
    )
    void editarPonencia(@Param("id") Long id,@Param("ponencia") Ponencia ponencia);

    @Query(value = " SELECT pon.* FROM ponencias pon "
            + " INNER JOIN usuarioponencia usupon on (usupon.idponencia = pon.id_ponencia) "
            + " WHERE usupon.idusuario = :idUsuario", nativeQuery = true)
    List<Ponencia> listarPonenciasPorAutor(Long idUsuario);

    @Transactional
    @Modifying
    @Query(value = " INSERT INTO usuarioponencia (idusuario, idponencia) VALUES (:idUsuario, :idPonencia )", nativeQuery = true)
    void actualizarTablaIntermedia(Long idUsuario, Long idPonencia);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM usuarioponencia WHERE idponencia = :idPonencia AND idusuario = :idUsuario", nativeQuery = true)
    void eliminarRegistrosUsuarioPonencia(Long idPonencia, Long idUsuario);
}
