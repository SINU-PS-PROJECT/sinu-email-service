package com.example.demo.controller;

import com.example.demo.model.dto.EmailInformationForSendDTO;
import com.example.demo.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/send-email")
public class EmailSenderController {

  private final EmailSenderService emailSenderService;

  @Autowired
  public EmailSenderController(EmailSenderService emailSenderService) {
    this.emailSenderService = emailSenderService;
  }

  @PostMapping
  public ResponseEntity<EmailInformationForSendDTO> sendEmailAPI(@RequestBody EmailInformationForSendDTO emailInformationForSendDTO){
    emailSenderService.sendAdministratorEmail(emailInformationForSendDTO);
    return new ResponseEntity<>(emailInformationForSendDTO,HttpStatus.ACCEPTED);
  }
}
