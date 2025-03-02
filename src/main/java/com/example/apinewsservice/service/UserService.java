package com.example.apinewsservice.service;

import com.example.apinewsservice.exception.EntityNotFoundException;
import com.example.apinewsservice.model.Role;
import com.example.apinewsservice.model.User;
import com.example.apinewsservice.repository.UserRepository;
import com.example.apinewsservice.util.BeanUtils;
import com.example.apinewsservice.web.model.UserFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public List<User> findAll(UserFilter filter) {
       return userRepository.findAll(PageRequest.of(filter.getPageNumber(), filter.getPageSize())).getContent();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(MessageFormat.format("Пользователь с id {0} не найден", id)));
    }

    public User save(User user, Role role) {
        user.setRoles(Collections.singletonList(role));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        role.setUser(user);

        return userRepository.saveAndFlush(user);
    }

    public User update(User user) {
        User existedUser = findById(user.getId());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        BeanUtils.copyNonNullProperties(user, existedUser);
        return userRepository.save(existedUser);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public User findByUserName(String name) {
        return userRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Username not found!"));
    }
}
