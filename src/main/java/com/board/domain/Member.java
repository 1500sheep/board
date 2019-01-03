package com.board.domain;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Pattern(regexp = "")
    @Column(nullable = false, unique = true, updatable = false)
    private String email;

    @NotNull
    @Pattern(regexp = "")
    @Column(nullable = false)
    private String password;

    @NotNull
    @Pattern(regexp = "")
    @Column(nullable = false, unique = true)
    private String name;

    @NotNull
    @Pattern(regexp = "")
    @Column(nullable = false)
    private String phoneNumber;

    @NotNull
    @Column(nullable = false)
    private String level;

    @Length(max = 500)
    private String profileImageUrl;


}
