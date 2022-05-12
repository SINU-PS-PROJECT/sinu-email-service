package com.example.demo.model.dto;


import lombok.*;
import org.springframework.stereotype.Component;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Component
public class EmailInformationForSendDTO {

  private String receiverEmail;
  private String topic;
  private String receiverName;
  private String description;
  private String subject;

}
