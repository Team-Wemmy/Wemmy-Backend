package wemmy.service.benefit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import wemmy.domain.area.Regions;
import wemmy.domain.baby.BabyEntity;
import wemmy.domain.baby.constant.BabyType;
import wemmy.domain.user.UserEntity;
import wemmy.dto.benefit.BenefitDTO;
import wemmy.dto.benefit.BenefitSaveDTO;
import wemmy.service.area.AreaService;
import wemmy.service.baby.BabyService;
import wemmy.service.user.UserService;

import java.util.List;

import static wemmy.domain.baby.constant.BabyType.PREGNANCY;

@SpringBootTest
class BenefitSaveServiceTest {

    @Autowired
    private BenefitSaveService benefitSaveService;

    @Autowired
    private BenefitService benefitService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private UserService userService;

    @Autowired
    private BabyService babyService;

    @Test
    void benefitSave() {
        // resource/benefit에 있는 json파일 파싱
        try {
            List<BenefitSaveDTO> benefitSaveDTO = benefitSaveService.benefitSave();

            // 파싱한 결과를 데이터베이스에 저장
            benefitService.benefitParseAndSave(benefitSaveDTO);
        } catch (Exception e) {

        }
    }

    /**
     * 앱 홈화면 복지정보 테스트
     */
    @Test
    void benefitTitleListApp() {

        // 사용자 기본키로 거주하는 지역 및 임신/육아 여부 판별.
        UserEntity user = userService.findByUserId(2L);
        BabyEntity babyInfo = babyService.findBabyByUserId(user.getId());

        String city = "서울특별시";
        String district = "금천구";
        BabyType babyType = PREGNANCY;

        // 회원 정보에 있는 sigg_id를 통해 region code 조회.
        Regions region = areaService.getRegionBySiggCode(user.getSigg_id());
        // 정부 region code 조회.
        Regions government = areaService.getRegionById(494L);

        // region code로 복지(혜택)정보 조회.
        List<BenefitDTO.titleResponse> benefitList = benefitService.getBenefitTitleList(region, government, city, district, babyType);

        for (BenefitDTO.titleResponse titleResponse : benefitList) {
            System.out.println(titleResponse.getBenefitId());
            System.out.println(titleResponse.getTitle());
            System.out.println(titleResponse.getCity());
            System.out.println(titleResponse.getDistrict());
            System.out.println("scrap = false");
        }
    }


    /**
     * 웹 복지 리스트 테스트
     */
    @Test
    void benefitTitleListWeb() {
        Regions government = areaService.getRegionById(494L);
        benefitService.getBenefitTitleListWeb(government, "서울특별시", "");
    }

}