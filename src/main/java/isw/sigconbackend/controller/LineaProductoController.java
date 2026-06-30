package isw.sigconbackend.controller;

import isw.sigconbackend.model.LineaProducto;
import isw.sigconbackend.service.LineaProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="api/v1/lineaproducto")
public class LineaProductoController {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    LineaProductoService lineaProductoService;

    @GetMapping
    public ResponseEntity<?> getLineas() {
        try {
            return ResponseEntity.ok(lineaProductoService.getLineasProducto());
        } catch (Exception e) {
            logger.error("Error Inesperado", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> insertLinea(@RequestBody LineaProducto lineaProducto) {
        try {
            LineaProducto nuevaLinea = lineaProductoService.insertLineaProducto(lineaProducto);
            return ResponseEntity.ok(nuevaLinea);
        } catch (Exception e) {
            logger.error("Error Inesperado", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}