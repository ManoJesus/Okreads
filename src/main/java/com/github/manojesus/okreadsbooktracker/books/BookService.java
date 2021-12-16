package com.github.manojesus.okreadsbooktracker.books;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;

    public void save(Book book){
        bookRepository.save(book);
    }

    public Optional<Book> getBookById(String bookId){
        return bookRepository.findById(bookId);
    }
}
