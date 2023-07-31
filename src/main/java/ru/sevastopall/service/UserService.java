package ru.sevastopall.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.graph.GraphSemantic;
import ru.sevastopall.dto.UserCreateDto;
import ru.sevastopall.dto.UserReadDto;
import ru.sevastopall.entity.User;
import ru.sevastopall.mapper.Mapper;
import ru.sevastopall.mapper.UserCreateMapper;
import ru.sevastopall.mapper.UserReadMapper;
import ru.sevastopall.repository.UserRepository;
import ru.sevastopall.validation.UpdateCheck;

import javax.transaction.Transactional;
import javax.validation.*;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserCreateMapper userCreateMapper;

    @Transactional
    public Long create(UserCreateDto userDto) {
        //validation
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<UserCreateDto>> validationResult = validator.validate(userDto, UpdateCheck.class);
        if (!validationResult.isEmpty()) {
            throw new ConstraintViolationException(validationResult);
        }



        User userEntity = userCreateMapper.mapFrom(userDto);
        return userRepository.save(userEntity).getId();
    }

    @Transactional
    public <T> Optional<T> findById(Long id, Mapper<User, T> mapper) {
        Map<String, Object> properties = Map.of(
                GraphSemantic.LOAD.getJpaHintName(), userRepository.getEntityManager().getEntityGraph("WithCompany")
        );
        return userRepository.findById(id, properties)
                .map(mapper::mapFrom);
    }

    @Transactional
    public Optional<UserReadDto> findById(Long id) {
        return findById(id, userReadMapper);
    }

    @Transactional
    public boolean deleteUser(Long id) {
        Optional<User> maybeUser = userRepository.findById(id);
        maybeUser.ifPresent(user -> userRepository.delete(user.getId()));
        return maybeUser.isPresent();
    }
}
