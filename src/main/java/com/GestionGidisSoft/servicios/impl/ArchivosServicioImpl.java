package com.GestionGidisSoft.servicios.impl;

import com.GestionGidisSoft.servicios.ArchivosServicio;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class ArchivosServicioImpl implements ArchivosServicio {

    private final Path UPLOADS_FOLDER  = Paths.get("archivosCargados") ;

    @Override
    public String guardarSoloUno(MultipartFile file) throws Exception {
        String nombreArchivo = file.getOriginalFilename().replace(" ", "_");
        String uniqueFilename = UUID.randomUUID().toString() + "_" + nombreArchivo;
        Files.copy(file.getInputStream(), this.UPLOADS_FOLDER.resolve(uniqueFilename));
        return uniqueFilename;
    }

    @Override
    public Resource cargarSoloUno(String name) throws Exception {
        return null;
    }

    @Override
    public void guardarVarios(List<MultipartFile> file) throws Exception {
        for (MultipartFile file1: file) {
            this.guardarSoloUno(file1);
        }
    }

    @Override
    public Stream<Path> cargarVarios() throws Exception {
        return null;
    }
}
