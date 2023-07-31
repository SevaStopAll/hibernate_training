package ru.sevastopall.entity;

import lombok.*;
import org.checkerframework.checker.units.qual.C;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = "name")
@EqualsAndHashCode(exclude = "users")
@Builder
@Table(name = "company")
@Entity
@Audited
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "Companies")
public class Company implements BaseEntity<Integer>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;
    @Builder.Default
    @OneToMany(mappedBy = "company", orphanRemoval = true/*, cascade = CascadeType.ALL*/)
    /*@JoinColumn(name = "company_id")*/
    /*@org.hibernate.annotations.OrderBy(clause = "username DESC, lastname ASC")*/
    /*@OrderBy(value="username DESC, personalInfo.lastName ASC")*/
    @MapKey(name = "username")
    @NotAudited
    private Map<String, User> users = new HashMap<>();

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "company_locale")
/*    @AttributeOverride(name="lang", column = @Column(name = "language"))*/
    @Column(name="description")
    @MapKeyColumn(name="lang")
    @NotAudited
    private Map<String, String> locales = new HashMap<>();

    public void addUser(User user) {
        users.put(user.getUsername(), user);
        user.setCompany(this);
    }
}
