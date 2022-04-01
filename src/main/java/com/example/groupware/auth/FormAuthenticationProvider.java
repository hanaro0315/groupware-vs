package com.example.groupware.auth;

import com.example.groupware.entity.GroupDetail;
import com.example.groupware.service.GroupDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
//인증 로직 담당
public class FormAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private  GroupDetailService groupDetailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String email = authentication.getName();
        String password = (String) authentication.getCredentials();

        GroupDetail groupDetail = (GroupDetail) groupDetailService.loadUserByUsername(email);

        if(groupDetail == null || !email.equals(groupDetail.getUsername()) ||
                !passwordEncoder.matches(password, groupDetail.getPassword())){
            throw new BadCredentialsException(email);
        }
        else if(!groupDetail.isAccountNonLocked()){
            throw new LockedException(email);
        }
        else if(!groupDetail.isEnabled()){
            throw new DisabledException(email);
        }//비밀번호 만료
        else if(!groupDetail.isCredentialsNonExpired()){
            throw new CredentialsExpiredException(email);
        }

        // 최종 인증정보 --> Security 본체에 전달
        Authentication newAuth = new UsernamePasswordAuthenticationToken(groupDetail, null, groupDetail.getAuthorities());

        System.out.println("simai");
        return newAuth;
    }

    //위의 authenticate 메소드에서 반환한 객체가 유효한 타입이 맞는지 검사
    // null 이거나 잘못된 타입을 반환하였을 때 인증 실패로 간주
    // false로 그냥 돌리면 꽤나 고생함..;
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
