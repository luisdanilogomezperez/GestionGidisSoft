package com.GestionGidisSoft.servicios;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public interface ArchivosServicio {
    public String guardarSoloUno(MultipartFile file) throws Exception;

    public Resource cargarSoloUno(String name) throws Exception;

    public void guardarVarios(List<MultipartFile> file) throws Exception;

    public Stream<Path> cargarVarios()  throws Exception;
}
