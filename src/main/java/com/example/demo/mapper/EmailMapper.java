package com.example.demo.mapper;

import com.example.demo.model.EMail;
import com.example.demo.model.dto.EmailInformationForSendDTO;
import com.example.demo.model.dto.FeeInformationToSendDTO;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class EmailMapper {

  public static EMail prepareEmail(FeeInformationToSendDTO feeInformationToSendDTO,String sender,String emailSubject) {
    Map<String, Object> model = new HashMap<>();
    model.put("studentName", feeInformationToSendDTO.getStudentName());
    model.put("feeName", feeInformationToSendDTO.getFeeName());
    model.put("feeAmount", feeInformationToSendDTO.getFeeAmount());
    model.put("dateOfPayment", feeInformationToSendDTO.getDateOfPayment());
    return EMail.builder()
        .from(sender)
        .to(feeInformationToSendDTO.getStudentEmail())
        .subject(emailSubject)
        .model(model)
        .build();
  }

  public static EMail prepareAdministratorEmail(EmailInformationForSendDTO emailInformationForSendDTO, String sender){
    Map<String, Object> model = new HashMap<>();
    model.put("receiverName", emailInformationForSendDTO.getReceiverName());
    model.put("topic", emailInformationForSendDTO.getTopic());
    model.put("description", emailInformationForSendDTO.getDescription());
    return EMail.builder()
        .subject(emailInformationForSendDTO.getSubject())
        .to(emailInformationForSendDTO.getReceiverEmail())
        .from(sender)
        .model(model)
        .build();
  }

}
