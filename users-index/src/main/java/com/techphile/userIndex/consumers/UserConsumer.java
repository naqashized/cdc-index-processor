package com.techphile.userIndex.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techphile.userIndex.domain.UserDocument;
import com.techphile.userIndex.domain.UserService;
import com.techphile.userIndex.dtos.PayLoad;
import com.techphile.userIndex.dtos.UserMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper;
import org.springframework.kafka.support.mapping.Jackson2JavaTypeMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;

@Component
@KafkaListener(topics = {"users-table-topic.public.users","responses"}, groupId = "index-processor")
public class UserConsumer {
    private final UserService userService;
    private final ElasticsearchOperations elasticsearchOperations;
    private final ObjectMapper objectMapper;
    private final static Logger logger = LoggerFactory.getLogger(UserConsumer.class);

    public UserConsumer(
            UserService userService,
            ElasticsearchOperations elasticsearchOperations,
            ObjectMapper objectMapper
    ) {
        this.userService = userService;
        this.elasticsearchOperations = elasticsearchOperations;
        this.objectMapper = objectMapper;
    }

    @KafkaHandler(isDefault = true)
    public void consumeUser(LinkedHashMap<?, ?> userPayload) throws JsonProcessingException {
        logger.info("User payload: {}", objectMapper.writeValueAsString(userPayload));
        var payLoad = objectMapper
            .readValue(
                objectMapper.writeValueAsString(userPayload.get("payload")), PayLoad.class
            );
        var userData = payLoad.after();

        elasticsearchOperations.indexOps(UserDocument.class).refresh();

        switch (payLoad.op()) {
            case c -> userService.save(userData);
            case u -> userService.update(userData);
            case d -> {
                    userData = payLoad.before();
                    userService.delete(userData);
                }
            default -> logger.info("Operation not supported");
        }
    }

    @Bean
    public RecordMessageConverter converter(){
        var converter = new JsonMessageConverter();
        var typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.setTypePrecedence(Jackson2JavaTypeMapper.TypePrecedence.TYPE_ID);
        typeMapper.addTrustedPackages("com.techphile.userIndex.dtos");
        var mappings = new HashMap<String, Class<?>>();
        mappings.put("users-table-topic.public.users", LinkedHashMap.class);
        mappings.put("responses", UserMessage.class);
        typeMapper.setIdClassMapping(mappings);
        converter.setTypeMapper(typeMapper);
        return converter;
    }
}
