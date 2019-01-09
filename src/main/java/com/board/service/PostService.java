package com.board.service;

import com.board.domain.*;
import com.board.dto.PostDTO;
import com.board.dto.UpdatePostDTO;
import com.board.support.domain.FileStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("postService")
@Slf4j
public class PostService {

    @Autowired
    private FileStorage fileStorage;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileInfoRepository fileInfoRepository;

    @Autowired
    private ReplyRepository replyRepository;

    public Post create(User loginedUser, PostDTO postDTO) {
        User user = userRepository.findById(loginedUser.getId()).orElseThrow(EntityNotFoundException::new);

        List<FileInfo> fileInfos = new ArrayList<>();

        Optional.ofNullable(postDTO.getFiles())
                .ifPresent(multipartFiles -> multipartFiles.forEach(multipartfile -> {
                    fileInfos.add(fileInfoRepository.save(fileStorage.upload(multipartfile)));
                }));

        Post post = postDTO.toEntity(user, fileInfos);

        return postRepository.save(post);
    }

    public Post getPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(EntityNotFoundException::new);
    }

    public Page<Post> getPostsByPage(Pageable pageable) {
        return postRepository.findAllByIsDeletedFalse(pageable);
    }

    public Page<Post> getSearchedPostsByPage(Pageable pageable, String searchType, String keyword) {

        if (searchType.equals("title")) {
            return postRepository.findByTitleContainingAndIsDeletedFalse(pageable, keyword);
        } else if (searchType.equals("content")) {
            return postRepository.findByContentContainingAndIsDeletedFalse(pageable, keyword);
        } else if (searchType.equals("titleOrcontent")) {
            return postRepository.findAllByTitleContainingAndIsDeletedFalseOrContentContainingAndIsDeletedFalse(pageable, keyword, keyword);
        }

        // 작성자를 통해서 검색하는 query문이 실행이 안됨, 에러 로그는 in PostRepository
//        else if (searchType.equals("writer")) {
//            return postRepository.findAllByWriterName(pageable, keyword);
//        }

        // null 대신 다른 값을 반환하는 것이 나을 듯
        return null;
    }

    public Post updatePost(User loginedUser, Long id, UpdatePostDTO updatePostDTO) {
        Post post = postRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        User user = userRepository.findById(loginedUser.getId()).orElseThrow(EntityNotFoundException::new);

        if (user.equals(post.getWriter())) {
            post.change(updatePostDTO);
        }

        Post updatedPost = postRepository.save(post);
        log.info("updatedPost : " + updatedPost);
        return updatedPost;
    }

    public void deletePost(User loginedUser, Long id) {
        Post post = postRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        User user = userRepository.findById(loginedUser.getId()).orElseThrow(EntityNotFoundException::new);

        if (user.equals(post.getWriter())) {
            post.changeToDeleted();
            postRepository.save(post);
        }
    }

    public Reply createReply(User loginedUser, Long postId, String comment) {
        Post post = postRepository.findById(postId).orElseThrow(EntityNotFoundException::new);
        User user = userRepository.findById(loginedUser.getId()).orElseThrow(EntityNotFoundException::new);

        return replyRepository.save(
                Reply.builder()
                        .writer(user)
                        .post(post)
                        .comment(comment)
                        .build()
        );
    }

    public List<Reply> getReplyByPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(EntityNotFoundException::new);
        return replyRepository.findAllByPostAndIsDeletedFalse(post);
    }
}
