package org.isaacwallace.librarymanagement.Member.Presentation.Models;

import lombok.*;
import org.isaacwallace.librarymanagement.Member.DataAccess.Address;
import org.isaacwallace.librarymanagement.Member.DataAccess.Phone;
import org.springframework.hateoas.RepresentationModel;

@Value
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberRequestModel extends RepresentationModel<MemberRequestModel> {
    String firstName;
    String lastName;

    String email;

    Address address;
    Phone phone;
}
