package com.wildcodeschool.webook.Auth.domain.service;

import com.wildcodeschool.webook.Auth.domain.service.interfaces.IDataValidationService;
import com.wildcodeschool.webook.book.domain.entity.Book;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;
import java.util.zip.DataFormatException;

@Service
public class DataValidationService implements IDataValidationService {
    @Override
    public boolean EmailValidation(String email) {
        return Pattern.compile("^(?=.{1,35}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
                .matcher(email)
                .matches();
    }

    @Override
    public boolean PasswordValidation(String password) {
        return Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&€+=*!-])(?=\\S+$).{8,20}$", Pattern.CASE_INSENSITIVE)
                .matcher(password)
                .matches();
    }

    @Override
    public boolean UsernameValidation(String username) {
        return Pattern.compile("^[a-zA-Z_.]{2,20}$", Pattern.CASE_INSENSITIVE)
                .matcher(username)
                .matches();
    }

    @Override
    public boolean ZipCodeValidation(String zipCode) {
        return Pattern.compile("^\\d{5}$", Pattern.CASE_INSENSITIVE)
                .matcher(zipCode)
                .matches();
    }

    @Override
    public boolean BookDataValidation(Book book) {
        Boolean isNameValid = Pattern.compile("^(?!\\s*$)[a-zA-Z\\s]{1,50}$", Pattern.CASE_INSENSITIVE)
                .matcher(book.getName())
                .matches();

        Boolean isAuthorValid = Pattern.compile("^(?!\\s*$)[a-zA-Z\\s]{1,35}$", Pattern.CASE_INSENSITIVE)
                .matcher(book.getAuthor())
                .matches();

        if (book.getIsbn() != null) {
            Boolean isIsbnValid = Pattern.compile("^([0-9Xx][- ]*){13}|([0-9Xx][- ]*){10}$", Pattern.CASE_INSENSITIVE)
                    .matcher(book.getIsbn())
                    .matches();
            return isNameValid && isAuthorValid && isIsbnValid;
        }
        return isNameValid && isAuthorValid;
    }
}
