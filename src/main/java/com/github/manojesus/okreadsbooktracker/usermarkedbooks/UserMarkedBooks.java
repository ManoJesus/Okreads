package com.github.manojesus.okreadsbooktracker.usermarkedbooks;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDate;

import static org.springframework.data.cassandra.core.mapping.CassandraType.Name.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("marked_books_user_and_book_ids")
public class UserMarkedBooks {
    @PrimaryKey
    private UserMarkedBooksPrimaryKey key;
    @Column("reading_status")
    @CassandraType(type = TEXT)
    private String readingStatus;
    @Column("start_date")
    @CassandraType(type = DATE)
    private LocalDate startDate;
    @Column("finished_date")
    @CassandraType(type = DATE)
    private LocalDate finishedDate;
    @Column("rating")
    @CassandraType(type = INT)
    private int rating;
}
