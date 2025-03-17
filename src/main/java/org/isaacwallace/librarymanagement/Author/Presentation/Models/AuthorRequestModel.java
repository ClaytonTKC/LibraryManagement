package org.isaacwallace.librarymanagement.Author.Presentation.Models;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

@Value
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthorRequestModel extends RepresentationModel<AuthorRequestModel> {
    String firstName;
    String lastName;
    String pseudonym;

}
