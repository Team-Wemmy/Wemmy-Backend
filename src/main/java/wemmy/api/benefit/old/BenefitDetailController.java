package wemmy.api.benefit.old;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wemmy.domain.user.UserEntity;
import wemmy.dto.welfare.benefit.BenefitDTO;
import wemmy.global.token.jwt.GetUserIDByToken;
import wemmy.service.benefit.BenefitService;
import wemmy.service.scrap.ScrapService;
import wemmy.service.user.UserService;


//@Tag(name = "Benefit", description = "복지 정보 API")
@Slf4j
@RestController
@RequiredArgsConstructor
//@RequestMapping("/wemmy/benefit")
public class BenefitDetailController {

    private final BenefitService benefitService;
    private final UserService userService;
    private final ScrapService scrapService;
    private final GetUserIDByToken getUserIDByToken;

    /**
     * App
     * 복지 내용 상세조회
     */
    /*@Tag(name = "Benefit")
    @Operation(summary = "앱 복지 상세조회 API", description = "accessToken필요, benefitId에 해당하는 상세 복지정보 응답.")
    @GetMapping("/detail/{id}")*/
    public ResponseEntity<BenefitDTO.response> getBenefitDetail(@PathVariable("id") Long id,
                                                                HttpServletRequest httpServletRequest) {

        // 사용자 기본키로 거주하는 지역 및 임신/육아 여부 판별.
        Long userID = getUserIDByToken.getUserID(httpServletRequest);
        UserEntity user = userService.findByUserId(userID);

        // 사용자 정보와 복지 정보 기본키로 스크랩 여부 조회.
        String scrap = scrapService.findScrap(user, id);

        System.out.println(scrap);

        // region code로 복지(혜택)정보 조회.
        BenefitDTO.response benefitDetail = benefitService.getBenefitDetail(id, scrap);
        return new ResponseEntity<>(benefitDetail, HttpStatus.OK);
    }

    /**
     * 웹 복지 내용 상세조회
     */
    /*@Tag(name = "Benefit")
    @Operation(summary = "웹 복지 상세조회 API", description = "benefitId에 해당하는 상세 복지정보 응답.")
    @GetMapping("/web/detail")*/
    public ResponseEntity<BenefitDTO.response> getBenefitDetailWeb(@RequestParam("id") Long id,
                                                                   HttpServletRequest httpServletRequest) {

        log.info("request url : " + httpServletRequest.getRequestURI());
        log.info("request user-agent : " + httpServletRequest.getHeader("user-agent"));

        // region code로 복지(혜택)정보 조회.
        BenefitDTO.response benefitDetail = benefitService.getWebBenefitDetail(id);
        return new ResponseEntity<>(benefitDetail, HttpStatus.OK);
    }
}
