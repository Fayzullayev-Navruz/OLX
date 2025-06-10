package uz.pdp.securitytest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery;
import uz.pdp.securitytest.entity.UserMessage;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface UserMessageRepository extends JpaRepository<UserMessage, Integer> {
    @Query("select u from UserMessage u where u.accepter.id=:userId or u.sender.id=:userId")
    List<UserMessage> findByUserId(Integer userId);

    @Query("select u from UserMessage u where (u.sender.id=:userId and u.accepter.email=:fullName) or (u.accepter.id=:userId and u.sender.email=:fullName ) order by u.timestamp asc")
    List<UserMessage> findByChat(Integer userId,String fullName);
}

