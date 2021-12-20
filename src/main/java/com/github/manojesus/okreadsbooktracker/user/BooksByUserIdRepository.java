package com.github.manojesus.okreadsbooktracker.user;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface BooksByUserIdRepository extends CassandraRepository<BooksByUserId, String> {
    Slice<BooksByUserId> findAllById(String userId, Pageable pageable);
}
