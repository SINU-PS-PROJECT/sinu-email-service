package com.example.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.springframework.stereotype.Component;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Component
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = FeeInformationToSendDTO.class)
public class FeeInformationToSendDTO {

  private String studentName;

  private String feeName;

  private double feeAmount;

  private String dateOfPayment;

  private String studentEmail;

  @Override
  public String toString() {
    return "FeeInformationToSendDTO{" +
        "studentName='" + studentName + '\'' +
        ", feeName='" + feeName + '\'' +
        ", feeAmount=" + feeAmount +
        ", dateOfPayment='" + dateOfPayment + '\'' +
        '}';
  }
}
