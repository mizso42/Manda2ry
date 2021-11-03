package application.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter @Setter
public class Blogger implements UserDetails {

    @Id
    private long id;

    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String emilAddress;
    private String password;
    private byte[] pic;

    @Enumerated(EnumType.STRING)
    private UserRole authority;

    @CreationTimestamp
    private LocalDateTime regTime;
    private boolean isLocked;

    @OneToMany(mappedBy = "author")
    private List<Blog> blogs;
    @OneToMany(mappedBy = "author")
    private List<Comment> comments;

    public Blogger() {
        pic = new byte[] {};
        blogs = new ArrayList<>();
        comments = new ArrayList<>();
        authority = UserRole.USER;
    }

    public Blogger(String username, String emilAddress, String password) {
        this();
        this.username = username;
        this.emilAddress = emilAddress;
        this.password = password;
    }

    public Blogger(String username, String emilAddress, String password, byte[] pic) {
        this(username, emilAddress, password);
        this.pic = pic;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        for (UserAuth auth : authority.AUTHS) {
            authorityList.add(new SimpleGrantedAuthority(auth.toString()));
        }

        return authorityList;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !isLocked;
    }
}