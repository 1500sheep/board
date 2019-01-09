package com.board.dto;

import com.board.domain.FileInfo;
import com.board.domain.Post;
import com.board.domain.User;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostDTO {

    @NotNull
    @Length(min = 1, max = 100)
    private String title;

    @NotEmpty
    @Length(max = 100000)
    @Lob
    private String content;

    @Size(max = 5, min = 1)
    private List<MultipartFile> files;

    public Post toEntity(User user, List<FileInfo> fileInfos) {

        Post toEntitiyPost = Post.builder()
                .writer(user)
                .title(title)
                .content(content)
                .build();

        if (!fileInfos.isEmpty()) {
            toEntitiyPost.setFileInfos(fileInfos);

        }

        return toEntitiyPost;
    }

}
