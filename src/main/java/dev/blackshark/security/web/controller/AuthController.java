package dev.blackshark.security.web.controller;

import dev.blackshark.domain.dto.MensajeDTO;
import dev.blackshark.domain.dto.NuevoUsuarioDTO;
import dev.blackshark.domain.service.RolService;
import dev.blackshark.domain.service.UsuarioService;
import dev.blackshark.email.domain.service.EmailServiceImpl;
import dev.blackshark.persistance.entity.Rol;
import dev.blackshark.persistance.entity.RolNombre;
import dev.blackshark.persistance.entity.Usuario;
import dev.blackshark.security.domain.dto.JwtDTO;
import dev.blackshark.security.domain.dto.LoginUsuario;
import dev.blackshark.security.domain.service.UserDetailsServiceIml;
import dev.blackshark.security.jwt.JwtProvider;
import dev.blackshark.security.persistance.entity.UsuarioPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private RolService rolService;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserDetailsServiceIml userDetailsServiceIml;
    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private HttpServletRequest request;

    @PostMapping("")
    public ResponseEntity<MensajeDTO> nuevo(@Valid @RequestBody NuevoUsuarioDTO nuevoUsuarioDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new MensajeDTO("Error al crear usuario"));
        }

        if(usuarioService.existsByUsername(nuevoUsuarioDTO.getUsername())) {
            return ResponseEntity.badRequest().body(new MensajeDTO("El usuario ya existe"));
        }

        if(usuarioService.existsByEmail(nuevoUsuarioDTO.getEmail())) {
            return ResponseEntity.badRequest().body(new MensajeDTO("El email ya existe"));
        }

        String to = nuevoUsuarioDTO.getEmail();
        String subject = "Creacion de cuenta";
        String body = "Hola " + nuevoUsuarioDTO.getNombre() + "!\n" +
                "Tu cuenta ha sido creada con exito.\n" +
                "Tu usuario es: " + nuevoUsuarioDTO.getUsername() + "\n" +
                "Este es un sistema de gestion de productos, por favor no responda a este mensaje.\n" +
                "Gracias por utilizar nuestro sistema.";

        emailService.sendEmail(to, subject, body);

        Usuario
                usuario = new Usuario(nuevoUsuarioDTO.getNombre(), nuevoUsuarioDTO.getUsername(), nuevoUsuarioDTO.getEmail(),
                passwordEncoder.encode(nuevoUsuarioDTO.getPassword()));

        Set<Rol> roles = new HashSet<>();
        roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());
        if(nuevoUsuarioDTO.getRoles().contains("admin")) {
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
        }

        usuario.setRoles(roles);
        usuarioService.save(usuario);

        return ResponseEntity.created(null).body(new MensajeDTO("Usuario creado"));

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginUsuario loginUsuarioDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new MensajeDTO("Error al crear usuario"));
        }

        UserDetails userDetails;
        try {
            userDetails = userDetailsServiceIml.loadUserByUsername(loginUsuarioDTO.getUsername());
        } catch (UsernameNotFoundException e ) {
            return ResponseEntity.badRequest().body(new MensajeDTO("Error al crear usuario"));
        }

        if(userDetails instanceof UsuarioPrincipal) {
            UsuarioPrincipal usuarioPrincipal = (UsuarioPrincipal) userDetails;
            String email = usuarioPrincipal.getEmail();

            // obtener la IP de donde se inicio session
            String publicIp = getPublicIP().getBody();


            // obtener el dispositivo donde se inicio session
            String device = request.getHeader("User-Agent");

            String to = email;
            String subject = "Inicio de sesion";
            String body = "Hola " + usuarioPrincipal.getNombre() + "!\n" +
                    "Eres tu? comprueba tu nuevo inicio de session:" +
                    "\n" + "IP: " + publicIp +
                    "\n" + "Dispositivo: " + device;

            emailService.sendEmail(to, subject, body);
        }


        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuarioDTO.getUsername(), loginUsuarioDTO.getPassword()));

        String token = jwtProvider.generateToken(authentication);
        JwtDTO jwtDTO = new JwtDTO(token);


        // return new ResponseEntity<>(jwtDTO, HttpStatus.ACCEPTED);
        return ResponseEntity.accepted().body(jwtDTO);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtDTO> refresh(@RequestBody JwtDTO jwtDTO) throws ParseException {
        String token = jwtProvider.refreshToken(jwtDTO);
        JwtDTO jwtDTO1 = new JwtDTO(token);
        return ResponseEntity.accepted().body(jwtDTO1);
    }


    // obtener la ip publica de una persona
    @GetMapping("/ip")
    public ResponseEntity<String> getPublicIP() {
        RestTemplate restTemplate = new RestTemplate();
        String publicIp = restTemplate.getForObject("https://httpbin.org/ip", String.class);

        return ResponseEntity.ok(publicIp);
    }

}
