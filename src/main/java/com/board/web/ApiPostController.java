package com.board.web;

import com.board.domain.Post;
import com.board.domain.User;
import com.board.dto.PostDTO;
import com.board.dto.ReplyDTO;
import com.board.dto.UpdatePostDTO;
import com.board.security.LoginUser;
import com.board.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utils.RestResponse;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/posts")
@Slf4j
public class ApiPostController {

    @Resource(name = "postService")
    private PostService postService;

    @PostMapping
    public ResponseEntity<RestResponse> create(@LoginUser User loginedUser, @Valid PostDTO postDTO) {
        Post post = postService.create(loginedUser, postDTO);
        return ResponseEntity.created(URI.create("/post/" + post.getId())).build();

    }

    @GetMapping("/{postId}")
    public ResponseEntity<RestResponse> getPost(@PathVariable Long postId) {
        return ResponseEntity.ok(RestResponse.success(postService.getPost(postId)));
    }

    @GetMapping
    public ResponseEntity<RestResponse> getPostsByPage(@PageableDefault(page = 0, size = 5) Pageable pageable) {
        return ResponseEntity.ok(RestResponse.success(postService.getPostsByPage(pageable)));
    }

    @GetMapping("/search")
    public ResponseEntity<RestResponse> getSearchedPostsByPage(@PageableDefault(page = 0, size = 5) Pageable pageable, @RequestParam(name = "searchType") String searchType, @RequestParam(name = "keyword") String keyword) {
        return ResponseEntity.ok(RestResponse.success(postService.getSearchedPostsByPage(pageable, searchType, keyword)));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<RestResponse> updatePost(@LoginUser User loginedUser, @PathVariable Long postId, @Valid UpdatePostDTO updatePostDTO) {
        return ResponseEntity.ok(RestResponse.success(postService.updatePost(loginedUser, postId, updatePostDTO)));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@LoginUser User loginedUser, @PathVariable Long postId) {
        postService.deletePost(loginedUser, postId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{postId}/reply")
    public ResponseEntity<RestResponse> createReply(@LoginUser User loginedUser, @PathVariable Long postId, @RequestBody ReplyDTO replyDTO) {
        return ResponseEntity.ok(RestResponse.success(postService.createReply(loginedUser, postId, replyDTO.getComment())));
    }

    @GetMapping("/{id}/reply")
    public ResponseEntity<RestResponse> getReplyByPost(@PathVariable Long id) {
        return ResponseEntity.ok(RestResponse.success(postService.getReplyByPost(id)));
    }
}
