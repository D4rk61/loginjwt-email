package dev.blackshark.security.domain.service;

import dev.blackshark.domain.service.UsuarioService;
import dev.blackshark.persistance.entity.Usuario;
import dev.blackshark.security.persistance.entity.UsuarioPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceIml implements UserDetailsService {

    @Autowired
    private final UsuarioService usuarioService;

    public UserDetailsServiceIml(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.getByUsername(username).get();
        return UsuarioPrincipal.build(usuario);
    }
}
