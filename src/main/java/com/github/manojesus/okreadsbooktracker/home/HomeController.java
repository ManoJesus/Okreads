package com.github.manojesus.okreadsbooktracker.home;

import com.github.manojesus.okreadsbooktracker.user.BooksByUserId;
import com.github.manojesus.okreadsbooktracker.user.BooksByUserIdRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

import static com.github.manojesus.okreadsbooktracker.constants.Constants.IMG_SOURCE_ROOT;

@AllArgsConstructor
@Controller
public class HomeController {

    private final BooksByUserIdRepository booksByUserIdRepository;

    @GetMapping("/")
    public String home(@AuthenticationPrincipal OAuth2User principal, Model model) {
        if (principal == null || principal.getAttribute("login") == null) {
            Slice<BooksByUserId> sampleBooksSlice = booksByUserIdRepository.findAll(CassandraPageRequest.of(0, 15));
            List<BooksByUserId> sampleBooksList = sampleBooksSlice.getContent().stream().distinct().collect(Collectors.toList());
            model.addAttribute("sampleBooks", sampleBooksList);
            return "index";
        }

        String avatar_url = principal.getAttribute("avatar_url");
        model.addAttribute("userPhoto", avatar_url);
        String login = principal.getAttribute("login");
        Slice<BooksByUserId> slicedDataOfUserMarkedBooks = booksByUserIdRepository.findAllById(login, CassandraPageRequest.of(0, 20));
        List<BooksByUserId> booksByUserIds = slicedDataOfUserMarkedBooks.getContent()
                .stream()
                .distinct()
                .peek(book -> {
                    List<String> coverIds = book.getCoverIds();
                    String coverUrl = "/images/no-image.png";
                    if (coverIds != null && coverIds.size() > 0) {
                        coverUrl = String.format(IMG_SOURCE_ROOT, coverIds.get(0), "M");
                    }
                    switch (book.getReadingStatus()){
                        case "1-READING" :
                            book.setReadingStatus("Currently Reading");
                            break;
                            case "2-FINISHED" :
                            book.setReadingStatus("Already Finished");
                            break;
                            case "3-NOT_FINISHED" :
                            book.setReadingStatus("Haven't finished");
                            break;
                    }
                    book.setCoverUrl(coverUrl);
                }).collect(Collectors.toList());
        model.addAttribute("userBooks", booksByUserIds);
        return "home";
    }
}
