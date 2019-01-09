package com.board.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByIsDeletedFalse(Pageable pageable);

    // pageable 이 있어서 findAll , find 둘 다 working ...
    Page<Post> findByTitleContainingAndIsDeletedFalse(Pageable pageable, String title);

    Page<Post> findByContentContainingAndIsDeletedFalse(Pageable pageable, String content);

    Page<Post> findAllByTitleContainingAndIsDeletedFalseOrContentContainingAndIsDeletedFalse(Pageable pageable, String title, String content);

    /*
    실행 안되는 query...
    org.springframework.dao.InvalidDataAccessResourceUsageException: could not prepare statement; SQL [select * from post where writer_user_id in (select u.user_id from user u where u.name like '%writer%') order by u.id desc limit ?]
     */
    @Query(value = "select * from post where writer_user_id in (select u.user_id from user u where u.name like '%writer%')", nativeQuery = true)
    Page<Post> findAllByWriterName(Pageable pageable, @Param("writer") String writer);

}
