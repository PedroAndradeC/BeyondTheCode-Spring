package com.jornada.beyondthecodeapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProdutorService {

    @Value(value= "${kafka.topic}")
    private String topico;

    private final KafkaTemplate<String,String> kafkaTemplate;

    public void EnviarMensagemAoTopico(String mensagem) {


        MessageBuilder<String> stringMessageBuilder = MessageBuilder.withPayload(mensagem)
                .setHeader(KafkaHeaders.TOPIC, topico)
                .setHeader(KafkaHeaders.KEY, UUID.randomUUID().toString()); // chave aleatória

        Message<String> mensagemParaKafka = stringMessageBuilder.build();
        kafkaTemplate.send(mensagemParaKafka);
    }

}
