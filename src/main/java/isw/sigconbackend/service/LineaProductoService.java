package isw.sigconbackend.service;

import isw.sigconbackend.model.LineaProducto;
import isw.sigconbackend.repository.LineaProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LineaProductoService {
    
    @Autowired
    LineaProductoRepository lineaProductoRepository;

    public List<LineaProducto> getLineasProducto() {
        return lineaProductoRepository.findAll();
    }

    public LineaProducto insertLineaProducto(LineaProducto linea) {
        return lineaProductoRepository.save(linea);
    }
}