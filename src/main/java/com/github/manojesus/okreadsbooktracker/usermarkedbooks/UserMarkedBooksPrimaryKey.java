package com.github.manojesus.okreadsbooktracker.usermarkedbooks;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.PARTITIONED;

@Data
@AllArgsConstructor
@NoArgsConstructor
@PrimaryKeyClass
public class UserMarkedBooksPrimaryKey {
    @PrimaryKeyColumn(name = "user_id", type = PARTITIONED, ordinal = 0)
    private String userId;
    @PrimaryKeyColumn(name = "book_id", type = PARTITIONED, ordinal = 1)
    private String bookId;
}
