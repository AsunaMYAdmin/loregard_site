package me.asunamyadmin.loregardsite.profile.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity,Integer> {
    Optional<ProfileEntity> findByUsername(String username);

    @Query(value = "SELECT nextval('account_number_seq')", nativeQuery = true)
    Integer getNextAccountNumber();

    Optional<ProfileEntity> findProfileByUsername(String username);
}
