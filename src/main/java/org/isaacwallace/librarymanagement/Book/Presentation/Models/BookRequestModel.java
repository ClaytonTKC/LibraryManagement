package org.isaacwallace.librarymanagement.Book.Presentation.Models;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Value
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BookRequestModel extends RepresentationModel<BookRequestModel> {
    String authorid;
    String inventoryid;

    String title;
    String genre;
    String publisher;

    LocalDateTime released;
}
