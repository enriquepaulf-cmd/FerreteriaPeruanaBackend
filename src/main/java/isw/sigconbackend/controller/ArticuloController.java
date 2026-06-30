package isw.sigconbackend.controller;

import isw.sigconbackend.model.Articulo;
import isw.sigconbackend.service.ArticuloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="api/v1/articulo")
public class ArticuloController {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ArticuloService articuloService;

    @GetMapping
    public ResponseEntity<?> getArticulos() {
        try {
            return ResponseEntity.ok(articuloService.getArticulos());
        } catch (Exception e) {
            logger.error("Error Inesperado", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> insertArticulo(@RequestBody Articulo articulo) {
        try {
            Articulo nuevoArticulo = articuloService.insertArticulo(articulo);
            return ResponseEntity.ok(nuevoArticulo);
        } catch (Exception e) {
            logger.error("Error de Validación: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}