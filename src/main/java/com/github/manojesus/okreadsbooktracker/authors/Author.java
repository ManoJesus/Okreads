package com.github.manojesus.okreadsbooktracker.authors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.PARTITIONED;
import static org.springframework.data.cassandra.core.mapping.CassandraType.Name.TEXT;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "Author_By_ID")
public class Author {
    @Id
    @PrimaryKeyColumn(name = "author_Id",ordinal = 0, type = PARTITIONED)
    private String id;
    @Column("author_name")
    @CassandraType(type = TEXT)
    private String name;
    @Column("author_personal_name")
    @CassandraType(type = TEXT)
    private String personalName;
}
