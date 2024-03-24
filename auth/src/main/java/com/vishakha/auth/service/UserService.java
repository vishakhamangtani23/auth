package com.vishakha.auth.service;

import com.vishakha.auth.repository.UserRepository;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
//    @username VARCHAR(100),
//    @password VARBINARY (MAX),
//    @email VARCHAR(200) ,
//    @fullname VARCHAR(200),
//    @mobile VARCHAR(20)
    @Autowired
    UserRepository userRepository;
    @Autowired
    JavaMailSender javaMailSender;
    public ResponseEntity<Map<String,Object>> registerUser( Map<String,Object> body)
    {
        String username = (String)body.get("username");
        String password = (String)body.get("password");
        String email = (String)body.get("email");
        String fullname = (String) body.get("fullname");
        String mobile = (String) body.get("mobile");

        int noOfRows  = userRepository.registerUser(fullname,username,email,password,mobile);
        if(noOfRows >0)
        {
            return ResponseEntity.ok(Map.of("status","successful"));
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of("status","Unsuccessful"));

    }
    public Map<String,Object> loginUser( Map<String,Object> body)
    {
        String username = (String)body.get("username");
        String password = (String)body.get("password");
        return userRepository.loginUser(username,password);

//        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of("status","Unsuccessful"));
    }
    public ResponseEntity<List<String>> getStudentNames(HttpServletRequest httpServletRequest)
    {

        boolean isValid = isValidToken(httpServletRequest);
        System.out.println(isValid + " isValid");
        if(isValid) {
            return ResponseEntity.ok(List.of("Hii", "Hello", "Bye"));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.emptyList());
    }
    private Map<String,String > getCookiesAsHashMap(Cookie[] cookies)
    {
        Map<String , String> cookieMap = new HashMap<>();
        System.out.println(cookies);
        for(Cookie c : cookies)
        {
            cookieMap.put(c.getName() , c.getValue());
        }
        return  cookieMap;
    }
    public boolean isValidToken(HttpServletRequest httpServletRequest)
    {
        Cookie [] cookies = httpServletRequest.getCookies();
        if(cookies==null)
        {
            return false;
        }
        Map<String , String> cookieMap = getCookiesAsHashMap(cookies);

        Map<String , Object> result = userRepository.isValidToken(Integer.parseInt(cookieMap.get("userid")) , cookieMap.get("token"));
        Integer isValid = (Integer) result.get("validYN");
        return isValid == 1;
    }
    public void validateAndSendMail(Map<String , Object> body)
    {
        String email = (String) body.get("email");
        if(validateEmail(email))
        {
            String token = generateToken(email);
            String link = generateLink(token);
            sendMail(email , link );
        }
        else
        {
            System.out.println("Invalid Email");
        }
//        1 validateemail
//        2 token
//        3 send mail


    }
    public boolean validateEmail(String email)
    {
        Map<String , Object> res = userRepository.validateEmail(email);
        Integer isValid = (Integer) res.get("validYN");
        return isValid == 1;
    }
    public String generateToken(String email)
    {
        Map <String , Object >res = userRepository.generateToken(email);
        String token = (String) res.get("GeneratedToken");
        return token;
    }
    public String generateLink(String token)
    {
        return "http://localhost:4200/reset/"+token;
    }
    public void sendMail(String email , String link)
    {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        try
        {
            mimeMessageHelper.setSubject("forgot ");
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setText("Click on the link to reset your password "+link);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        javaMailSender.send(mimeMessage);
    }
    public ResponseEntity<Map<String,Object>> validateFPToken(Map<String,Object> body)
    {
        String token = (String) body.get("token");
        Map<String,Object> res = userRepository.validateFPToken(token);
        Integer isValid = (Integer) res.get("validYN");
        if(isValid == 1)
        {
            return ResponseEntity.ok(Map.of("status","successful"));
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of("status","Unsuccessful"));
    }



}
