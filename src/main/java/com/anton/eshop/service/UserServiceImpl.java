package com.anton.eshop.service;

import com.anton.eshop.data.Cart;
import com.anton.eshop.data.User;
import com.anton.eshop.dto.UserDTO;
import com.anton.eshop.dto.mapDTO.UserMapper;
import com.anton.eshop.repository.CartRepository;
import com.anton.eshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, CartRepository cartRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }


    @Override
    public boolean save(UserDTO userDTO) {
        if (Objects.equals(userDTO.getPassword(), userDTO.getMatchingPassword())) {
            Cart cart = new Cart();
            cart.setItems(new ArrayList<>());
            User user = User.builder()
                    .username(userDTO.getUsername())
                    .password(passwordEncoder.encode(userDTO.getPassword()))
                    .email(userDTO.getEmail())
                    .role(userDTO.getRole())
                    .cart(cart)
                    .build();

            userRepository.save(user);
            cartRepository.save(cart);
            return true;
        } else {
            throw new RuntimeException("Password is not equals.");
        }
    }


    @Override
    public List<UserDTO> fetchAll() {
        return ((List<User>) userRepository.findAll())
                .stream()
                .map(it -> UserMapper.MAPPER.userMapUserDTO(it))
                .collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = fetchUserByUsername(username);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("Unknown username: " + username);
        }

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(user.getRole().name()));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                roles
        );
    }



    public User fetchUserByUsername(String username) {
        List<User> users = (List<User>) userRepository.findAll();
        User user = null;

        for (User usr : users) {
            if (Objects.equals(usr.getUsername(), username)) {
                user = usr;
                break;
            }
        }

        return Objects.nonNull(user) ? user : null;
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        User updateUser = fetchUserByUsername(userDTO.getUsername());
        boolean isCheck = false;

        if (Objects.isNull(updateUser)) {
            throw new RuntimeException("User not found: " + userDTO.getUsername());
        }

        if (!Objects.equals(updateUser.getEmail(), userDTO.getEmail())) {
            updateUser.setEmail(userDTO.getEmail());
            isCheck = true;
        }

        if (Objects.nonNull(userDTO.getPassword()) && !userDTO.getPassword().isEmpty()) {
            updateUser.setPassword(userDTO.getPassword());
            isCheck = true;
        }

        if (isCheck) userRepository.save(updateUser);
    }
}
