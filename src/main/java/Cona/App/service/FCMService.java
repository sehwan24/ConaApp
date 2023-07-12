package Cona.App.service;

import Cona.App.controller.dto.FCMMessageDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FCMService {
    private String API_URL = "https://fcm.googleapis.com/v1/projects/cona-9d535/messages:send";
    private final ObjectMapper objectMapper;

    public void sendMessageTo(String targetToken, String title, String body) throws IOException {  //일단 title, body만, 이미지 없이
        String message = makeMessage(targetToken, title, body);

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        Response response = client.newCall(request).execute();

        log.info(response.body().string());
    }

//    @Scheduled(cron = "* * * * * ?")    //msg 스케줄링
    public void scheduledAlarm() throws FirebaseMessagingException, IOException {
        log.info("Test");
        sendMessageTo("eoMUbvsBR-eroyIjYWj48R:APA91bFFJCFp3Q_Qifel4VCIZDwN6WjEMpsf-rmhY6LFXqze_J5TmvJeIBzX90yIK7ZlAw225cnno3doylQjLO-ljMQd6sj6jFUIXyz6w5WvfTJbTM99VjO9H7lQ6r4sA9X2dBchIz4V", "Test", "Test");
    }

    private String makeMessage(String targetToken, String title, String body) throws JsonProcessingException {
        FCMMessageDto fcmMessageDto = FCMMessageDto.builder()
                .message(FCMMessageDto.Message.builder()
                        .token(targetToken)
                        .notification(FCMMessageDto.Notification.builder()
                                .title(title)
                                .body(body)
                                .image(null)
                                .build()
                        )
                        .build()
                )
                .validateOnly(false)
                .build();
        return objectMapper.writeValueAsString(fcmMessageDto);
    }

    private String getAccessToken() throws IOException {
        String firebaseConfigPath = "firebase/cona-9d535-firebase-adminsdk-gm35g-a55eb5f575.json";
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));
        googleCredentials.refreshIfExpired();
        System.out.println("googleCredentials.getAccessToken().getTokenValue() = " + googleCredentials.getAccessToken().getTokenValue());
        return googleCredentials.getAccessToken().getTokenValue();
    }
}
