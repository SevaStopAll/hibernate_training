package ru.sevastopall.entity;

import lombok.*;
import org.hibernate.annotations.Type;
import ru.sevastopall.converter.BirthdayConverter;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = "username")
@EqualsAndHashCode(exclude = "company")
@Builder
@Entity
@Table(name="users", schema = "public")
public class User {

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

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id") // company_id (название сущности и через _ название первичного ключа)
    private Company company;
}
