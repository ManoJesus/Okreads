package com.github.manojesus.okreadsbooktracker.books;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

import static com.github.manojesus.okreadsbooktracker.constants.Constats.IMG_SOURCE_ROOT;

@AllArgsConstructor
@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @GetMapping("/{bookId}")
    public String getBook(@PathVariable String bookId, Model model){
        Optional<Book> bookOptional = bookService.getBookById(bookId);
        if(bookOptional.isPresent()){
            Book book = bookOptional.get();
            String bookCover = "images/no-image.png";
            if(book.getBookCoverIDs() != null && book.getBookCoverIDs().size() > 0){
                bookCover = String.format(IMG_SOURCE_ROOT,book.getBookCoverIDs().get(0),"L");
            }
            model.addAttribute("bookCover",bookCover);
            System.out.println(book.getBookId() + " "+ book.getTitle());
            model.addAttribute("book", book);
            return "book";
        }else {
            return "book-not-found";
        }
    }
}
