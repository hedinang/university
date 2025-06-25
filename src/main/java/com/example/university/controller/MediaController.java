package com.example.university.controller;

import com.example.university.service.ResourceService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/media")
@RequiredArgsConstructor
@Slf4j
public class MediaController {
    private final ResourceService resourceService;

    @GetMapping("/{resource}")
    public ResponseEntity<?> getFile(@PathVariable String resource) {
        return resourceService.getResource(resource);
    }

//    @GetMapping("/video/{scope}/{uuid}/**")
//    public ResponseEntity<InputStreamResource> streamVideo(HttpServletRequest request, @PathVariable String scope,
//                                                           @PathVariable String uuid, @RequestParam String token,
//                                                           @RequestHeader(value = "Range", required = false) String rangeHeader) {
//        String userId = tokenProvider.extractUserIdFromToken(token);
//
//        if (userId == null || userId.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        if (Objects.equals(scope, UrlPathType.conversation.toString())) {
//            return resourceService.streamInConversation(rangeHeader, uuid, userId, request.getRequestURL().toString());
//        }
//
//        if (Objects.equals(scope, UrlPathType.user.toString())) {
//            return resourceService.getAvatar(uuid, request.getRequestURL().toString());
//        }
//
//        if (Objects.equals(scope, UrlPathType.task.toString())) {
//            return resourceService.streamInComment(rangeHeader, uuid, userId, request.getRequestURL().toString());
//        }
//
//        return ResponseEntity.notFound().build();
//    }
}
