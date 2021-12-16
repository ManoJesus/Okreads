package com.github.manojesus.okreadsbooktracker.search;

import lombok.Data;

import java.util.List;

@Data
public class Search {
    private int numFound;
    private List<SearchResultBook> docs;
}
