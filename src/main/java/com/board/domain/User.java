package com.board.domain;

import com.board.dto.UpdateDTO;
import com.board.support.domain.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import utils.ValidateRegex;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class User extends BaseTimeEntity {

    public static final GuestUser GUEST_USER = new GuestUser();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotNull
    @Pattern(regexp = ValidateRegex.EMAIL)
    @Column(nullable = false, unique = true, updatable = false)
    private String email;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;

    @NotNull
    @Pattern(regexp = ValidateRegex.USERNAME)
    @Column(nullable = false, unique = true)
    private String name;

    @NotNull
    @Pattern(regexp = ValidateRegex.PHONE)
    @Column(nullable = false)
    private String phoneNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private FileInfo profile;

    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();

    @Column
    private boolean isDeleted = false;

    public void update(UpdateDTO updateDTO) {
        name = updateDTO.getName();
        phoneNumber = updateDTO.getPhoneNumber();
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void changeToDeleted() {
        isDeleted = true;
    }

    public boolean isAdmin() {
        return roles.stream()
                .anyMatch(role -> role.isAdmin());
    }

    @JsonIgnore
    public boolean isGuestUser() {
        return false;
    }

    private static class GuestUser extends User {
        @Override
        public boolean isGuestUser() {
            return true;
        }

        public boolean equalsEmail(User user) {
            if (getEmail().equals(user.getEmail())) {
                return true;
            }
            return false;
        }
    }
}
