package com.revup.user.repository;


import com.revup.user.entity.LoginType;
import com.revup.user.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findBySocialIdAndLoginType(String socialId, LoginType loginType);

    @Override
    Optional<User> findById(Long id);
}
