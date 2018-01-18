package cn.jrry.common.service;

import java.util.Map;

public interface LoginService {
    public Map<String,Object> login(String username,String password) throws RuntimeException;
}
