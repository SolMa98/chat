package kr.co.study.chatapi.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chat")
public class ChatController {
    @GetMapping
    public String chatPageOpen(HttpServletRequest request){
        return "chat/chat";
    }

    @GetMapping("/dashboard")
    public String chatDashBoard(HttpServletRequest request) { return "chat/chat-dashboard"; }
}
