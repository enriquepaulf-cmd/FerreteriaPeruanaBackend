package isw.sigconbackend.service;

import isw.sigconbackend.model.Articulo;
import isw.sigconbackend.repository.ArticuloRepository;
import isw.sigconbackend.repository.LineaProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticuloService {
    
    @Autowired
    ArticuloRepository articuloRepository;

    @Autowired
    LineaProductoRepository lineaProductoRepository;

    public List<Articulo> getArticulos() {
        return articuloRepository.findAll();
    }

    public Articulo insertArticulo(Articulo articulo) throws Exception {
        
        // 1. Validar que el código no exista ya
        if (articuloRepository.existsByCodigo(articulo.getCodigo().trim())) {
            throw new Exception("Error: Ya existe un artículo con el código " + articulo.getCodigo());
        }

        // 2. Validar que nos hayan enviado una línea de producto
        if (articulo.getLineaProducto() == null || articulo.getLineaProducto().getIdLineaProducto() == null) {
            throw new Exception("Error: El artículo debe pertenecer a una línea de producto válida.");
        }

        // 3. Validar que la línea de producto exista físicamente en la BD
        Integer idLinea = articulo.getLineaProducto().getIdLineaProducto();
        if (!lineaProductoRepository.existsById(idLinea)) {
            throw new Exception("Error: La línea de producto con ID " + idLinea + " no existe.");
        }

        return articuloRepository.save(articulo);
    }
}