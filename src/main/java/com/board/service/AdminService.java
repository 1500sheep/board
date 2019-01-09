package com.board.service;

import com.board.domain.*;
import com.board.exception.NotAllowedException;
import com.board.exception.UnAuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service("adminService")
@Slf4j
public class AdminService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReplyRepository replyRepository;

    public Page<?> getDataByPage(User adminUser, Pageable pageable, String selectedType) {
        checkUserIsAdmin(adminUser);

        if (selectedType.equals("user")) {
            return getUsersByPage(pageable);
        } else if (selectedType.equals("post")) {
            return getPostsByPage(pageable);
        } else if (selectedType.equals("reply")) {
            return getReplysByPage(pageable);
        }

        throw new EntityNotFoundException("탐색 종류가 없습니다.");
    }

    public Page<User> getUsersByPage(Pageable pageable) {
        return userRepository.findAllByIsDeletedFalse(pageable);
    }

    public Page<Post> getPostsByPage(Pageable pageable) {
        return postRepository.findAllByIsDeletedFalse(pageable);
    }

    public Page<Reply> getReplysByPage(Pageable pageable) {
        return replyRepository.findAllByIsDeletedFalse(pageable);
    }

    public void deleteData(User adminUser, Long dataId, String selectedType) {
        checkUserIsAdmin(adminUser);

        if (selectedType.equals("user")) {

            User user = userRepository.findById(dataId).orElseThrow(EntityNotFoundException::new);
            user.changeToDeleted();
            userRepository.save(user);

            return;

        } else if (selectedType.equals("post")) {

            Post post = postRepository.findById(dataId).orElseThrow(EntityNotFoundException::new);
            post.changeToDeleted();
            postRepository.save(post);

            return;

        } else if (selectedType.equals("reply")) {

            Reply reply = replyRepository.findById(dataId).orElseThrow(EntityNotFoundException::new);
            reply.changeToDeleted();
            replyRepository.save(reply);

            return;

        }

        throw new EntityNotFoundException("탐색 종류가 없습니다.");
    }

    private void checkUserIsAdmin(User adminUser) {
        User user = userRepository.findById(adminUser.getId()).orElseThrow(EntityNotFoundException::new);
        if (!user.isAdmin()) {
            throw new NotAllowedException("관리자가 아닙니다.");
        }
    }
}
