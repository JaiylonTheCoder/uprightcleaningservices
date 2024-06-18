package com.jaiylonbabb.uprightcleaningservices.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContactForm {
    private String name;
    private String email;
    private String message;
}
