package com.MattyDubs.MovieProject.event;

import com.MattyDubs.MovieProject.entity.CustomUser;
import com.MattyDubs.MovieProject.entity.UserMovies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class Listener {

    private final EmailSender emailSender;

    @Value("${APP_PORT}")
    private String url;

    @Autowired
    public Listener(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @EventListener
    public void onRegistration(RegistrationEvent event) {
        CompletableFuture.runAsync(() -> {
            try {
                CustomUser user = event.user();
                try {
                    emailSender.sendEmail(user.getEmail(), "Welcome!", "Welcome to the movie application, " + user.getUsername() + "!\n Please click" +
                            "the link below to verify your email: \n" +
                            "http://" + url + ":8080/register/verify?token=" + user.getVerificationToken());
                } catch (MailSendException e) {
                    System.out.println("Error sending email to: " + user.getEmail() + " " + e.getMessage());
                }
            } catch (Exception e) {
                System.out.println("Mail error: " + e.getMessage());
            }
        });
    }

    @EventListener
    public void sendMovieList(MovieEmailRequestEvent event) {
        CompletableFuture.runAsync(() -> {
            try {
                CustomUser user = event.user();
                List<UserMovies> movies = event.movies();
                String str = "";
                for (UserMovies movie : movies) {
                    str += movie.getMovie().getTitle() + "\n";
                }
                try {
                    emailSender.sendEmail(user.getEmail(), "Movie-List Request", str);
                } catch (MailSendException e) {
                    System.out.println("Error sending email to: " + user.getEmail() + " " + e.getMessage());
                }
            } catch (Exception e) {
                System.out.println("Email error: " + e.getMessage());
            }
        });
    }
}
