package me.asunamyadmin.loregardsite.profile.domain;

import me.asunamyadmin.loregardsite.profile.data.ProfileEntity;
import me.asunamyadmin.loregardsite.profile.data.ProfileRepository;
import me.asunamyadmin.loregardsite.profile.exception.AlreadyHaveThisStatusException;
import me.asunamyadmin.loregardsite.profile.exception.ProfileNotFoundException;
import me.asunamyadmin.loregardsite.profile.exception.UsernameAlreadyTakenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final ProfileMapper mapper;

    @Autowired
    public ProfileService(ProfileRepository profileRepository, ProfileMapper mapper) {
        this.profileRepository = profileRepository;
        this.mapper = mapper;
    }

    public List<Profile> getAllProfiles(){
        return profileRepository.findAll().stream()
                .map(mapper::mapProfileEntityToProfile)
                .collect(Collectors.toList());
    }

    public Profile getProfileById(int id){
        ProfileEntity entity = profileRepository.findById(id).orElseThrow(ProfileNotFoundException::new);
        return mapper.mapProfileEntityToProfile(entity);
    }

    public Profile getProfileByUsername(String username){
        ProfileEntity entity = profileRepository.findByUsername(username).orElseThrow(ProfileNotFoundException::new);
        return mapper.mapProfileEntityToProfile(entity);
    }

    public boolean findProfileByUsername(String username){
        Optional<ProfileEntity> entity = profileRepository.findByUsername(username);
        return entity.isPresent();
    }

    public Profile createProfile(Profile profile){
        ProfileEntity profileEntity = mapper.mapProfileToProfileEntity(profile);
        return mapper.mapProfileEntityToProfile(profileRepository.save(profileEntity));
    }

    public void changeNameProfile(int id, String newName){
        ProfileEntity entity = profileRepository.findById(id).orElseThrow(ProfileNotFoundException::new);
        if (findProfileByUsername(newName)){
            throw new UsernameAlreadyTakenException();
        }
        entity.setUsername(newName);
    }

    public void deleteProfileById(int id){
        ProfileEntity entity = profileRepository.findById(id).orElseThrow(ProfileNotFoundException::new);
        profileRepository.delete(entity);
    }

    public void ban(int id){
        ProfileEntity entity = profileRepository.findById(id).orElseThrow(ProfileNotFoundException::new);
        if (entity.getStatus() == ProfileStatus.BANNED) {
            throw new AlreadyHaveThisStatusException();
        }
        entity.ban();
    }

    public void freeze(int id){
        ProfileEntity entity = profileRepository.findById(id).orElseThrow(ProfileNotFoundException::new);
        if (entity.getStatus() == ProfileStatus.INACTIVE) {
            throw new AlreadyHaveThisStatusException();
        }
        entity.freezeProfile();
    }

    public void activate(int id){
        ProfileEntity entity = profileRepository.findById(id).orElseThrow(ProfileNotFoundException::new);
        if (entity.getStatus() == ProfileStatus.ACTIVE) {
            throw new AlreadyHaveThisStatusException();
        }
        entity.activateProfile();
    }
}
