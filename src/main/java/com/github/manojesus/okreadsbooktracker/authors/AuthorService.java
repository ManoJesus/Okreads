package com.github.manojesus.okreadsbooktracker.authors;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public void save(Author author){
        authorRepository.save(author);
    }

    public String getAuthorNameById(String id){
        Optional<Author> authorOpt = authorRepository.findById(id);
        if(authorOpt.isEmpty()){
            return "Unknown author";
        }
        return authorOpt.get().getName();
    }
}
