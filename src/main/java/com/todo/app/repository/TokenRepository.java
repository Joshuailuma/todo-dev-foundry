package com.todo.app.repository;

import com.todo.app.model.entity.Token;
import com.todo.app.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, UUID> {
    Optional<Token> findByToken(String token);

    @Query(value = "SELECT * FROM token WHERE users_id = ?1 AND (is_expired = 'false' or is_revoked = 'false')", nativeQuery = true)
    List<Token> findAllValidTokenByUser(UUID id);

    List<Token> findAllTokenByUsers(User user);
}
