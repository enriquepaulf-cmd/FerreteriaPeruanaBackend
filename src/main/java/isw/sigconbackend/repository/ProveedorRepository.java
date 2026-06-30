package isw.sigconbackend.repository;

import isw.sigconbackend.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {
    
    // Método autogenerado por Spring para validar duplicados
    boolean existsByRuc(String ruc);
}