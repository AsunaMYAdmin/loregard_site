package me.asunamyadmin.loregardsite.profile.controller;

import me.asunamyadmin.loregardsite.profile.domain.Profile;
import me.asunamyadmin.loregardsite.profile.domain.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Profile>>  getAllProfiles() {
        List<Profile> profiles = profileService.getAllProfiles();
        return ResponseEntity.ok().body(profiles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profile> getProfile(@PathVariable int id) {
        Profile profile = profileService.getProfileById(id);
        return ResponseEntity.ok().body(profile);
    }

    @GetMapping("/{username}")
    public ResponseEntity<Profile> getProfileByUsername(@PathVariable String username) {
        Profile profile = profileService.getProfileByUsername(username);
        return ResponseEntity.ok().body(profile);
    }

    @PostMapping("/create")
    public ResponseEntity<Profile> createProfile(@RequestBody Profile profile) {
        Profile createdProfile = profileService.createProfile(profile);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProfile);
    }

    @PatchMapping("/newName/{id}/{newName}")
    public ResponseEntity<Profile> updateProfile (@PathVariable int id, @PathVariable String newName) {
        profileService.changeNameProfile(id, newName);
        return ResponseEntity.ok().body(profileService.getProfileById(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable int id) {
        profileService.deleteProfileById(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/setStatus/{id}")
    public ResponseEntity<Void> updateProfileStatus(@PathVariable int id, @RequestParam String status) {
        switch(status) {
            case "banned":
                profileService.ban(id);
                break;
            case "froze":
                profileService.freeze(id);
                break;
            default:
                profileService.activate(id);
        }
        return ResponseEntity.ok().build();
    }
}
