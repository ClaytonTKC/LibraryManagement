package org.isaacwallace.librarymanagement.Member.DataAccess;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Embeddable
public class Address {
    private String street;
    private String city;
    private String postal;
    private String province;
}
