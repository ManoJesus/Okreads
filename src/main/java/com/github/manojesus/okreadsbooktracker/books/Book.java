package com.github.manojesus.okreadsbooktracker.books;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.PARTITIONED;
import static org.springframework.data.cassandra.core.mapping.CassandraType.Name.*;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Table("book_by_id")
public class Book {
    @Id
    @PrimaryKeyColumn(name = "book_id", ordinal = 0, type = PARTITIONED)
    private String bookId;

    @Column("book_title")
    @CassandraType(type = TEXT)
    private String title;

    @Column("book_description")
    @CassandraType(type = TEXT)
    private String description;

    @Column("published_date")
    @CassandraType(type = DATE)
    private LocalDate publishedDate;

    @Column("book_cover_ids")
    @CassandraType(type = LIST, typeArguments = TEXT)
    private List<String> bookCoverIDs;

    @Column("book_author_ids")
    @CassandraType(type = LIST, typeArguments = TEXT)
    private List<String> authorIds;

    @Column("book_author_names")
    @CassandraType(type = LIST, typeArguments = TEXT)
    private List<String> authorNames;
}


