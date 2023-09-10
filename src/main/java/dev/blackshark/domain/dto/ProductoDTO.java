package dev.blackshark.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter @Setter
@NoArgsConstructor
public class ProductoDTO {

    @NotBlank
    private String nombre;

    @Min(0)
    private float precio;

    public ProductoDTO(@NotBlank String nombre, @Min(0) float precio) {
        this.nombre = nombre;
        this.precio = precio;
    }
}
