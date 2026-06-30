package isw.sigconbackend.repository;

import isw.sigconbackend.model.LineaProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineaProductoRepository extends JpaRepository<LineaProducto, Integer> {
}