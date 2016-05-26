package com.valeriisosliuk.game.service;

import java.util.List;

public interface MessageService {

    <T> void send(String player, T t);

    <T> void sendToAll(List<String> players, T t);

}
