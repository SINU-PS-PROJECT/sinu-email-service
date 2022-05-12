package com.example.demo.service;


import com.example.demo.mapper.EmailMapper;
import com.example.demo.model.EMail;
import com.example.demo.model.dto.EmailInformationForSendDTO;
import com.example.demo.model.dto.FeeInformationToSendDTO;
import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

@Component
public class EmailSenderService {

  private final JavaMailSender javaMailSender;
  private final Configuration fmConfiguration;

  @Value("${email.sender}")
  private String sender;

  @Value("${email.subject}")
  private String emailSubject;

  @Autowired
  public EmailSenderService(JavaMailSender javaMailSender, Configuration fmConfiguration) {
    this.javaMailSender = javaMailSender;
    this.fmConfiguration = fmConfiguration;
  }


  public void sendFeeInformationEmail(FeeInformationToSendDTO feeInformationToSendDTO) {
    EMail mail = EmailMapper.prepareEmail(feeInformationToSendDTO,sender,emailSubject);
    MimeMessage mimeMessage =javaMailSender.createMimeMessage();
    try {

      MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

      mimeMessageHelper.setSubject(mail.getSubject());
      mimeMessageHelper.setFrom(mail.getFrom());
      mimeMessageHelper.setTo(mail.getTo());
      mail.setContent(geContentFromTemplate(mail.getModel()));
      mimeMessageHelper.setText(mail.getContent(), true);

      javaMailSender.send(mimeMessageHelper.getMimeMessage());
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }


  public void sendAdministratorEmail(EmailInformationForSendDTO emailInformationForSendDTO){
    EMail mail = EmailMapper.prepareAdministratorEmail(emailInformationForSendDTO,sender);
    MimeMessage mimeMessage =javaMailSender.createMimeMessage();
    try {

      MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

      mimeMessageHelper.setSubject(mail.getSubject());
      mimeMessageHelper.setFrom(mail.getFrom());
      mimeMessageHelper.setTo(mail.getTo());
      mail.setContent(geContentFromAdminEmailTemplate(mail.getModel()));
      mimeMessageHelper.setText(mail.getContent(), true);

      javaMailSender.send(mimeMessageHelper.getMimeMessage());
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }

  private String geContentFromTemplate(Map< String, Object > model)     {
    StringBuffer content = new StringBuffer();

    try {
      content.append(FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate("email-template.flth"), model));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return content.toString();
  }

  private String geContentFromAdminEmailTemplate(Map< String, Object > model)     {
    StringBuffer content = new StringBuffer();

    try {
      content.append(FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate("admin-email-template.flth"), model));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return content.toString();
  }





}
