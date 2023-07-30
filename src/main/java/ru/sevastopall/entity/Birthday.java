package ru.sevastopall.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Builder
public record Birthday(LocalDate birthDate) {

    public long getAge() {
        return ChronoUnit.YEARS.between(birthDate, LocalDate.now());
    }
}
