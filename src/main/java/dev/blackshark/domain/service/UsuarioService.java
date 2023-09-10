package dev.blackshark.domain.service;

import dev.blackshark.persistance.entity.Usuario;
import dev.blackshark.persistance.repository.crud.IUsuarioCrudRepository;
import dev.blackshark.security.persistance.entity.UsuarioPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class UsuarioService {

    @Autowired
    private final IUsuarioCrudRepository usuarioCrudRepository;

    public UsuarioService(IUsuarioCrudRepository usuarioCrudRepository) {
        this.usuarioCrudRepository = usuarioCrudRepository;
    }

    public Page<Usuario> getAll(int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);

        return this.usuarioCrudRepository.findAll(pageable);
    }

    public Optional<Usuario> getByUsername(String username) {
        if(!existsByUsername(username)) {
            return Optional.empty();

        }
        return this.usuarioCrudRepository.findByUsername(username);
    }

    public Usuario save(Usuario usuario) {
        try {
            if(existsByUsername(usuario.getUsername())) {
                return null;
            }
            if(existsByEmail(usuario.getEmail())) {
                return null;
            }

            return this.usuarioCrudRepository.save(usuario);

        } catch (Exception e ) {
            return null;
        }
    }


    public boolean existsByUsername(String username) {
        return this.usuarioCrudRepository.existsByUsername(username);
    }
    public boolean existsByEmail(String email) {
        return this.usuarioCrudRepository.existsByEmail(email);
    }

    /*
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOptional = this.usuarioCrudRepository.findByUsername(username);

        if (!usuarioOptional.isPresent()) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        Usuario usuario = usuarioOptional.get();

        return new UsuarioPrincipal(
                usuario.getNombre(),
                usuario.getUsername(),
                usuario.getEmail(),
                usuario.getPassword(),
                usuario.getRoles() // Puedes incluir roles aquí si los necesitas
        );
    }

     */

}
