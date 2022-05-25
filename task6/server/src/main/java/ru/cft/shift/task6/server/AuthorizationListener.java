package ru.cft.shift.task6.server;

import ru.cft.shift.task6.common.Request;

public interface AuthorizationListener {
    void checkUser(Request request);
}
