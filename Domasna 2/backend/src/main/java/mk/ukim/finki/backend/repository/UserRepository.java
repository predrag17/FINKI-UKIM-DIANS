package mk.ukim.finki.backend.repository;

import mk.ukim.finki.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByUsername(String username);

    boolean existsByUsernameOrEmail(String username, String email);

    boolean existsByUsername(String admin);
}
