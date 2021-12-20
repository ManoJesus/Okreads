package com.github.manojesus.okreadsbooktracker.usermarkedbooks;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface UserMarkedBooksRepository extends CassandraRepository<UserMarkedBooks,UserMarkedBooksPrimaryKey> {

}
