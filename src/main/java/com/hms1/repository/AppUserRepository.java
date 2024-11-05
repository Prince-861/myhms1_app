package com.hms1.repository;

import com.hms1.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
  Optional<AppUser> findByUsername(String username);//we are not using the camelcasing for this variable. So, the way I'm writing is the 'U' capital and not the 'n', reason is when we created the variable we have given the name with the same-casing(as the username)
//  The above finder method job is to check whether this username is present in the database or not and if yes, then do not allow to sign-up.
  Optional<AppUser> findByEmail(String Email);//This finder method job is to check whether this email id is present in the database or not, and if yes, then do not allow to sign-up.
}
