package me.asunamyadmin.loregardsite.profile.domain;

import me.asunamyadmin.loregardsite.profile.data.ProfileEntity;
import me.asunamyadmin.loregardsite.profile.data.ProfileRepository;
import me.asunamyadmin.loregardsite.profile.exception.AlreadyHaveThisStatusException;
import me.asunamyadmin.loregardsite.profile.exception.ProfileNotFoundException;
import me.asunamyadmin.loregardsite.profile.exception.UsernameAlreadyTakenException;
import me.asunamyadmin.loregardsite.security.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Profile createProfile(Profile profile){
        ProfileEntity profileEntity = mapper.mapProfileToProfileEntity(profile);
        profileEntity.setAccountNumber(profileRepository.getNextAccountNumber());
        profileRepository.save(profileEntity);
        return mapper.mapProfileEntityToProfile(profileEntity);
    }

    @Transactional
    public void changeNameProfile(int id, String newName){
        ProfileEntity entity = profileRepository.findById(id).orElseThrow(ProfileNotFoundException::new);
        if (findProfileByUsername(newName)){
            throw new UsernameAlreadyTakenException();
        }
        entity.setUsername(newName);
    }

    @Transactional
    public void deleteProfileById(int id){
        ProfileEntity entity = profileRepository.findById(id).orElseThrow(ProfileNotFoundException::new);
        profileRepository.delete(entity);
    }

    @Transactional
    public void ban(int id){
        ProfileEntity entity = profileRepository.findById(id).orElseThrow(ProfileNotFoundException::new);
        if (entity.getStatus() == ProfileStatus.BANNED) {
            throw new AlreadyHaveThisStatusException();
        }
        entity.ban();
    }

    @Transactional
    public void freeze(int id){
        ProfileEntity entity = profileRepository.findById(id).orElseThrow(ProfileNotFoundException::new);
        if (entity.getStatus() == ProfileStatus.INACTIVE) {
            throw new AlreadyHaveThisStatusException();
        }
        entity.freezeProfile();
    }

    @Transactional
    public void activate(int id){
        ProfileEntity entity = profileRepository.findById(id).orElseThrow(ProfileNotFoundException::new);
        if (entity.getStatus() == ProfileStatus.ACTIVE) {
            throw new AlreadyHaveThisStatusException();
        }
        entity.activateProfile();
    }

    @Transactional
    public void setGroup(int id, UserRole role){
        ProfileEntity entity = profileRepository.findById(id).orElseThrow(ProfileNotFoundException::new);
        if (entity.getStatus() == ProfileStatus.INACTIVE || entity.getStatus() == ProfileStatus.BANNED) {
            throw new IllegalStateException();
        }
        entity.setGroup(role);
        profileRepository.save(entity);
    }
}
