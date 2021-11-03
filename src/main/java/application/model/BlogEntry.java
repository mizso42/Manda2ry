package application.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class BlogEntry {

    @Id
    private long id;

    private String subtitle;
    private String intro;
    private String body;

    @CreationTimestamp
    private LocalDateTime postedAt;

    @ManyToOne
    @JoinColumn(name = "blog_id")
    private Blog blog;

    @OneToMany(mappedBy = "entry")
    private List<Comment> comments;

    public BlogEntry() {
        comments = new ArrayList<>();
    }

}
