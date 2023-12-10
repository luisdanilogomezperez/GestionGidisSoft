package com.GestionGidisSoft.controlador;

import com.GestionGidisSoft.DTO.ArchivosResponseDto;
import com.GestionGidisSoft.entidades.Libro;
import com.GestionGidisSoft.servicios.impl.ArchivosServicioImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/archivos")
public class ArchivosControlador {

    @Autowired
    private ArchivosServicioImpl archivosServicio;


 }
