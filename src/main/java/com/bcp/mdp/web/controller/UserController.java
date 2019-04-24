package com.bcp.mdp.web.controller;

import com.bcp.mdp.model.RoleName;
import com.bcp.mdp.model.User;
import com.bcp.mdp.security.CurrentUser;
import com.bcp.mdp.security.UserPrincipal;
import com.bcp.mdp.service.TransferInBPServiceV1;
import com.bcp.mdp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TransferInBPServiceV1 transferService;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('AGENT') or hasRole('CTRL') or hasRole('CTN')")
    public User getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        User userSummary = new User();
        userSummary = userService.getUserByRegistrationNumber(currentUser.getUsername());
        return userSummary;
    }

    @GetMapping("/user/checkRegistrationNumberAvailability")
    public Boolean checkRegistrationNumberAvailability(@RequestParam(value = "registrationNumber") String registrationNumber) {
        Boolean isAvailable = !userService.findUserByRegistrationNumber(registrationNumber);
        return isAvailable;
    }

    @GetMapping("/user/checkEmailAvailability")
    public Boolean checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userService.findUserByEmail(email);
        return isAvailable;
    }

    @GetMapping("/users/{registrationNumber}")
    public User getUserProfile(@PathVariable(value = "registrationNumber") String registrationNumber) {
        User userProfile = userService.getUserByRegistrationNumber(registrationNumber);
        RoleName userRole = userProfile.getRoles().iterator().next().getName();
        userProfile.setRole(userRole.toString());
        /*if(userProfile.getRole().equals("ROLE_CTRL")){
            Teller userTeller = userProfile.getTeller();
            userProfile.setDescription(userTeller.getDescription());
        }*/

        return userProfile;
    }

    @PutMapping("/users/{registrationNumber}")
    public User updateUserProfile(@PathVariable(value = "registrationNumber") String registrationNumber, @Valid @RequestBody User userRequest) {
        User userProfile = userService.updateUser(registrationNumber, userRequest);

        return userProfile;
    }

    /*@GetMapping("/users/{username}/transfers")
    public PagedResponse<Transfer> getTransfersCreatedBy(@PathVariable(value = "registrationNumber") String registrationNumber,
                                                   @CurrentUser UserPrincipal currentUser,
                                                   @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                   @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        //return transferService.getTransfersCreatedBy(registrationNumber, currentUser, page, size);
        return transferService.getTransfersCreatedBy(registrationNumber, currentUser, page, size);
    }*/

}
