package org.isaacwallace.librarymanagement.Author.Presentation;

import org.isaacwallace.librarymanagement.Author.Business.AuthorService;
import org.isaacwallace.librarymanagement.Author.DataAccess.Author;
import org.isaacwallace.librarymanagement.Author.Presentation.Models.AuthorRequestModel;
import org.isaacwallace.librarymanagement.Author.Presentation.Models.AuthorResponseModel;
import org.isaacwallace.librarymanagement.Member.Presentation.Models.MemberResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("")
    public ResponseEntity<List<AuthorResponseModel>> getAllAuthors() {
        return ResponseEntity.status(HttpStatus.OK).body(this.authorService.getAllAuthors());
    }

    @GetMapping("{authorid}")
    public ResponseEntity<AuthorResponseModel> getAuthorById(@PathVariable String authorid) {
        return ResponseEntity.status(HttpStatus.OK).body(this.authorService.getAuthorById(authorid));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorResponseModel> addAuthor(@RequestBody AuthorRequestModel authorRequestModel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.authorService.addAuthor(authorRequestModel));
    }

    @PutMapping(value = "{authorid}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorResponseModel> EditAuthor(@PathVariable String authorid, @RequestBody AuthorRequestModel authorRequestModel) {
        return ResponseEntity.status(HttpStatus.OK).body(this.authorService.updateAuthor(authorid, authorRequestModel));
    }

    @DeleteMapping("{authorid}")
    public ResponseEntity<AuthorResponseModel> DeleteAuthor(@PathVariable String authorid) {
        this.authorService.deleteAuthor(authorid);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
