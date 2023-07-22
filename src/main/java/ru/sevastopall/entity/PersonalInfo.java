package ru.sevastopall.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sevastopall.converter.BirthdayConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class PersonalInfo {

    private String firstName;
    private String lastName;

/*    @Convert(converter = BirthdayConverter.class)
    @Column(name = "birth_date")*/
    private Birthday birthDate;
}
