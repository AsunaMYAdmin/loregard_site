package me.asunamyadmin.loregardsite.profile.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import me.asunamyadmin.loregardsite.profile.domain.FirstlyProfile;
import me.asunamyadmin.loregardsite.profile.domain.ProfileStatus;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "profiles")
public class ProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Setter
    private String username;
    private int userId;
    private int accountNumber;
    private int gameID;
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private ProfileStatus status;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        status = ProfileStatus.ACTIVE;
    }

    public void register(FirstlyProfile firstlyProfile){
        userId = firstlyProfile.userId();
        accountNumber = firstlyProfile.accountNumber();
        gameID = firstlyProfile.gameID();
    }

    public void freezeProfile(){
        status = ProfileStatus.INACTIVE;
    }

    public void activateProfile(){
        status = ProfileStatus.ACTIVE;
    }

    public void ban() {
        status = ProfileStatus.BANNED;
    }
}
