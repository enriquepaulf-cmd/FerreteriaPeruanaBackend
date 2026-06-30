package isw.sigconbackend.service;

import isw.sigconbackend.dto.LoginRequest;
import isw.sigconbackend.dto.LoginResponse;
import isw.sigconbackend.model.Usuario;
import isw.sigconbackend.repository.UsuarioRepository;
import isw.sigconbackend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    public LoginResponse autenticar(LoginRequest request) throws Exception {

        // 1. Buscar usuario por username
        Usuario usuario = usuarioRepository.findByUsername(request.getUsername().trim())
                .orElseThrow(() -> new Exception("Usuario o contraseña incorrectos."));

        // 2. Verificar que la cuenta esté activa
        if (!usuario.getActivo()) {
            throw new Exception("Esta cuenta está desactivada. Contacta al administrador.");
        }

        // 3. Verificar la contraseña con BCrypt
        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new Exception("Usuario o contraseña incorrectos.");
        }

        // 4. Generar JWT y devolver la respuesta
        String token = jwtUtil.generarToken(usuario);
        return LoginResponse.builder()
                .token(token)
                .nombreCompleto(usuario.getNombreCompleto())
                .rol(usuario.getRol())
                .build();
    }
}
