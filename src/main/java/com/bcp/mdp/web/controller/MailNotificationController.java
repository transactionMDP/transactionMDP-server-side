package com.bcp.mdp.web.controller;


import com.bcp.mdp.dto.MailMessageDto;
import com.bcp.mdp.service.MailNotificationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;


@RestController
public class MailNotificationController  {

 
    @PostMapping("/simpleemail")
    public void sendMail(@RequestBody MailMessageDto mailMessage) throws MessagingException, IOException {
    	MailNotificationService.sendSimpleMessage(mailMessage);
    	
    }
}
