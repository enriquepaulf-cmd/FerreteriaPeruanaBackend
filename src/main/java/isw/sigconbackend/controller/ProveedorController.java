package isw.sigconbackend.controller;

import isw.sigconbackend.model.Proveedor;
import isw.sigconbackend.service.ProveedorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="api/v1/proveedor")
public class ProveedorController {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ProveedorService proveedorService;

    @GetMapping
    public ResponseEntity<?> getProveedores() {
        try {
            return ResponseEntity.ok(proveedorService.getProveedores());
        } catch (Exception e) {
            logger.error("Error Inesperado", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> insertProveedor(@RequestBody Proveedor proveedor) {
        try {
            Proveedor nuevoProveedor = proveedorService.insertProveedor(proveedor);
            return ResponseEntity.ok(nuevoProveedor);
        } catch (Exception e) {
            // Atrapamos las validaciones del Service y se las mandamos limpias al Frontend
            logger.error("Error de Validación: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}