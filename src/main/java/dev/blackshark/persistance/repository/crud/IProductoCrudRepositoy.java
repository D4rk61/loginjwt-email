package dev.blackshark.persistance.repository.crud;

import dev.blackshark.persistance.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IProductoCrudRepositoy extends JpaRepository<Producto, Long> {

    Optional<Producto> findByNombre(String nombre);

    boolean existsByNombre(String nombre);
}
