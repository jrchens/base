package cn.jrry.common.service.impl;

import cn.jrry.common.exception.UserNotFoundException;
import cn.jrry.common.exception.UserPasswordErrorException;
import cn.jrry.common.exception.UserStateException;
import cn.jrry.common.service.LoginService;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Map<String, Object> login(String username, String password) throws RuntimeException {
        List<Map<String, Object>> data = jdbcTemplate.queryForList("select * from sys_user where deleted = 0 and username = ?", username);
        if (data.isEmpty()) {
            throw new UserNotFoundException(); // username.not.exists
        }
        Map<String, Object> map = data.get(0);
        String salt = ObjectUtils.getDisplayString(map.get("password_salt"));

        SecretKey key = new SecretKeySpec(salt.getBytes(Charsets.UTF_8), "HmacSHA512");
        String pwd = Hashing.hmacSha512(key).hashBytes(password.getBytes(Charsets.UTF_8)).toString();

        if (!pwd.equals(ObjectUtils.getDisplayString(map.get("password")))) {
            // password error
            throw new UserPasswordErrorException();

        }

        String locked = ObjectUtils.getDisplayString(map.get("locked"));
        String disabled = ObjectUtils.getDisplayString(map.get("disabled"));
        if ("true".equals(locked)) {
            // locked
            throw new UserStateException("locked");
        }
        if ("true".equals(disabled)) {
            // disabled
            throw new UserStateException("disabled");
        }

        return map;
    }
}
