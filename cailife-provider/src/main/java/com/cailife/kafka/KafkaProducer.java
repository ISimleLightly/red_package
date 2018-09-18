package com.cailife.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
@EnableScheduling
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Scheduled(cron = "1-59 * * * * ? ")
    public void send() {
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send("test-topic", "hello kafka");
        future.addCallback(o -> System.out.println("send-消息发送成功：" ), throwable -> System.out.println("消息发送失败"));
    }
}
