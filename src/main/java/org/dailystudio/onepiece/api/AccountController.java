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
