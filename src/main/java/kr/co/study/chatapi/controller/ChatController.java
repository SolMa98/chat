package kr.co.study.chatapi.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/chat")
public class ChatController {
    @GetMapping
    public String chatPageOpen(HttpServletRequest request){
        return "chat/chat";
    }

    @GetMapping("/dashboard")
    public String chatDashBoard(HttpServletRequest request) { return "chat/chat-dashboard"; }

    @PostMapping("/upload/file")
    @ResponseBody
    public ResponseEntity<Map<String, String>> uploadFile(MultipartFile file) {
        try {
            String originalFileName = file.getOriginalFilename();
            String fileName = UUID.randomUUID().toString() + "_" + originalFileName;
            Path filePath = Paths.get("F:/study/image/" + fileName);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            String fileUrl = "/files/" + fileName;

            Map<String, String> response = new HashMap<>();
            response.put("fileUrl", fileUrl);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
