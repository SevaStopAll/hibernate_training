package ru.sevastopall.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@NamedQuery(name="findUserByName", query = "select u from User u " +
        "left join u.company c " +
        "where u.personalInfo.firstName = :firstname and c.name = :companyName " +
        "order by u.personalInfo.lastName desc")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = "username")
@EqualsAndHashCode(exclude = {"company", "profile", "userChats" })
@Builder
@Entity
@Table(name = "users", schema = "public")
@Inheritance(strategy = InheritanceType.JOINED)
/*@DiscriminatorColumn(name = "type")*/
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Embedded
    @AttributeOverride(name="birthDate", column= @Column(name = "birth_date"))
    private PersonalInfo personalInfo;

    @Type(type = "jsonb")
    private String info;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id") // company_id (название сущности и через _ название первичного ключа)
    private Company company;

    @Builder.Default
    @OneToMany(mappedBy = "receiver")
    private List<Payment> payments = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Profile profile;

    @OneToMany(mappedBy = "user")
    private List<UserChat> userChats = new ArrayList<>();

}
