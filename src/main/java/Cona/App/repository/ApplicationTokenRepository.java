package Cona.App.repository;

import Cona.App.domain.ApplicationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationTokenRepository extends JpaRepository<ApplicationToken, Long> {
    Optional<ApplicationToken> findByKey(Long key);
}
