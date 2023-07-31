package ru.sevastopall.mapper;

import lombok.RequiredArgsConstructor;
import ru.sevastopall.dto.UserReadDto;
import ru.sevastopall.entity.User;

import java.util.Optional;

@RequiredArgsConstructor
public class UserReadMapper implements Mapper<User, UserReadDto> {
    private final CompanyReadMapper companyReadMapper;

    @Override
    public UserReadDto mapFrom(User object) {
        return new UserReadDto(
                object.getId(),
                object.getPersonalInfo(),
                object.getUsername(),
                object.getInfo(),
                object.getRole(),
                Optional.ofNullable(
                        object.getCompany())
                        .map(companyReadMapper::mapFrom)
                        .orElse(null)
                );
    }
}
