package dev.blackshark.email.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor  @AllArgsConstructor
public class EmailOneDTO {

    private String toUser;
    private String subject;
    private String message;
}
