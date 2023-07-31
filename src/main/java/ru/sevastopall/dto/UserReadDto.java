package ru.sevastopall.dto;


import ru.sevastopall.entity.PersonalInfo;
import ru.sevastopall.entity.Role;

public record UserReadDto(Long id,
                          PersonalInfo personalInfo,
                          String username,
                          String info,
                          Role role,
                          CompanyReadDto company ) {

}
