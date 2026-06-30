package isw.sigconbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "almacenes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Almacen {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_almacen")
    private Integer idAlmacen;

    @Column(nullable = false, length = 80)
    private String nombre;

    @Column(nullable = false, length = 40)
    private String tipo;
}