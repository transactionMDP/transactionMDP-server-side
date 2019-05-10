package com.bcp.mdp.web.controller;

import com.bcp.mdp.model.User;
import com.bcp.mdp.security.CurrentUser;
import com.bcp.mdp.security.UserPrincipal;
import com.bcp.mdp.service.TransferInBPServiceV1;
import com.bcp.mdp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.io.FilenameUtils.removeExtension;

@RestController
@RequestMapping("/api/file")
public class FileRestController {

    @Autowired
    private TransferInBPServiceV1 transferInBPServiceV1;

    @PostMapping("/upload")
    public /*UploadFileResponse*/void uploadFile(@RequestParam("file") MultipartFile file, @CurrentUser UserPrincipal currentUser) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        fileName = removeExtension(fileName);
        System.out.println("fileName: "+fileName);

        String delimiters = "[_]";

        // analyzing the string
        String[] tokensVal = fileName.split(delimiters);

        // prints the count of tokens
        System.out.println("Num du Compte: "+tokensVal[0]);
        System.out.println("Montant: "+tokensVal[1]);

        for(String token : tokensVal) {
            System.out.print(token+" ");
        }
        // transferInBPServiceV1.storeFile(file,"insea_it");
        /*User user = userService.storeFile(file,"insea_it");

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(user.getEmail())
                .toUriString();

        return new UploadFileResponse(user.getEmail(), fileDownloadUri,
                file.getContentType(), file.getSize());*/
    }

    /*@GetMapping("/downloadFile/{userName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String userName) {
        // Load file as Resource
        User user = userService.loadUserDoc(userName);


        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(user.getName()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + user.getUsername() + "\"")
                .body(new ByteArrayResource(user.getAvatar()));
    }*/
}
