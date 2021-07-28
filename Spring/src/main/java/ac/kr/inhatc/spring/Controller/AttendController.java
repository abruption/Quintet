package ac.kr.inhatc.spring.Controller;

import ac.kr.inhatc.spring.Service.AttendService;
import ac.kr.inhatc.spring.Service.MemberService;
import ac.kr.inhatc.spring.Service.PointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class AttendController {
    @Autowired
    MemberService memberService;        // MemberService 의존성 주입

    @Autowired
    PointService pointService;          // PointService 의존성 주입

    @Autowired
    AttendService attendService;    // AttendanceService 의존성 주입

    // SLF4J Logger 사용
    private static final Logger Log = LoggerFactory.getLogger(MemberService.class);

    // 출석여부를 판별하는 페이지 Mapping
    @ResponseBody
    @RequestMapping(value = "/Check_Attendance", method = RequestMethod.POST)
    public String Check_Attendance(String id) throws Exception {
        Log.info("Check_Attendance() 진입");
        int result = attendService.attCheck(id);

        if(result == 0)
            return "Success";
        else
            return "Fail";
    }

    // 출석여부를 확인 후, 출석체크와 포인트 지급을 담당
    @ResponseBody
    @RequestMapping(value = "/Attendance", method = RequestMethod.POST)
    public String Attendance(String id) throws Exception {
        Log.info("Attendance() 진입");
        attendService.Attendance(id);
        pointService.attendPoint(id);
        int count = attendService.checkAttend(id);

        if(count == 3){
            // 최근 3일(당일 포함) 출석 일수가 3일인 경우
            count = pointService.checkBonus(id);
            if(count != 1){
                // 지급 받은 경우 count는 1
                // 최근 3일(당일 포함) 출석체크 추가 포인트를 지급받지 않은 경우에만 지급처리.
                pointService.BonusPoint(id);
                return "Bonus";
            }
        }
        return "None";
    }

    // 전체회원의 출석현황을 조회하는 페이지 Mapping
    @RequestMapping("/attend/searchAttend.do")
    public ModelAndView searchAttend() throws Exception {
        ModelAndView mv = new ModelAndView();
        List<?> PointRanking = pointService.PointRanking();
        List<?> MemberList = memberService.MemberList();
        List<?> AttendCount = attendService.AttendCount();
        mv.addObject("PointRanking", PointRanking);
        mv.addObject("MemberList", MemberList);
        mv.addObject("AttendCount", AttendCount);
        mv.setViewName("attend/searchAttend");
        return mv;
    }

    // 선택한 회원의 출석정보를 조회하는 페이지 Mapping
    @RequestMapping("/attend/detailAttend.do")
    public ModelAndView detailAttend(HttpServletRequest request) throws Exception {
        String id = request.getParameter("id");
        List<?> detailAttend = attendService.detailAttend(id);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("attend/detailAttend");
        mv.addObject("detailAttend", detailAttend);
        return mv;
    }
}
