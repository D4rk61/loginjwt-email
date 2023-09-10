package dev.blackshark.domain.service;

import dev.blackshark.persistance.entity.Producto;
import dev.blackshark.persistance.repository.crud.IProductoCrudRepositoy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Transactional
@Service
public class ProductoService {

    @Autowired
    private final IProductoCrudRepositoy productoPaginRepositoy;
    @Autowired
    private final IProductoCrudRepositoy productoCrudRepositoy;


    public ProductoService(IProductoCrudRepositoy productoPaginRepositoy, IProductoCrudRepositoy productoCrudRepositoy) {
        this.productoPaginRepositoy = productoPaginRepositoy;
        this.productoCrudRepositoy = productoCrudRepositoy;
    }

    public Page<Producto> getAll(int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);

        return this.productoPaginRepositoy.findAll(pageable);
    }

    public Optional<Producto> getById(Long id) {
        try {
            if(!existById(id)) {
                return Optional.empty();
            }

            return this.productoCrudRepositoy.findById(id);
        }catch (Exception e){
            return Optional.empty();
        }
    }

    public Optional<Producto> getByNombre(String nombre) {
        try {
            if(!existByNombre(nombre)) {
                return Optional.empty();
            }

            return this.productoCrudRepositoy.findByNombre(nombre);
        }catch (Exception e){
            return Optional.empty();
        }
    }

    public Producto save(Producto producto) {
        return this.productoCrudRepositoy.save(producto);
    }

    public void delete(Long id) {
        this.productoCrudRepositoy.deleteById(id);
    }

    public Producto update(Long id, Producto producto) {
        if(!existById(id)) {
            throw new RuntimeException("No existe el producto con el id: " + id);
        }

        try {
            producto.setId(id);
            return this.productoCrudRepositoy.save(producto);
        } catch (Exception e) {
            throw e;
        }
    }


    public boolean existById(Long id) {
        return this.productoCrudRepositoy.existsById(id);
    }
    public boolean existByNombre(String nombre) {
        return this.productoCrudRepositoy.existsByNombre(nombre);
    }
}
