package org.isaacwallace.librarymanagement.Author.Business;

import org.isaacwallace.librarymanagement.Author.DataAccess.Author;
import org.isaacwallace.librarymanagement.Author.DataAccess.AuthorIdentifier;
import org.isaacwallace.librarymanagement.Author.DataAccess.AuthorRepository;
import org.isaacwallace.librarymanagement.Author.Mapper.AuthorRequestMapper;
import org.isaacwallace.librarymanagement.Author.Mapper.AuthorResponseMapper;
import org.isaacwallace.librarymanagement.Author.Presentation.Models.AuthorRequestModel;
import org.isaacwallace.librarymanagement.Author.Presentation.Models.AuthorResponseModel;
import org.isaacwallace.librarymanagement.Member.DataAccess.Member;
import org.isaacwallace.librarymanagement.Member.Presentation.Models.MemberResponseModel;
import org.isaacwallace.librarymanagement.Utils.Exceptions.InUseException;
import org.isaacwallace.librarymanagement.Utils.Exceptions.InvalidInputException;
import org.isaacwallace.librarymanagement.Utils.Exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorResponseMapper authorResponseMapper;
    private final AuthorRequestMapper authorRequestMapper;

    private static final Logger logger = LoggerFactory.getLogger(AuthorServiceImpl.class);

    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorResponseMapper authorResponseMapper, AuthorRequestMapper authorRequestMapper) {
        this.authorRepository = authorRepository;
        this.authorResponseMapper = authorResponseMapper;
        this.authorRequestMapper = authorRequestMapper;
    }

    public List<AuthorResponseModel> getAllAuthors() {
        return this.authorResponseMapper.entityToResponseModelList(authorRepository.findAll());
    }

    public AuthorResponseModel getAuthorById(String authorid) {
        Author author = this.authorRepository.findAuthorByAuthorIdentifier_Authorid(authorid);

        if (author == null) {
            throw new NotFoundException("Unknow memberid: " + authorid);
        }

        return this.authorResponseMapper.entityToResponseModel(author);
    }

    public AuthorResponseModel addAuthor(AuthorRequestModel authorRequestModel) {
        Author author = this.authorRequestMapper.requestModelToEntity(authorRequestModel, new AuthorIdentifier());

        return authorResponseMapper.entityToResponseModel(this.authorRepository.save(author));
    }

    public AuthorResponseModel updateAuthor(String authorid, AuthorRequestModel authorRequestModel) {
        Author author = this.authorRepository.findAuthorByAuthorIdentifier_Authorid(authorid);

        if (author == null) {
            throw new NotFoundException("Unknow authorid: " + authorid);
        }

        this.authorRequestMapper.updateEntityFromRequest(authorRequestModel, author);

        Author updatedAuthor = this.authorRepository.save(author);

        logger.info("Updated author: " + authorid);

        return this.authorResponseMapper.entityToResponseModel(updatedAuthor);
    }

    public void deleteAuthor(String authorid) {
        Author author = this.authorRepository.findAuthorByAuthorIdentifier_Authorid(authorid);

        if (author == null) {
            throw new NotFoundException("Unknow authorid: " + authorid);
        }

        try {
            this.authorRepository.delete(author);
            logger.info("Author with id: " + authorid + " deleted.");
        } catch (DataIntegrityViolationException e) {
            logger.error("Failed to delete author: " + authorid);
            throw new InUseException("Author with id: " + authorid + " is already in use by another entity.");
        }
    }
}
