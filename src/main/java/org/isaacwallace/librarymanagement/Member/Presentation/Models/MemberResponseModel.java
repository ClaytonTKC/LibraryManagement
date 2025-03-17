package org.isaacwallace.librarymanagement.Member.Presentation.Models;

import lombok.Data;
import org.isaacwallace.librarymanagement.Member.DataAccess.Address;
import org.isaacwallace.librarymanagement.Member.DataAccess.Phone;
import org.springframework.hateoas.RepresentationModel;

@Data
public class MemberResponseModel extends RepresentationModel<MemberResponseModel> {
    private String memberid;

    private String first_name;
    private String last_name;
    private String email;

    private Address address;
    private Phone phone;
}
