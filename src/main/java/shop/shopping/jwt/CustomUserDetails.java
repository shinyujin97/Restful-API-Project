package shop.shopping.jwt;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import shop.shopping.entity.Member;

import java.util.ArrayList;
import java.util.Collection;

@Getter
public class CustomUserDetails implements UserDetails {

    private  Member member;

    public CustomUserDetails(Member member){

        this.member = member;
    }

    // user 권한 가져오기
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { //유저에게 부여된 권한들 반환
        ArrayList<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
        auth.add(new SimpleGrantedAuthority(member.getAuthority()));
        return auth;
    }

    @Override
    public String getPassword() {

        return member.getPassword();
    }

    @Override
    public String getUsername() {

        return member.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {

        return true;
    } //계정 만료 여부

    @Override
    public boolean isAccountNonLocked() {

        return true;
    } //유저 사용 가능 여부

    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    } //credentials(password) 만료 여부

    @Override
    public boolean isEnabled() {

        return true;
    } //유저 사용 가능 여부


}
