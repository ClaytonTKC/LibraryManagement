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
    String first_name;
    String last_name;

    String email;

    Address address;
    Phone phone;
}
