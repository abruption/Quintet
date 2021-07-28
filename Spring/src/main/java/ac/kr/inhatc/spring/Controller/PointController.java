package ac.kr.inhatc.spring.Controller;

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
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class PointController {
    @Autowired
    PointService pointService;      // PointService 의존성 주입

    // SLF4J Logger 사용
    private static final Logger Log = LoggerFactory.getLogger(MemberService.class);

    // 포인트 적립 순위 조회를 위한 페이지 Mapping
    @RequestMapping("/point/pointRanking.do")
    public ModelAndView pointRanking() throws Exception {
        ModelAndView mv = new ModelAndView();
        List<?> PointRanking = pointService.PointRanking();
        mv.addObject("PointRanking", PointRanking);
        mv.setViewName("point/pointRanking");
        return mv;
    }

    // 회원정보 수정란의 가용포인트의 상세내역 조회 페이지 Mapping
    @RequestMapping("/point/detailPoint.do")
    public ModelAndView detailPoint(HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        HttpSession session = request.getSession();
        String id = request.getParameter("id");
        if(id != null){
            List<?> PointList = pointService.PointList(id);
            mv.addObject("PointList", PointList);
        } else {
            List<?> PointList = pointService.PointList(session.getAttribute("userId").toString());
            mv.addObject("PointList", PointList);
        }
        mv.setViewName("point/detailPoint");
        return mv;
    }

    // 회원가입 시, 웰컴 포인트와 수신동의 포인트 지급을 담당하는 페이지 (AJAX와 연동)
    @ResponseBody
    @RequestMapping(value = "/Point_Service", method = RequestMethod.POST)
    public void Point_Service(String id, String name, String phone, String email_agree, String sms_agree) throws Exception {
        String description = null;
        String type = null;
        int JoinPoint = 1000;
        int EmailPoint = 1000;
        int SmsPoint = 1000;
        Log.info("Point_Service() 진입");
        Log.info("ID 값 : " + id);
        Log.info("이름 값 : " + name);
        Log.info("전화번호 값 : " + phone);
        Log.info("이메일 동의 값 : " + email_agree);
        Log.info("SMS 동의 값 : " + sms_agree);

        if (email_agree.equals("동의") && sms_agree.equals("동의")) {
            description = "회원가입 축하 포인트 지급";
            type = "Join";
            pointService.JoinPoint(id, name, phone, JoinPoint, description, type);
            description = "이메일 수신동의 포인트 지급";
            type = "EMAIL";
            pointService.EmailPoint(id, name, phone, EmailPoint, description, type);
            description = "SMS 수신동의 포인트 지급";
            type = "SMS";
            pointService.SMSPoint(id, name, phone, SmsPoint, description, type);
        }else if(email_agree.equals("동의")){
            description = "회원가입 축하 포인트 지급";
            type = "Join";
            pointService.JoinPoint(id, name, phone, JoinPoint, description, type);
            description = "이메일 수신동의 포인트 지급";
            type = "EMAIL";
            pointService.EmailPoint(id, name, phone, EmailPoint, description, type);
        } else if (sms_agree.equals("동의")) {
            description = "회원가입 축하 포인트 지급";
            type = "Join";
            pointService.JoinPoint(id, name, phone, JoinPoint, description, type);
            description = "SMS 수신동의 포인트 지급";
            type = "SMS";
            pointService.SMSPoint(id, name, phone, SmsPoint, description, type);
        }  else {
            description = "회원가입 축하 포인트 지급";
            type = "Join";
            pointService.JoinPoint(id, name, phone, JoinPoint, description, type);
        }
    }
}
