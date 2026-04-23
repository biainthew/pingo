package aib.pingo.controller;

import aib.pingo.dto.request.MemberRequestDTO;
import aib.pingo.dto.response.MemberResponseDto;
import aib.pingo.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthContoller {

    private final MemberService memberService;

    /**
     * 회원가입
     * @param request MemberRequestDTO.SignUp
     * @return ResponseEntity<MemberResponseDto.Info>
     */
    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto.Info> signUp(@Valid @RequestBody MemberRequestDTO.SignUp request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.signUp(request));
    }

    /**
     * 로그인
     * @param request MemberRequestDTO.Login
     * @return MemberResponseDto.Token
     */
    @PostMapping("login")
    public ResponseEntity<MemberResponseDto.Token> login(@Valid @RequestBody MemberRequestDTO.Login request) {
        return ResponseEntity.ok(memberService.login(request));
    }
}
