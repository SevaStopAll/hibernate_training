package ru.sevastopall.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
/*@DiscriminatorValue("manager")*/
@PrimaryKeyJoinColumn(name = "id")
public class Manager extends User {

    private String projectName;

    @Builder
    public Manager(Long id, String username, PersonalInfo personalInfo, String info, Role role, Company company, Profile profile, List<UserChat> userChats, String projectName) {
        super(id, username, personalInfo, info, role, company, profile, userChats);
        this.projectName = projectName;
    }
}
