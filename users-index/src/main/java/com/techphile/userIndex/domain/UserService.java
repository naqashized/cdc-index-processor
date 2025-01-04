package com.techphile.userIndex.domain;

import com.techphile.userIndex.dtos.UserMessage;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserDocumentRepository userDocumentRepository;

    public UserService(UserDocumentRepository userDocumentRepository) {
        this.userDocumentRepository = userDocumentRepository;
    }

    public void save(UserMessage userMessage) {
        var userDocument = new UserDocument(
                userMessage.id(),
                userMessage.name(),
                userMessage.email(),
                userMessage.phone()
            );

        userDocumentRepository.findById(userMessage.id())
        .ifPresentOrElse(
            existingUserDocument -> {
                existingUserDocument.setName(userDocument.getName());
                existingUserDocument.setEmail(userDocument.getEmail());
                existingUserDocument.setPhone(userDocument.getPhone());
                userDocumentRepository.save(existingUserDocument);
            },
            () -> userDocumentRepository.save(userDocument)
        );
    }
}
