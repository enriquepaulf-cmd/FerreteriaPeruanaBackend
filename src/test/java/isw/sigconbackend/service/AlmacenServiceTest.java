package isw.sigconbackend.service;

import isw.sigconbackend.model.Almacen;
import isw.sigconbackend.repository.AlmacenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * HU03 - Gestionar Almacenes.
 * Prueba unitaria de la capa de servicio con Mockito.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("HU03 - Prueba unitaria: AlmacenService")
class AlmacenServiceTest {

    @Mock
    private AlmacenRepository almacenRepository;

    @InjectMocks
    private AlmacenService almacenService;

    private Almacen almacenValido;

    @BeforeEach
    void setUp() {
        almacenValido = Almacen.builder()
                .idAlmacen(1)
                .nombre("Almacen Central Lima")
                .tipo("PRINCIPAL")
                .build();
    }

    @Test
    @DisplayName("HU03-T01: getAlmacenes() debe retornar la lista de almacenes")
    void testGetAlmacenesRetornaLista() {
        Almacen secundario = Almacen.builder()
                .idAlmacen(2)
                .nombre("Almacen Arequipa")
                .tipo("SECUNDARIO")
                .build();
        when(almacenRepository.findAll()).thenReturn(Arrays.asList(almacenValido, secundario));

        List<Almacen> resultado = almacenService.getAlmacenes();

        assertThat(resultado).isNotNull().hasSize(2);
        assertThat(resultado.get(0).getNombre()).isEqualTo("Almacen Central Lima");
        verify(almacenRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("HU03-T02: insertAlmacen() debe guardar y retornar el almacen")
    void testInsertAlmacen() {
        when(almacenRepository.save(any(Almacen.class))).thenReturn(almacenValido);

        Almacen guardado = almacenService.insertAlmacen(almacenValido);

        assertThat(guardado).isNotNull();
        assertThat(guardado.getTipo()).isEqualTo("PRINCIPAL");
        verify(almacenRepository, times(1)).save(almacenValido);
    }
}
