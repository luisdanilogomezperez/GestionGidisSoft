package Ecepciones;

import com.GestionGidisSoft.DTO.ArchivosResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class CargaArchivoEcepcion {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ArchivosResponseDto> handleMaxUploadSizeException(MaxUploadSizeExceededException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArchivosResponseDto("Verifica el tamaño de los archivos"));
    }

    @ExceptionHandler(MultipartException.class)
    public String handleException(MultipartException ex, RedirectAttributes attributes) {
        attributes.addFlashAttribute("mensaje", ex.getCause().getMessage());
        return "redirect:/libros/status";
    }
}
