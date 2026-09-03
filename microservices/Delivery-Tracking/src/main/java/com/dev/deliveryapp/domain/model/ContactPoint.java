package com.dev.deliveryapp.domain.model;

import lombok.*;

@EqualsAndHashCode
@AllArgsConstructor
@Builder
@Getter
public class ContactPoint {
    private String nome;
    private String phone;
    private String zipCode;
    private String street;
    private String number;
    private String complement;
}
