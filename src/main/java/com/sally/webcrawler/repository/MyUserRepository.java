package com.sally.webcrawler.repository;

import com.sally.webcrawler.model.Keyword;
import com.sally.webcrawler.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MyUserRepository extends JpaRepository<MyUser, Integer> {
    Optional<MyUser> findByEmail(String email);
    Optional<MyUser> findById(Long id);
    @Query("SELECT u FROM MyUser u JOIN u.keywords k WHERE k = ?1")//selects users who are associated with a specific keyword
    List<MyUser> findByKeywords(Keyword keyword);
}
