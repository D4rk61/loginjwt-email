package dev.blackshark.web.controller;

import dev.blackshark.domain.dto.MensajeDTO;
import dev.blackshark.domain.dto.ProductoDTO;
import dev.blackshark.domain.service.ProductoService;
import dev.blackshark.persistance.entity.Producto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/products")    @CrossOrigin(origins = "*")
public class ProductoController {
    private static final String Page_NUMBER = "0";
    private static final String Page_SIZE = "10";

    @Autowired
    private final ProductoService productoService;


    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("")
    public ResponseEntity<Page<Producto>> getAll(
            @RequestParam(defaultValue = Page_NUMBER) int page,
            @RequestParam(defaultValue = Page_SIZE) int size) {
        try {
            return ResponseEntity.ok(productoService.getAll(page, size));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/listById/{id}")
    public ResponseEntity<Optional<Producto>> getById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(productoService.getById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/listByNombre/{nombre}")
    public ResponseEntity<Optional<Producto>> getByNombre(@RequestParam String nombre) {
        try {
            return ResponseEntity.ok(productoService.getByNombre(nombre));
        } catch (Exception e) {
            throw new RuntimeException("El nombre: " + nombre + " intenta nuevamente");
            //return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("")
    public ResponseEntity<MensajeDTO> create (@RequestBody ProductoDTO productoDTO) {
        if(StringUtils.isBlank(productoDTO.getNombre()))
            return new ResponseEntity<MensajeDTO>(new MensajeDTO("El nombre del producto es obligatorio"), HttpStatus.BAD_REQUEST);

        if(productoDTO.getPrecio() < 0)
            return new ResponseEntity<MensajeDTO>(new MensajeDTO("El precio de este prodcuto debe de eser mayor a 00.00"), HttpStatus.BAD_REQUEST);

        if(productoService.existByNombre(productoDTO.getNombre()))
            return new ResponseEntity<MensajeDTO>(new MensajeDTO("Este producto ya existe"), HttpStatus.BAD_REQUEST);


        Producto producto = new Producto(productoDTO.getNombre(), productoDTO.getPrecio());
        productoService.save(producto);

        return new ResponseEntity<MensajeDTO>(new MensajeDTO("Producto creado con exito!"), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MensajeDTO> update (@PathVariable("id") Long id, @RequestBody ProductoDTO productoDTO) {
        if(StringUtils.isBlank(productoDTO.getNombre()))
            return new ResponseEntity<MensajeDTO>(new MensajeDTO("El nombre del producto es obligatorio"), HttpStatus.BAD_REQUEST);

        if(productoDTO.getPrecio() == 0 || productoDTO.getPrecio() < 0)
            return new ResponseEntity<MensajeDTO>(new MensajeDTO("El precio de este prodcuto debe de eser mayor a 00.00"), HttpStatus.BAD_REQUEST);

        if(productoService.existByNombre(productoDTO.getNombre()))
            return new ResponseEntity<MensajeDTO>(new MensajeDTO("Este producto ya existe"), HttpStatus.BAD_REQUEST);


        Producto producto = new Producto(productoDTO.getNombre(), productoDTO.getPrecio());
        productoService.update(id, producto);

        return new ResponseEntity<MensajeDTO>(new MensajeDTO("Producto actualizado con exito!"), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MensajeDTO> delete (@PathVariable("id") Long id) {
        if(!productoService.existById(id))
            return new ResponseEntity<MensajeDTO>(new MensajeDTO("Este producto no existe"), HttpStatus.BAD_REQUEST);

        if(productoService.existByNombre(productoService.getById(id).get().getNombre()))
            return new ResponseEntity<MensajeDTO>(new MensajeDTO("Este producto no existe"), HttpStatus.BAD_REQUEST);

        if(productoService.existByNombre(productoService.getById(id).get().getNombre()))
            return new ResponseEntity<MensajeDTO>(new MensajeDTO("Este producto no existe"), HttpStatus.BAD_REQUEST);

        if(productoService.existByNombre(productoService.getById(id).get().getNombre()))
            return new ResponseEntity<MensajeDTO>(new MensajeDTO("Este producto no existe"), HttpStatus.BAD_REQUEST);


        productoService.delete(id);
        return new ResponseEntity<MensajeDTO>(new MensajeDTO("Producto eliminado con exito!"), HttpStatus.OK);
    }
}
