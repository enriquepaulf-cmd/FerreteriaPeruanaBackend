package isw.sigconbackend.controller;

import isw.sigconbackend.dto.LoginRequest;
import isw.sigconbackend.dto.LoginResponse;
import isw.sigconbackend.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/auth")
public class AuthController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            LoginResponse response = authService.autenticar(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.warn("Intento de login fallido para: " + request.getUsername());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
