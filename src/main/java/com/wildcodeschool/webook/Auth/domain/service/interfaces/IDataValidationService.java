package com.wildcodeschool.webook.Auth.domain.service.interfaces;

import com.wildcodeschool.webook.book.domain.entity.Book;

public interface IDataValidationService {
    public boolean EmailValidation(String email);
    public boolean PasswordValidation(String password);
    public boolean UsernameValidation(String username);
    public boolean ZipCodeValidation(String zipCode);
    public boolean BookDataValidation(Book book);
}
