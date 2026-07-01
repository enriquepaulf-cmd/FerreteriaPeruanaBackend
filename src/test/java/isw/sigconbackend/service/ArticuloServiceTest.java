package isw.sigconbackend.service;

import isw.sigconbackend.model.Articulo;
import isw.sigconbackend.model.LineaProducto;
import isw.sigconbackend.repository.ArticuloRepository;
import isw.sigconbackend.repository.LineaProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * HU02 - Gestionar Articulos (y su Linea de Producto asociada).
 * Prueba unitaria de la capa de servicio con Mockito.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("HU02 - Prueba unitaria: ArticuloService")
class ArticuloServiceTest {

    @Mock
    private ArticuloRepository articuloRepository;

    @Mock
    private LineaProductoRepository lineaProductoRepository;

    @InjectMocks
    private ArticuloService articuloService;

    private LineaProducto linea;
    private Articulo articuloValido;

    @BeforeEach
    void setUp() {
        linea = LineaProducto.builder()
                .idLineaProducto(1)
                .nombre("Cemento y Agregados")
                .build();

        articuloValido = Articulo.builder()
                .idArticulo(1)
                .codigo("ART-001")
                .nombre("Cemento Sol Tipo I")
                .nivelRotacion((short) 1)
                .precioUnitario(new BigDecimal("25.50"))
                .unidadMedida("BOLSA")
                .lineaProducto(linea)
                .build();
    }

    @Test
    @DisplayName("HU02-T01: getArticulos() debe retornar la lista de articulos")
    void testGetArticulosRetornaLista() {
        when(articuloRepository.findAll()).thenReturn(Arrays.asList(articuloValido));

        List<Articulo> resultado = articuloService.getArticulos();

        assertThat(resultado).isNotNull().hasSize(1);
        assertThat(resultado.get(0).getCodigo()).isEqualTo("ART-001");
        verify(articuloRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("HU02-T02: insertArticulo() con datos validos debe guardar y retornar el articulo")
    void testInsertArticuloValido() throws Exception {
        when(articuloRepository.existsByCodigo("ART-001")).thenReturn(false);
        when(lineaProductoRepository.existsById(1)).thenReturn(true);
        when(articuloRepository.save(any(Articulo.class))).thenReturn(articuloValido);

        Articulo guardado = articuloService.insertArticulo(articuloValido);

        assertThat(guardado).isNotNull();
        assertThat(guardado.getCodigo()).isEqualTo("ART-001");
        verify(articuloRepository, times(1)).save(articuloValido);
    }

    @Test
    @DisplayName("HU02-T03: insertArticulo() con codigo duplicado debe lanzar excepcion")
    void testInsertArticuloCodigoDuplicado() {
        when(articuloRepository.existsByCodigo("ART-001")).thenReturn(true);

        Exception ex = assertThrows(Exception.class,
                () -> articuloService.insertArticulo(articuloValido));

        assertThat(ex.getMessage()).contains("Ya existe");
        verify(articuloRepository, never()).save(any());
    }

    @Test
    @DisplayName("HU02-T04: insertArticulo() sin linea de producto debe lanzar excepcion")
    void testInsertArticuloSinLinea() {
        Articulo sinLinea = Articulo.builder()
                .codigo("ART-002")
                .nombre("Articulo sin linea")
                .nivelRotacion((short) 1)
                .precioUnitario(new BigDecimal("10.00"))
                .unidadMedida("UNIDAD")
                .lineaProducto(null)
                .build();
        when(articuloRepository.existsByCodigo("ART-002")).thenReturn(false);

        Exception ex = assertThrows(Exception.class,
                () -> articuloService.insertArticulo(sinLinea));

        assertThat(ex.getMessage()).contains("pertenecer");
        verify(articuloRepository, never()).save(any());
    }

    @Test
    @DisplayName("HU02-T05: insertArticulo() con linea de producto inexistente debe lanzar excepcion")
    void testInsertArticuloLineaInexistente() {
        LineaProducto lineaFantasma = LineaProducto.builder().idLineaProducto(99).build();
        Articulo conLineaMala = Articulo.builder()
                .codigo("ART-003")
                .nombre("Articulo con linea inexistente")
                .nivelRotacion((short) 1)
                .precioUnitario(new BigDecimal("10.00"))
                .unidadMedida("UNIDAD")
                .lineaProducto(lineaFantasma)
                .build();
        when(articuloRepository.existsByCodigo("ART-003")).thenReturn(false);
        when(lineaProductoRepository.existsById(99)).thenReturn(false);

        Exception ex = assertThrows(Exception.class,
                () -> articuloService.insertArticulo(conLineaMala));

        assertThat(ex.getMessage()).contains("no existe");
        verify(articuloRepository, never()).save(any());
    }
}
