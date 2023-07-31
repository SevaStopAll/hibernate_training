package ru.sevastopall.dto;

import ru.sevastopall.entity.PersonalInfo;
import ru.sevastopall.entity.Role;
import ru.sevastopall.validation.UpdateCheck;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public record UserCreateDto(@Valid PersonalInfo personalInfo,
                            @NotNull String username,
                            String info,
                            @NotNull(groups = UpdateCheck.class)
                            Role role,
                            Integer companyId) {
}
