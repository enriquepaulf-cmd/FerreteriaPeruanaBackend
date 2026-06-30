package isw.sigconbackend.repository;

import isw.sigconbackend.model.Articulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticuloRepository extends JpaRepository<Articulo, Integer> {
    
    // Validar si el código del producto ya fue registrado
    boolean existsByCodigo(String codigo);
}