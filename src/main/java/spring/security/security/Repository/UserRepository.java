package spring.security.security.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spring.security.security.Entity.Users;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    @Query(value = "select * from users u where u.user_name=?1",nativeQuery = true)
    Users findUserName(String userName);

    Optional<Users> findByUsername(String username);


}
