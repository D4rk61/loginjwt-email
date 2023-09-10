package dev.blackshark.persistance.repository.crud;

import dev.blackshark.persistance.entity.Rol;
import dev.blackshark.persistance.entity.RolNombre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRolCrudRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByRolNombre(RolNombre rolNombre);
}
