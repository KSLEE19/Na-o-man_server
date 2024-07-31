package com.umc.naoman.domain.member.controller;

import com.umc.naoman.domain.member.converter.MemberConverter;
import com.umc.naoman.domain.member.dto.MemberResponse;
import com.umc.naoman.domain.member.entity.Member;
import com.umc.naoman.domain.member.service.MemberService;
import com.umc.naoman.global.error.ErrorResponse;
import com.umc.naoman.global.result.ResultResponse;
import com.umc.naoman.global.result.code.MemberResultCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
@Tag(name = "회원 API", description = "회원 도메인의 API입니다.")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;


    @GetMapping("/{memberId}") // memberId를 사용해 특정 회원 정보 조회
    @Operation( summary = "특정 회원 정보 조회 API", description = "PathVariable]\n memberId\nrequest]\n" +
            "response]\n uesrname, email, 소셜 프로필 이미지 url")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse
                    (responseCode = "SM005", description = "특정 회원 정보 조회 성공."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse
                    (responseCode = "EM001", description = "해당 memberId를 가진 회원이 존재하지 않습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    public ResultResponse<MemberResponse.MemberInfo> getMemberInfo(@PathVariable(name = "memberId") Long memberId) {
        Member member = memberService.findMember(memberId);
        return ResultResponse.of(MemberResultCode.MEMBER_INFO,
                MemberConverter.toMemberInfo(member));
    }

    @GetMapping("/terms/{memberId}")
    @Operation(summary = "마케팅 약관 동의 여부 조회 API", description = "PathVariable]\n memberId\nrequest]\n" +
            "response]\n 마케팅 동의 여부 -> 동의 => true, 비동의 => false")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse
                    (responseCode = "SM006", description = "마케팅 약관 동의 여부 조회 성공."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse
                    (responseCode = "EM001", description = "해당 memberId를 가진 회원이 존재하지 않습니다.",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    public ResultResponse<MemberResponse.MarketingAgreed> getMarketingAgreed(@PathVariable(name = "memberId") Long memberId) {
        Member member = memberService.findMember(memberId);
        return ResultResponse.of(MemberResultCode.CHECK_MARKETING_AGREED, MemberConverter.toMarketingAgreed(member));
    }
}
