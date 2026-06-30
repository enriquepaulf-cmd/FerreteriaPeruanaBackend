package isw.sigconbackend.util;

import isw.sigconbackend.model.Usuario;
import isw.sigconbackend.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Solo inserta el admin si la tabla está vacía (idempotente)
        if (usuarioRepository.count() == 0) {
            Usuario admin = Usuario.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .nombreCompleto("Administrador del Sistema")
                    .rol("ADMIN")
                    .activo(true)
                    .build();
            usuarioRepository.save(admin);
            logger.info("✅ Usuario administrador creado: admin / admin123");
        }
    }
}
