package com.dev.deliveryapp.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class ContatctPoint {
    private String nome;
    private String phone;
    private String zipCode;
    private String street;
    private String number;
    private String complement;
}
