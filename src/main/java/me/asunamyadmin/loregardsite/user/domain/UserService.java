package me.asunamyadmin.loregardsite.user.domain;

import me.asunamyadmin.loregardsite.user.data.UserEntity;
import me.asunamyadmin.loregardsite.user.data.UserRepository;
import me.asunamyadmin.loregardsite.user.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserEntityMapper userEntityMapper;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userEntityMapper = new UserEntityMapper();
    }

    public List<User> findAll() {
        List<UserEntity> usersFromRepository = new ArrayList<>(userRepository.findAll());
        List<User> users = new ArrayList<>();
        for (UserEntity userEntity : usersFromRepository) {
            User user = userEntityMapper.toUser(userEntity);
            users.add(user);
        }
        return users;
    }

    public User findById(int id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return userEntityMapper.toUser(userEntity);
    }

    @Transactional
    public User updateUser (User user, int id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userEntity.setEmail(user.email());
        userEntity.setPassword(user.password());
        return userEntityMapper.toUser(userRepository.save(userEntity));
    }

    @Transactional
    public User saveUser(User user) {
        UserEntity newEntity = userRepository.save(userEntityMapper.toUserEntity(user));
        return userEntityMapper.toUser(newEntity);
    }

    @Transactional
    public void deleteById(int id) {
        userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userRepository.deleteById(id);
    }


}
