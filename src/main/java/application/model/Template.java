package application.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Template {

    @Id
    private long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ColorTheme colorTheme;
    @Enumerated(EnumType.STRING)
    private Category category;

    private byte[] backGround;

    public Template() {
        backGround = new byte[] {};
    }
}
