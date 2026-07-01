package isw.sigconbackend.service;

import isw.sigconbackend.model.Proveedor;
import isw.sigconbackend.repository.ProveedorRepository;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * HU01 - Gestionar Proveedores.
 * Prueba unitaria de la capa de servicio con Mockito (se simula el repositorio,
 * no se usa base de datos real).
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("HU01 - Prueba unitaria: ProveedorService")
class ProveedorServiceTest {

    @Mock
    private ProveedorRepository proveedorRepository;

    @InjectMocks
    private ProveedorService proveedorService;

    private Proveedor proveedorValido;

    @BeforeEach
    void setUp() {
        proveedorValido = Proveedor.builder()
                .idProveedor(1)
                .nombre("ACEROS AREQUIPA S.A.")
                .ruc("20100123456") // 11 digitos
                .build();
    }

    @Test
    @DisplayName("HU01-T01: getProveedores() debe retornar la lista de proveedores")
    void testGetProveedoresRetornaLista() {
        when(proveedorRepository.findAll()).thenReturn(Arrays.asList(proveedorValido));

        List<Proveedor> resultado = proveedorService.getProveedores();

        assertThat(resultado).isNotNull().hasSize(1);
        assertThat(resultado.get(0).getRuc()).isEqualTo("20100123456");
        verify(proveedorRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("HU01-T02: insertProveedor() con datos validos debe guardar y retornar el proveedor")
    void testInsertProveedorValido() throws Exception {
        when(proveedorRepository.existsByRuc("20100123456")).thenReturn(false);
        when(proveedorRepository.save(any(Proveedor.class))).thenReturn(proveedorValido);

        Proveedor guardado = proveedorService.insertProveedor(proveedorValido);

        assertThat(guardado).isNotNull();
        assertThat(guardado.getRuc()).isEqualTo("20100123456");
        verify(proveedorRepository, times(1)).save(proveedorValido);
    }

    @Test
    @DisplayName("HU01-T03: insertProveedor() con RUC de longitud invalida debe lanzar excepcion")
    void testInsertProveedorRucInvalido() {
        Proveedor invalido = Proveedor.builder()
                .nombre("Proveedor X")
                .ruc("123") // longitud distinta de 11
                .build();

        Exception ex = assertThrows(Exception.class,
                () -> proveedorService.insertProveedor(invalido));

        assertThat(ex.getMessage()).contains("11");
        verify(proveedorRepository, never()).save(any());
    }

    @Test
    @DisplayName("HU01-T04: insertProveedor() con RUC duplicado debe lanzar excepcion")
    void testInsertProveedorRucDuplicado() {
        when(proveedorRepository.existsByRuc("20100123456")).thenReturn(true);

        Exception ex = assertThrows(Exception.class,
                () -> proveedorService.insertProveedor(proveedorValido));

        assertThat(ex.getMessage()).contains("Ya existe");
        verify(proveedorRepository, never()).save(any());
    }
}
