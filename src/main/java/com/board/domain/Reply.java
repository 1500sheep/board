package com.board.domain;

import com.board.support.domain.BaseTimeEntity;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Reply extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private User writer;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_reply_product"))
    private Post post;

    @NotEmpty
    @Length(min = 1, max = 100)
    @Column(nullable = false, length = 100)
    private String comment;

    @Column
    private boolean isDeleted = false;

    public void changeToDeleted() {
        isDeleted = true;
    }
}
