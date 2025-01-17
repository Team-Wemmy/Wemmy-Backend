package wemmy.api.baby;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wemmy.dto.baby.BabyRespDTO;
import wemmy.global.token.jwt.GetUserIDByToken;
import wemmy.service.baby.BabyService;

import java.util.List;

//@Tag(name = "Baby", description = "아기 관련 API")
@RestController
@RequiredArgsConstructor
//@RequestMapping("/wemmy/baby")
public class BabyListController {

    private final BabyService babyService;
    private final GetUserIDByToken getUserIDByToken;

    @Tag(name = "Baby")
    @Operation(summary = "아기정보 조회 API", description = "마이페이지 등 등록된 아기정보 요청.")
    //@GetMapping("/get")
    public ResponseEntity<List<BabyRespDTO>> babyList(HttpServletRequest httpServletRequest) {

        Long userId = getUserIDByToken.getUserID(httpServletRequest);
        List<BabyRespDTO> result = babyService.babyList(userId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
