package websocket;

import com.project.blog.entities.User;
import com.project.blog.repositories.UserRepository;
import com.project.blog.responses.ErrorSuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class WebSocketController {


    private final SimpMessagingTemplate simpMessagingTemplate;
    private final UserRepository userRepository;

    @MessageMapping("/message")
    private ErrorSuccessResponse receiveMessage(String user){
        ErrorSuccessResponse errorSuccessResponse = new ErrorSuccessResponse();
        errorSuccessResponse.setError("block oldu");
    simpMessagingTemplate.convertAndSendToUser(user,"/private",errorSuccessResponse);
    return errorSuccessResponse;
    }
    @Scheduled(fixedDelay = 1000)
    private void checkBlocks(){
        LocalDateTime now = LocalDateTime.now();
        List<User> users = userRepository.findBlockedUsers(now);

        users.stream().forEach(user->{
            receiveMessage(user.getUsername());
        });
    }
}
