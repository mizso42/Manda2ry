package application.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Blog {

    @Id
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Blogger author;

    @Column(unique = true)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id")
    private Template template;

    @OneToMany(mappedBy = "blog")
    private List<BlogEntry> blogEntries;

    public Blog() {
        blogEntries = new ArrayList<>();
    }

}
