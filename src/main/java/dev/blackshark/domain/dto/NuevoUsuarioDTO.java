package dev.blackshark.domain.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Setter @Getter
public class NuevoUsuarioDTO {

    @NotBlank
    private String nombre;
    @NotBlank
    private String username;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    private Set<String> roles = new HashSet<>();
}
