package ru.sevastopall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Birthday {
    private LocalDate birthDate;
    public long getAge() {
        return ChronoUnit.YEARS.between(birthDate, LocalDate.now());
    }
}
