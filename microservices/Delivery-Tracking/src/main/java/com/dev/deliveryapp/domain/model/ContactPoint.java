package com.dev.deliveryapp.domain.model;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class ContactPoint {
    private String name;
    private String phone;
    private String zipCode;
    private String street;
    private String number;
    private String complement;
}
