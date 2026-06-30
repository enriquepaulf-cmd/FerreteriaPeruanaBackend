package isw.sigconbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "articulos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Articulo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_articulo")
    private Integer idArticulo;

    @Column(nullable = false, length = 20, unique = true)
    private String codigo;

    @Column(name = "codigo_barras", length = 30, unique = true)
    private String codigoBarras;

    @Column(nullable = false, length = 120)
    private String nombre;

    @Column(name = "nivel_rotacion", nullable = false)
    private Short nivelRotacion;

    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    @Column(name = "unidad_medida", nullable = false, length = 20)
    private String unidadMedida;

    // MAGIA RELACIONAL: Muchos Artículos pertenecen a Una Línea de Producto
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_linea_producto", referencedColumnName = "id_linea_producto", nullable = false)
    private LineaProducto lineaProducto;
}