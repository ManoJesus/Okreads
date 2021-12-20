package com.github.manojesus.okreadsbooktracker.search;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

import static com.github.manojesus.okreadsbooktracker.constants.Constants.IMG_SOURCE_ROOT;


@Controller
public class SearchController {

    private final WebClient webClient;

    public SearchController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer
                                        .defaultCodecs()
                                        .maxInMemorySize(16 * 1024 * 1024)
                                )
                        .build())
                .baseUrl("http://openlibrary.org/search.json")
                .build();
    }

    @GetMapping("/search")
    public String getResultsOfSearch(@RequestParam String query, Model model){
        Search searchResult = webClient
                .get()
                .uri("?q={query}", query)
                .retrieve()
                .bodyToMono(Search.class)
                .block();

        List<SearchResultBook> resultsBooks = searchResult
                                              .getDocs()
                                              .stream()
                                              .limit(10)
                                              .peek(book -> {
                                                book.setKey(book.getKey().replace("/works/",""));
                                                String coverId = book.getCover_i();
                                                if(StringUtils.hasText(coverId)){
                                                    book.setCover_i(String.format(IMG_SOURCE_ROOT, coverId,"M"));
                                                }else {
                                                    book.setCover_i("images/no-image.png");
                                                }
                                              })
                                              .collect(Collectors.toList());
        model.addAttribute("resultsBooks", resultsBooks);
        return "search";
    }
}
