package me.asunamyadmin.loregardsite.profile.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import me.asunamyadmin.loregardsite.profile.domain.ProfileStatus;
import me.asunamyadmin.loregardsite.security.UserRole;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "profiles")
public class ProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Setter
    @Column(nullable = false, unique = true, name = "username")
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "account_number", unique = true)
    private Integer accountNumber;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProfileStatus status;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        status = ProfileStatus.ACTIVE;
        role = UserRole.USER;
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

    public void setGroup(UserRole role){
        this.role = role;
    }

    public void setAccountNumber(Integer accountNumber) {
        if (this.accountNumber != null) {
            return;
        }
        this.accountNumber = accountNumber;
    }


    public void setPassword(String password) {
        if (this.password != null) {
            return;
        }
        this.password = password;
    }
}
