package com.github.manojesus.okreadsbooktracker.books;

import com.github.manojesus.okreadsbooktracker.usermarkedbooks.UserMarkedBooks;
import com.github.manojesus.okreadsbooktracker.usermarkedbooks.UserMarkedBooksPrimaryKey;
import com.github.manojesus.okreadsbooktracker.usermarkedbooks.UserMarkedBooksRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

import static com.github.manojesus.okreadsbooktracker.constants.Constants.IMG_SOURCE_ROOT;

@AllArgsConstructor
@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final UserMarkedBooksRepository userMarkedBooksRepository;

    @GetMapping("/{bookId}")
    public String getBook(@PathVariable String bookId, Model model, @AuthenticationPrincipal OAuth2User principal){
        Optional<Book> bookOptional = bookService.getBookById(bookId);
        if(bookOptional.isPresent()){
            Book book = bookOptional.get();
            String bookCover = "/images/no-image.png";
            if(book.getBookCoverIDs() != null && book.getBookCoverIDs().size() > 0){
                bookCover = String.format(IMG_SOURCE_ROOT,book.getBookCoverIDs().get(0),"L");
            }
            model.addAttribute("bookCover",bookCover);
            System.out.println(book.getBookId() + " "+ book.getTitle());
            model.addAttribute("book", book);
            if(principal != null && principal.getAttribute("login") != null){
                model.addAttribute("userId", principal.getAttribute("login"));
                UserMarkedBooksPrimaryKey key = new UserMarkedBooksPrimaryKey(
                        principal.getAttribute("login"),
                        bookId
                );
                Optional<UserMarkedBooks> userMarkedBook = userMarkedBooksRepository.findById(key);
                if(userMarkedBook.isPresent()){
                    model.addAttribute("userMarkedBook", userMarkedBook.get());
                }else
                    model.addAttribute("userMarkedBook", new UserMarkedBooks());
            }
            return "book";
        }else {
            return "book-not-found";
        }
    }
}
