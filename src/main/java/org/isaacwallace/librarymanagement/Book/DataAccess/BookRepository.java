package org.isaacwallace.librarymanagement.Book.DataAccess;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    Book findBookByBookIdentifier_Bookid(String bookid);
    List<Book> findBooksByInventoryid(String inventoryid);
}
