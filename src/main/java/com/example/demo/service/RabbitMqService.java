package com.example.demo.service;

import com.example.demo.model.dto.FeeInformationToSendDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqService implements RabbitListenerConfigurer {
  private static final Logger logger = LoggerFactory.getLogger(RabbitMqService.class);

  private final EmailSenderService emailSenderService;

  @Autowired
  public RabbitMqService(EmailSenderService emailSenderService) {
    this.emailSenderService = emailSenderService;
  }

  @Override
  public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {
  }

  @RabbitListener(queues = "${spring.rabbitmq.queue}")
  public void receivedMessage(FeeInformationToSendDTO feeInformationToSendDTO) {
    logger.info("Fee Details Received is.. " + feeInformationToSendDTO);
    if (checkParameters(feeInformationToSendDTO)) {
      emailSenderService.sendFeeInformationEmail(feeInformationToSendDTO);
    }
  }

  private boolean checkParameters(FeeInformationToSendDTO feeInformationToSendDTO){
    if(feeInformationToSendDTO.getStudentName() != null &&
        feeInformationToSendDTO.getFeeName() != null &&
        feeInformationToSendDTO.getFeeAmount() != 0.0 &&
        feeInformationToSendDTO.getDateOfPayment() != null){
       return true;
    }
    return false;
  }

}
