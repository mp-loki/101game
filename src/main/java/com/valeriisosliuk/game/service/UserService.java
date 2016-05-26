package com.valeriisosliuk.game.service;

import java.util.Collection;
import java.util.List;

import com.valeriisosliuk.game.dto.OnlineUserDto;
import com.valeriisosliuk.game.dto.PendingPlayerDto;
import com.valeriisosliuk.game.model.Game;
import com.valeriisosliuk.game.model.Player;

public interface UserService {

    String getCurrentUserName();

    List<OnlineUserDto> getLoggedInUsers();

    void setAvailable(Collection<Player> users);

    void setAvailable(String user);

    void setBusy(Collection<Player> users);

    List<PendingPlayerDto> getPendingPlayers(Game game);

    void addUser(String username);

    void removeUser(String username);

    List<String> getAvailabePlayers();

    boolean isUserAvailable(String username);

}
