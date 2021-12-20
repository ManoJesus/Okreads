package com.github.manojesus.okreadsbooktracker.usermarkedbooks;

import com.github.manojesus.okreadsbooktracker.books.Book;
import com.github.manojesus.okreadsbooktracker.books.BookRepository;
import com.github.manojesus.okreadsbooktracker.user.BooksByUserId;
import com.github.manojesus.okreadsbooktracker.user.BooksByUserIdRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.Optional;


@AllArgsConstructor
@Controller
public class UserMarkedBooksController {

    private final UserMarkedBooksRepository userMarkedBooksRepository;
    private final BookRepository bookRepository;
    private final BooksByUserIdRepository booksByUserIdRepository;

    @PostMapping("/postbookforuser")
    public ModelAndView addMarkedBookForUser(@RequestBody MultiValueMap<String, String> formValues, @AuthenticationPrincipal OAuth2User principal){
        if(principal != null && principal.getAttribute("login") != null) {
            final String userId = principal.getAttribute("login");
            final String bookId = formValues.getFirst("bookId");
            final int rating = Integer.parseInt(formValues.getFirst("rating"));
            final String readingStatus = formValues.getFirst("readingStatus");

            assert bookId != null;
            Optional<Book> optBook = bookRepository.findById(bookId);
            UserMarkedBooksPrimaryKey userMarkedBooksPrimaryKey = new UserMarkedBooksPrimaryKey();
            userMarkedBooksPrimaryKey.setUserId(userId);
            userMarkedBooksPrimaryKey.setBookId(bookId);

            UserMarkedBooks userMarkedBook = new UserMarkedBooks(
                    userMarkedBooksPrimaryKey,
                    readingStatus,
                    LocalDate.parse(formValues.getFirst("startDate")),
                    LocalDate.parse(formValues.getFirst("finishedDate")),
                    rating
            );
            userMarkedBooksRepository.save(userMarkedBook);
            if(optBook.isEmpty()){
                return new ModelAndView("redirect:/books/"+bookId);
            }
            Book book = optBook.get();

            BooksByUserId booksByUserId = new BooksByUserId();
            booksByUserId.setId(userId);
            booksByUserId.setBookId(bookId);
            booksByUserId.setBookTitle(book.getTitle());
            booksByUserId.setRating(rating);
            booksByUserId.setAuthorNames(book.getAuthorNames());
            booksByUserId.setCoverIds(book.getBookCoverIDs());
            booksByUserId.setReadingStatus(readingStatus);
            booksByUserIdRepository.save(booksByUserId);

            return new ModelAndView("redirect:/books/"+bookId);
        }
        return null;
    }
}
