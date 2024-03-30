package com.GestionGidisSoft.servicios.impl;

import com.GestionGidisSoft.Constantes.Format;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class ArchivosServicioImpl implements ArchivosServicio {

    private final Path UPLOADS_FOLDER  = Paths.get(Format.ARCHIVOS_CARGADOS_PATH) ;

    @Override
    public String guardarSoloUno(MultipartFile file) throws Exception {
        try {
            if (!Files.exists(UPLOADS_FOLDER)) {
                Files.createDirectories(UPLOADS_FOLDER);
            }
            String nombreArchivo = file.getOriginalFilename().replace(" ", "_");
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            String uniqueFilename = timestamp + "_" + nombreArchivo;
//            String uniqueFilename = UUID.randomUUID().toString() + "_" + nombreArchivo;
            Path filePath = this.UPLOADS_FOLDER.resolve(uniqueFilename);
            Files.copy(file.getInputStream(), UPLOADS_FOLDER.resolve(uniqueFilename));
            return uniqueFilename;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Error al guardar el archivo en el servicio");
        }
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
