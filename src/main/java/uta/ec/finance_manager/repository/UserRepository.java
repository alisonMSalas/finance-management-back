package uta.ec.finance_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uta.ec.finance_manager.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}
