package hellojpa;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(100) default 'EMPTY'")
    private String username;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    //@Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createDate;

    //@Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime lastModifiedDate;

    @Lob
    private String description;

    @Transient
    private int temp;

    public Member() {
    }
}
