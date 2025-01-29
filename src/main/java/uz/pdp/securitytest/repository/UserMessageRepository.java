package uz.pdp.securitytest.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery;
import uz.pdp.securitytest.entity.UserMessage;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface UserMessageRepository extends JpaRepository<UserMessage, Integer> {
    @Query("select u from UserMessage u where u.accepter.id=:userId")
    List<UserMessage> findByUserId(Integer userId);

    @Query("select u from UserMessage u where u.sender.id=:userId")
    List<UserMessage> findBySenderId(Integer userId);
}

