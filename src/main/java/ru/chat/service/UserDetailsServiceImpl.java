package ru.chat.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.chat.domain.Person;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private PersonService users;

    public UserDetailsServiceImpl(PersonService users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person user = users.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(user.getLogin(), user.getPassword(), emptyList());
    }
}