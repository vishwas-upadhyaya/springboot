package com.productservice.userservice.services;

import ch.qos.logback.core.testUtil.RandomUtil;
import com.productservice.userservice.dtos.UserDto;
import com.productservice.userservice.dtos.ValidateTokenRequestDto;
import com.productservice.userservice.models.Role;
import com.productservice.userservice.models.Session;
import com.productservice.userservice.models.SessionStatus;
import com.productservice.userservice.models.User;
import com.productservice.userservice.repositories.SessionRepository;
import com.productservice.userservice.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;
import io.jsonwebtoken.security.SignatureAlgorithm;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class AuthService {

    UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    AuthService(UserRepository userRepository,
                SessionRepository sessionRepository,BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public ResponseEntity<UserDto> login(String email,String password){
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()){
            return  null;
        }
        User user = userOptional.get();

        if(!bCryptPasswordEncoder.matches(password,user.getPassword())){
            return null;
        }

//        String token = RandomStringUtils.randomAlphanumeric(30);

        MacAlgorithm alg = Jwts.SIG.HS256; //or HS384 or HS256
//        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.

//        SecretKey key = alg.key().build();
        String secretString = "vishwasisagoodpersonvishwasisagoodpersonvishwasisagoodperson";
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretString));
//        String key = "vishwas";
        System.out.println("-------------------- key ----------------------");
        System.out.println(key);
//        System.out.println(key.getEncoded()); // [B@c44e779

        Map<String,Object> jsonMap = new HashMap<>();
        jsonMap.put("email",email);
        jsonMap.put("roles",user.getRoles());
        jsonMap.put("createdAt", new Date());
        jsonMap.put("expireAt", DateUtils.addDays(new Date(),30));

        String message = "Hello World!";
        byte[] content = message.getBytes(StandardCharsets.UTF_8);

//      Create the compact JWS:
//        String jws = Jwts.builder().content(content, "text/plain").signWith(key, alg).compact();
        String jws = Jwts.builder().claims(jsonMap).signWith(key,alg).compact();

//      Parse the compact JWS:
//        content = Jwts.parser().verifyWith(key).build().parseSignedContent(jws).getPayload();

//        assert message.equals(new String(content, StandardCharsets.UTF_8));


        Session session = new Session();
        session.setUser(user);
        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setToken(jws);
        sessionRepository.save(session);

        UserDto userDto = new UserDto();
        userDto.setEmail(email);
        MultiValueMapAdapter<String,String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add(HttpHeaders.SET_COOKIE,"auth-token:"+jws);
        ResponseEntity<UserDto> responseEntity = new ResponseEntity<>(userDto,headers, HttpStatus.OK);
        return responseEntity;
    }

    public UserDto signup(String email,String password){
        User user = new User();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        User savedUser = userRepository.save(user);
        return UserDto.from(savedUser);
    }

    public ResponseEntity<Void> logout(String token,Long userId){
        Optional<Session> optionalSession = sessionRepository.findByTokenAndUser_Id(token,userId);
        if(optionalSession.isEmpty()){
            return null;
        }

        Session session = optionalSession.get();
        session.setSessionStatus(SessionStatus.ENDED);
        sessionRepository.save(session);
        return ResponseEntity.ok().build();
    }

    public SessionStatus validate(String token,Long userId) throws Exception {

        String secretString = "vishwasisagoodpersonvishwasisagoodpersonvishwasisagoodperson";
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretString));

        try{
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);

            Jws<Claims> obj = Jwts.parser().build().parseSignedClaims(token);
            String email = (String) obj.getPayload().get("email");
            List<Role> roles = (List<Role>) obj.getPayload().get("roles");
            Date expireAt = (Date) obj.getPayload().get("expireAt");
            Date createdAt = (Date) obj.getPayload().get("createdAt");





            return SessionStatus.ACTIVE;
        }catch (Exception e){
            throw new Exception("not verified");
        }




//        Optional<Session> optionalSession = sessionRepository.findByTokenAndUser_Id(token,userId);
//        if(optionalSession.isEmpty()){
//            return null;
//        }
//        return SessionStatus.ACTIVE;
    }

}
