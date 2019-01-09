package com.board.domain;

import com.board.dto.UpdatePostDTO;
import com.board.support.domain.BaseTimeEntity;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private User writer;

    @NotNull
    @Length(min = 1, max = 100)
    @Column(nullable = false, length = 100)
    private String title;

    @NotEmpty
    @Length(max = 100000)
    @Lob
    @Column(nullable = false, length = 100000)
    private String content;

    @Nullable
    @Size(max = 5)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn
    @Setter
    private List<FileInfo> fileInfos;

    @Column
    private boolean isDeleted = false;

    public void changeToDeleted() {
        isDeleted = true;
    }

    public void change(UpdatePostDTO updatePostDTO) {
        title = updatePostDTO.getTitle();
        content = updatePostDTO.getContent();
    }
}
