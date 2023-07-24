package ru.sevastopall.entity;

import lombok.*;
import org.hibernate.annotations.Type;
import ru.sevastopall.converter.BirthdayConverter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = "username")
@EqualsAndHashCode(exclude = {"company", "profile", "chats"})
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

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Profile profile;

    @Builder.Default
    @ManyToMany
    @JoinTable(name = "users_chat",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "chat_id"))
    private Set<Chat> chats = new HashSet<>();

    public void addChat(Chat chat) {
        chats.add(chat);
        chat.getUsers().add(this);
    }
}
