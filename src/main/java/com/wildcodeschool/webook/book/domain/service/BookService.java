package com.wildcodeschool.webook.book.domain.service;

import com.wildcodeschool.webook.Auth.domain.entity.User;
import com.wildcodeschool.webook.Auth.domain.service.CookieService;
import com.wildcodeschool.webook.Auth.domain.service.DataValidationService;
import com.wildcodeschool.webook.Auth.domain.service.JwtAuthenticationFilter;
import com.wildcodeschool.webook.Auth.infrastructure.exception.WrongDataFormatException;
import com.wildcodeschool.webook.Auth.infrastructure.repository.UserRepository;
import com.wildcodeschool.webook.book.domain.entity.Book;
import com.wildcodeschool.webook.book.domain.entity.Category;
import com.wildcodeschool.webook.book.infrastructure.repository.BookRepository;
import com.wildcodeschool.webook.book.infrastructure.exception.NotFoundException;
import com.wildcodeschool.webook.fileUpload.domain.dto.MediaDTO;
import com.wildcodeschool.webook.fileUpload.domain.entity.Media;
import com.wildcodeschool.webook.fileUpload.domain.service.MediaMapper;
import com.wildcodeschool.webook.fileUpload.domain.service.UploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Service
public class BookService {
    private final BookRepository repository;
    private final DataValidationService dataValidationService;
    private final UserRepository userRepository;
    private final CategoryService categoryService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CookieService cookieService;
    private final UploadService uploadService;
    private final MediaMapper mediaMapper;

    public BookService(BookRepository repository, DataValidationService dataValidationService, UserRepository userRepository, CategoryService categoryService, JwtAuthenticationFilter jwtAuthenticationFilter, CookieService cookieService, UploadService uploadService, MediaMapper mediaMapper) {
        this.repository = repository;
        this.dataValidationService = dataValidationService;
        this.userRepository = userRepository;
        this.categoryService = categoryService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.cookieService = cookieService;
        this.uploadService = uploadService;
        this.mediaMapper = mediaMapper;
    }

    public List<Book> getAllBooks() {
        return repository.findAll();
    }

    public Book getOneBook(Long id) {
        return repository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    public List<Book> getBooksByOwner(Long ownerId) {
        User user = userRepository.findById(ownerId).orElseThrow(NotFoundException::new);
        return repository.findBooksByOwner(user);
    }

    public List<Book> getBooksByCategory(Long categoryId) {
        Category category = categoryService.getOneCategory(categoryId);
        return repository.findBooksByBookCategory(category);
    }

    public List<Book> getBooksByTitleOrAuthorName(String keyword) {
        return repository.findBooksByTitleIsContainingIgnoreCaseOrAuthorIsContainingIgnoreCase(keyword, keyword);
    }

    public Book createBook(Book newBook, MultipartFile file) {
        if (dataValidationService.BookDataValidation(newBook)) {
            newBook.setOwner(cookieService.getUserByCookie());

            Media media = mediaMapper.transformMediaDTOIntoEntity(uploadService.store(file));
            newBook.setCoverImage(media);
            return repository.save(newBook);
        } else throw new WrongDataFormatException("Book title, author or ISBN");
    }


    public Book updateBook(Book newBook, Long id) {
        if (dataValidationService.BookDataValidation(newBook)) {
            return repository.findById(id)
                    .map(book -> {
                        book.setTitle(newBook.getTitle());
                        book.setCoverImage(newBook.getCoverImage());
                        book.setAuthor(newBook.getAuthor());
                        book.setEdition(newBook.getEdition());
                        book.setResume(newBook.getResume());
                        book.setIsbn(newBook.getIsbn());
                        book.setReview(newBook.getReview());
                        book.setBookCategory(newBook.getBookCategory());

                        return repository.save(book);
                    })
                    .orElseThrow(NotFoundException::new);
        } else throw new WrongDataFormatException("Book title, author or ISBN");
    }

    public void deleteBook(Long id) {
        repository.deleteById(id);
    }
}
