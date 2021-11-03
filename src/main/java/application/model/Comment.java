package application.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
public class Comment {

    @Id
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Blogger author;
    @ManyToOne
    @JoinColumn(name = "entry_id")
    private BlogEntry entry;
    @CreationTimestamp
    private LocalDateTime postedAt;

    private String text;

    @ManyToOne
    @JoinColumn(name = "previous_id")
    private Comment previous;
    @OneToMany(mappedBy = "previous")
    private List<Comment> replies;

}
