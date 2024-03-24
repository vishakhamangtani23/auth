package com.vishakha.auth.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Repository
public class UserRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;
    public int registerUser(String fullname,String username,String email, String password, String mobile)
    {
        return jdbcTemplate.update("EXEC sp_register_user ?,?,?,?,?",username ,
                password,
                email ,
                fullname ,
                mobile
                 );
    }
    public Map<String, Object> loginUser(String username, String password) {
        System.out.println(jdbcTemplate.queryForMap("EXEC sp_login_user ? , ? ", username, password));
        return jdbcTemplate.queryForMap("EXEC sp_login_user ? , ? ", username, password);
    }
    public Map<String, Object> isValidToken(Integer userid, String token) {
        return jdbcTemplate.queryForMap("EXEC sp_validate_token ? , ? ", userid, token);
    }
    public Map<String,Object> validateEmail(String email) {
        return jdbcTemplate.queryForMap("EXEC sp_validate_email ?", email);
    }
    public Map<String , Object > generateToken(String email) {
        return jdbcTemplate.queryForMap("EXEC sp_generate_token ?  ", email);
    }
    public Map<String,Object> validateFPToken( String token) {
        return jdbcTemplate.queryForMap("EXEC sp_validate_fp_token ? ",  token);
    }
}
