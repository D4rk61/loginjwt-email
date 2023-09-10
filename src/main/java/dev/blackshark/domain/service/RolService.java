package dev.blackshark.domain.service;

import dev.blackshark.persistance.entity.Rol;
import dev.blackshark.persistance.entity.RolNombre;
import dev.blackshark.persistance.repository.crud.IRolCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class RolService {

    @Autowired
    private final IRolCrudRepository rolCrudRepository;


    public RolService(IRolCrudRepository rolCrudRepository) {
        this.rolCrudRepository = rolCrudRepository;
    }


    public Optional<Rol> getByRolNombre(RolNombre rolNombre) {
        return this.rolCrudRepository.findByRolNombre(rolNombre);
    }

    public void save(Rol rol) {
        this.rolCrudRepository.save(rol);
    }
}
