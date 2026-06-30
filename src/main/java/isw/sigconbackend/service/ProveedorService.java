package isw.sigconbackend.service;

import isw.sigconbackend.model.Proveedor;
import isw.sigconbackend.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorService {
    
    @Autowired
    ProveedorRepository proveedorRepository;

    public List<Proveedor> getProveedores() {
        return proveedorRepository.findAll();
    }

    public Proveedor insertProveedor(Proveedor proveedor) throws Exception {
        // Regla 1: El RUC es obligatorio y debe tener exactamente 11 caracteres
        if (proveedor.getRuc() == null || proveedor.getRuc().trim().length() != 11) {
            throw new Exception("El RUC ingresado no es válido. Debe tener exactamente 11 dígitos.");
        }
        
        // Regla 2: El RUC no puede existir previamente en la base de datos
        if (proveedorRepository.existsByRuc(proveedor.getRuc().trim())) {
            throw new Exception("Error: Ya existe un proveedor registrado con el RUC " + proveedor.getRuc());
        }
        
        return proveedorRepository.save(proveedor);
    }
}