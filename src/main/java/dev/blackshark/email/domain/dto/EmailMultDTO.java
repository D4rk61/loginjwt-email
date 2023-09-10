package dev.blackshark.email.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmailMultDTO {

    private String[] toUser;
    private String subject;
    private String message;
}