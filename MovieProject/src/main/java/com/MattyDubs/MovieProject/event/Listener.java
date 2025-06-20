package com.MattyDubs.MovieProject.event;

import com.MattyDubs.MovieProject.entity.CustomUser;
import com.MattyDubs.MovieProject.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Listener {

    private final EmailSender emailSender;

    @Autowired
    public Listener(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @EventListener
    public void onRegistration(RegistrationEvent event) {
        CustomUser user = event.getUser();
        try {
            emailSender.sendEmail(user.getEmail(), "Welcome!", "Welcome to the movie application, " + user.getUsername() + "!");
        } catch (MailSendException e) {
            System.out.println("Error sending email to: " + user.getEmail() + " " + e.getMessage());
        }
    }

    @EventListener
    public void sendMovieList(MovieEmailRequestEvent event) {
        CustomUser user = event.getUser();
        List<Movie> movies = event.getMovies();
        String str = "";
        for (Movie movie : movies) {
            str += movie.getTitle() + "\n";
        }
        try {
            emailSender.sendEmail(user.getEmail(), "Movie-List Request", str);
        } catch (MailSendException e) {
            System.out.println("Error sending email to: " + user.getEmail() + " " + e.getMessage());
        }
    }
}
