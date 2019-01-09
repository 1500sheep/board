package com.board.dto;

import com.board.domain.Post;
import com.board.domain.User;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdatePostDTO {

    @NotNull
    @Length(min = 1, max = 100)
    private String title;

    @NotEmpty
    @Length(max = 100000)
    @Lob
    private String content;

    public Post toEntity(User user) {
        return Post.builder()
                .writer(user)
                .title(title)
                .content(content)
                .build();
    }

}
