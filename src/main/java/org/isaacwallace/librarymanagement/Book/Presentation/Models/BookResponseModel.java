package org.isaacwallace.librarymanagement.Book.Presentation.Models;

import lombok.Data;
import org.isaacwallace.librarymanagement.Book.DataAccess.BookStatus;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Data
public class BookResponseModel extends RepresentationModel<BookResponseModel> {
    private String bookid;
    private String authorid;
    private String inventoryid;

    private String title;
    private String genre;
    private String publisher;

    private LocalDateTime released;

    private BookStatus availability;
}
