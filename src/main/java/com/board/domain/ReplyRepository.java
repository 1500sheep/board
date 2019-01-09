package com.board.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    Page<Reply> findAllByIsDeletedFalse(Pageable pageable);

    List<Reply> findAllByPostAndIsDeletedFalse(Post post);

}
