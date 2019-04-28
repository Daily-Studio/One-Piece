package org.dailystudio.onepiece.api;

import lombok.extern.slf4j.Slf4j;
import org.dailystudio.onepiece.dto.account.AccountLoginReqDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/account")
@Slf4j
public class AccountController {

    @PostMapping("/login")
    public void login(@RequestBody AccountLoginReqDto loginReqDto) {
    }
}

//우리가 구현해야할 내용들
//1. 요청을 받아 처리할 필터
//2. Manager에 등록할 Auth Provider
//3. 인증 정보를 담을 DTO객체들
//4. 인증 성공/실패 핸들러
//5. 인증 성공/실패시 사용할 Authentication 객체


//인증의 순서를 따라가 보면
//1.요청이 들어오면 요청의 엔드포인트에 해당하는 필터를 거치게된다!
//2.이때 인증에 필요한 정보를 우리가 UsernamePasswordAuthenticationToken 라는 Token을 이용해서 하는데
//2-1. 인증 전,후 로 Token이 필요하므로 우리는 두개의 Token을 작성한다.
//      전,후는 각각 인증 요청객체, 인증 완료객체 이다.
//      이러한 Token은 Pre, Post로 구분하도록하자
//      왜냐하면 UsernamePasswordAuthenticationToken은 기본적으로 인증 전,후를
//      생성자의 파라미터 갯수로 구분하기 때문에 명시적으로 나타내기 위해서이다.
//3.유저 정보를 인증과정에서 처리하는 방식으로 Spring Security에서 제공하는
//      UserDetails를 상속받은 User객체를 이용한다. --> AccountContext, AccountContextService
//3-1. 로그인을 하기위해 요청받은 값을 Pre~Token으로 만들고 아이디를 검색하는 값을 가져와서
//      AccountContextService 에서 Account를 찾고
//      찾은 Account를 기반으로 비밀번호 확인과 같은 인증 절차를 거쳐 AccountContext를 만든다.
//3-2. 만들어진 AccountContext는 Post~Token이라 여기면 된다.
//3-3. 위 3의 과정은 Provider내부에서 행해진다.
//4. 정상적으로 인증이 이루어졌고 인증이 성공했다면
//      SuccessHandler에서 Post~Token을 받는다.
//      이제 인증이 완료되 객체를 이용하여 JWT를 만들어 반환해주면 된다.
//      실패하면 FailureHandler에서 실패 처리를 해주면 된다.
//4-1.  만들어진 Handler들은 SecurityConfig에서 해당 Filter에서 추가시켜주면 사용할 수 있다.
