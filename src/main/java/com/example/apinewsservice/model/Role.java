package com.example.apinewsservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
@Setter
@Entity(name = "authorities")
public class Role {
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated
    private RoleType authority;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "name")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;

    public GrantedAuthority toAuthority() {
        return new SimpleGrantedAuthority(authority.name());
    }
    public static Role from(RoleType type) {
        var role = new Role();
        role.setAuthority(type);
        return role;
    }
}
