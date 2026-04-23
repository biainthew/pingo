package aib.pingo.service;

import aib.pingo.domain.entity.Member;
import aib.pingo.domain.enums.MemberRole;
import aib.pingo.dto.request.MemberRequestDTO;
import aib.pingo.dto.response.MemberResponseDto;
import aib.pingo.exception.CustomException;
import aib.pingo.exception.ErrorCode;
import aib.pingo.repository.MemberRepository;
import aib.pingo.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;

    /**
     * 회원가입
     * @param request MemberRequestDTO
     * @return Member
     */
    public MemberResponseDto.Info signUp(MemberRequestDTO.SignUp request) {
        // 이메일 중복 확인
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new CustomException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        Member member = Member.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .role(MemberRole.ROLE_USER)
                .build();

        return new MemberResponseDto.Info(memberRepository.save(member));
    }

    public MemberResponseDto.Token login(MemberRequestDTO.Login request) {
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow();

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        String accessToken = jwtTokenProvider.createAccessToken(member.getEmail(), member.getRole().name());
        String refreshToken  = jwtTokenProvider.createRefreshToken(member.getEmail(), member.getRole().name());

        // Refresh Token Redis에 저장
        refreshTokenService.save(member.getEmail(), refreshToken);

        return new MemberResponseDto.Token(accessToken, refreshToken);
    }

    public MemberResponseDto.AccessToken refresh(String refreshToken) {
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        String email = jwtTokenProvider.getEmail(refreshToken);
        String role = jwtTokenProvider.getRole(refreshToken);

        if (!refreshTokenService.isValid(email, refreshToken)) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        String newAccessToken = jwtTokenProvider.createAccessToken(email, role);

        return new MemberResponseDto.AccessToken(newAccessToken);
    }
}
