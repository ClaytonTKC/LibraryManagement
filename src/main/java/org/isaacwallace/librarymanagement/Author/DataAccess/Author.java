package org.isaacwallace.librarymanagement.Author.DataAccess;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.isaacwallace.librarymanagement.Member.DataAccess.MemberIdentifier;

import java.util.UUID;

@Entity
@Data
@Table(name = "authors")
@NoArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private AuthorIdentifier authorIdentifier;

    private String firstName;
    private String lastName;
    private String pseudonym;
}
