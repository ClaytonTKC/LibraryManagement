package org.isaacwallace.librarymanagement.Author.DataAccess;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    Author findAuthorByAuthorIdentifier_Authorid(String authorid);

}
