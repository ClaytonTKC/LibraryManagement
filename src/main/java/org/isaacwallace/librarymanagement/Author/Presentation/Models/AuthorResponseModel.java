package org.isaacwallace.librarymanagement.Author.Presentation.Models;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

@Data
public class AuthorResponseModel extends RepresentationModel<AuthorResponseModel> {
    private String authorId;
    private String firstName;
    private String lastName;
    private String pseudonym;
}
