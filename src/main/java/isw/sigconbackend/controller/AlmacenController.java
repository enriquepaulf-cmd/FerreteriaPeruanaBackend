package isw.sigconbackend.controller;

import isw.sigconbackend.model.Almacen;
import isw.sigconbackend.service.AlmacenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="api/v1/almacen")
public class AlmacenController {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AlmacenService almacenService;

    @GetMapping
    public ResponseEntity<?> getAlmacenes() {
        try {
            return ResponseEntity.ok(almacenService.getAlmacenes());
        } catch (Exception e) {
            logger.error("Error Inesperado", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> insertAlmacen(@RequestBody Almacen almacen) {
        try {
            Almacen nuevoAlmacen = almacenService.insertAlmacen(almacen);
            return ResponseEntity.ok(nuevoAlmacen);
        } catch (Exception e) {
            logger.error("Error de Validación: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}