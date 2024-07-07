package org.adoxx.socialmedia.services;

import com.chrisdempewolf.pinterest.Pinterest;
import com.chrisdempewolf.pinterest.responses.user.User;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.adoxx.socialmedia.exceptions.PinterestInitializationException;
import org.springframework.stereotype.Service;

@Getter
@Service
@Slf4j
public class PinterestServiceImpl implements IPinterestService {

    private Pinterest pinterestClient;

    public void init(String accessToken) {
        try {
            this.pinterestClient = new Pinterest(accessToken);
            log.info("Pinterest client initialized successfully.");
        } catch (Exception e) {
            log.error("Error initializing Pinterest client: ", e);
            throw new PinterestInitializationException("Failed to initialize Pinterest client.");
        }
    }

    @Override
    public void getBoards() {

    }

    @Override
    public void getPins() {

    }

    @Override
    public void getPin() {

    }

    @Override
    public void getPinComments() {

    }

    @Override
    public void postPin() {

    }

}
