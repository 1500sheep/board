package com.board.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@Entity
@ToString
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private int id;

    @Column(name = "role")
    private String role;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Role)) return false;
        Role otherRole = (Role) obj;
        return role.equals(otherRole.getRole());
    }

    public boolean isAdmin() {
        if (role.equals("관리자")) {
            return true;
        }
        return false;
    }
}
