package com.softwarefoundation.ministore.message;

import com.softwarefoundation.ministore.dto.ProdutoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProdutoSendMessage {

    @Value("${ministore.rabbitmq.exchange}")
    String exchange;

    @Value("${ministore.rabbitmq.routingkey}")
    String routingkey;

    RabbitTemplate rabbitTemplate;

    @Autowired
    public ProdutoSendMessage(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(ProdutoDto produtoDto){
        rabbitTemplate.convertAndSend(exchange, routingkey, produtoDto);
        log.info("Produto:{} enviado para fila", produtoDto.getNome());
    }
}
