package me.asunamyadmin.loregardsite.profile.domain;

import me.asunamyadmin.loregardsite.profile.data.ProfileEntity;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {
    public Profile mapProfileEntityToProfile(ProfileEntity profileEntity) {
        return new Profile(
                profileEntity.getUsername(),
                profileEntity.getUserId(),
                profileEntity.getAccountNumber(),
                profileEntity.getGameID(),
                profileEntity.getCreatedAt()
        );
    }

    public ProfileEntity mapProfileToProfileEntity(Profile profile) {
        ProfileEntity entity = new ProfileEntity();
        entity.setUsername(profile.username());
        entity.register(new FirstlyProfile(
                profile.userId(),
                profile.accountNumber(),
                profile.gameID()
        ));
        return entity;
    }
}
