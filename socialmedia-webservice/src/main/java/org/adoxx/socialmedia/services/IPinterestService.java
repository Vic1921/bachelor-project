package org.adoxx.socialmedia.services;

public interface IPinterestService {
    void getBoards();
    void getPins();
    void getPin();
    void getPinComments();
    void postPin();

    void init(String accessToken);
}
