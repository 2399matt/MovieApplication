package com.MattyDubs.MovieProject.event;

import com.MattyDubs.MovieProject.entity.CustomUser;

public class RegistrationEvent {

    private final CustomUser user;

    public RegistrationEvent(CustomUser user) {
        this.user = user;
    }

    public CustomUser getUser() {
        return user;
    }
}
