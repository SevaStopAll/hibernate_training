package ru.sevastopall.mapper;

import lombok.RequiredArgsConstructor;
import ru.sevastopall.dto.UserCreateDto;
import ru.sevastopall.entity.User;
import ru.sevastopall.repository.CompanyRepository;

@RequiredArgsConstructor
public class UserCreateMapper implements Mapper<UserCreateDto, User> {

    private final CompanyRepository companyRepository;
    @Override
    public User mapFrom(UserCreateDto object) {
        return User.builder()
                .personalInfo(object.personalInfo())
                .username(object.username())
                .info(object.info())
                .role(object.role())
                .company(companyRepository.findById(object.companyId()).orElseThrow(IllegalArgumentException::new))
                .build();
    }
}
