package ac.kr.inhatc.spring.Controller;

import ac.kr.inhatc.spring.DTO.MemberDTO;
import ac.kr.inhatc.spring.Service.AttendanceService;
import ac.kr.inhatc.spring.Service.MemberService;
import ac.kr.inhatc.spring.Service.PointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class JspController {
    @Autowired
    MemberService memberService;        // MemberService 의존성 주입

    @Autowired
    PointService pointService;          // PointService 의존성 주입

    @Autowired
    AttendanceService attendanceService;    // AttendanceService 의존성 주입

    // SLF4J Logger 사용
    private static final Logger Log = LoggerFactory.getLogger(MemberService.class);

    // 최상위 루트로 접근 시 /index.do로 Redirect
    @RequestMapping("/")
    public String Redirect() {
        return "redirect:index.do";
    }

    // 메인페이지 Mapping (로그인한 사용자의 ID 세션 값을 "userId"라는 메서드에 추가)
    @RequestMapping("/index.do")
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        mv.addObject("userId", request.getSession().getAttribute("userId"));
        return mv;
    }

    // 회원가입 여부를 판단하는 페이지 Mapping
    @RequestMapping("/member/checkMember.do")
    public ModelAndView checkMember() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/member/checkMember");
        return mv;
    }

    // 입력받은 전화번호 값이 DB에 존재하는지 연동체크 (AJAX와 연동)
    @ResponseBody
    @RequestMapping(value = "/Member_Check", method = RequestMethod.POST)
    public String Member_Check(String phone) throws Exception {
        Log.info("Member_Check() 진입");
        int result = memberService.memberCheck(phone);
        Log.info("결과 값 : " + result);

        if(result != 0)
            return "fail";
        else
            return "success";
    }

    // 회원가입 페이지 Mapping
    @RequestMapping("/member/JoinMember.do")
    public ModelAndView joinMember() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/member/joinMember");
        return mv;
    }

    // ID 중복 체크를 위한 DB 연동체크 (AJAX와 연동)
    @ResponseBody
    @RequestMapping(value = "/ID_Check", method = RequestMethod.POST)
    public String ID_Check(String id) throws Exception {
        Log.info("ID_Check() 진입");
        int result = memberService.idCheck(id);
        Log.info("결과 값 : " + result);

        if(result != 0)
            return "fail";
        else
            return "success";
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

    // 입력된 값을 넘겨받아 DB에 삽입하는 처리 페이지
    @RequestMapping("/member/processJoinMember.do")
    public ModelAndView processJoinMember(MemberDTO dto) throws Exception {
        ModelAndView mv = new ModelAndView();
        memberService.processJoinMember(dto);
        Log.info(String.valueOf(dto));
        mv.setViewName("/index");
        return mv;
    }

    // 로그인 페이지 Mapping
    @RequestMapping("/member/LoginMember.do")
    public ModelAndView LoginMember(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/member/loginMember");
        return mv;
    }

    // 입력한 아이디와 비밀번호가 일치하는지 DB에 연동 체크 (AJAX와 연동)
    // 로그인 성공 시, 사용자의 ID 값을 userId라는 세션에 추가
    @ResponseBody
    @RequestMapping(value = "/Login_Check", method = RequestMethod.POST)
    public String processLoginMember(String id, String password, HttpServletRequest request) throws Exception {
        Log.info("Member_Check() 진입");
        int result = memberService.loginCheck(id, password);
        Log.info("결과 값 : " + result);

        if(result == 0) {
            return "fail";
        }
        else {
            HttpSession session = request.getSession();
            session.setAttribute("userId", id);
            return "success";
        }
    }

    // 로그아웃 처리(세션 초기화) 및 메인 페이지로 Redirect를 담당하는 주소 Mapping
    @RequestMapping("/member/LogoutMember.do")
    public ModelAndView logoutMember(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
        return index(request);
    }

    // 세션의 ID 값 또는 파라미터로 전달받은 ID 값을 기반으로 회원정보와 포인트를 불러오는 회원정보 수정 페이지 Mapping
    @RequestMapping("/member/updateMember.do")
    public ModelAndView updateMember(HttpServletRequest request) throws Exception{
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/member/updateMember");

        String id = request.getParameter("id");
        HttpSession session = request.getSession();

        if(id != null) {
            MemberDTO dto = new MemberDTO();
            dto.setId(request.getParameter("id"));
            dto = memberService.processMemberInfo(dto);
            int point = pointService.TotalPoint(request.getParameter("id"));
            mv.addObject("member", dto);
            mv.addObject("TotalPoint", point);
        } else {
            MemberDTO dto = new MemberDTO();
            dto.setId(session.getAttribute("userId").toString());
            dto = memberService.processMemberInfo(dto);
            int point = pointService.TotalPoint(session.getAttribute("userId").toString());
            mv.addObject("member", dto);
            mv.addObject("TotalPoint", point);
        }
        return mv;
    }

    // 회원정보 수정을 담당하는 주소 Mapping
    @RequestMapping("/member/processUpdateMember.do")
    public ModelAndView processUpdateMember(HttpServletRequest request, MemberDTO dto) throws Exception {
        memberService.processUpdateMember(dto);
        return index(request);
    }

    // 회원탈퇴 시 세션 초기화 및 포인트 정보 삭제를 담당하는 주소 Mapping
    @RequestMapping("/member/deleteMember.do")
    public ModelAndView deleteMember(HttpServletRequest request) throws Exception {
        String id = request.getParameter("id");
        Log.info(id);
        memberService.processDeleteMember(id);
        pointService.processDeleteMember(id);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        HttpSession session = request.getSession();
        session.invalidate();
        return mv;
    }

    // 회원정보 수정란의 가용포인트의 상세내역 조회 페이지 Mapping
    @RequestMapping("/member/detailPoint.do")
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
        mv.setViewName("/member/detailPoint");
        return mv;
    }

    // 포인트 적립 순위 조회를 위한 페이지 Mapping
    @RequestMapping("/member/pointRanking.do")
    public ModelAndView pointRanking() throws Exception {
        ModelAndView mv = new ModelAndView();
        List<?> PointRanking = pointService.PointRanking();
        mv.addObject("PointRanking", PointRanking);
        mv.setViewName("/member/pointRanking");
        return mv;
    }

    // 관리자용 회원조회를 위한 페이지 Mapping
    @RequestMapping("/member/searchMember.do")
    public ModelAndView searchMember() throws Exception {
        ModelAndView mv = new ModelAndView();
        List<?> PointRanking = pointService.PointRanking();
        List<?> MemberList = memberService.MemberList();
        mv.addObject("PointRanking", PointRanking);
        mv.addObject("MemberList", MemberList);
        mv.setViewName("/member/searchMember");
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/Check_Attendance", method = RequestMethod.POST)
    public String Check_Attendance(String id) throws Exception {
        Log.info("Check_Attendance() 진입");
        int result = attendanceService.attCheck(id);

        if(result == 0)
            return "Success";
        else
            return "Fail";
    }

    @ResponseBody
    @RequestMapping(value = "/Attendance", method = RequestMethod.POST)
    public String Attendance(String id) throws Exception {
        Log.info("Attendance() 진입");
        attendanceService.Attendance(id);
        pointService.attendPoint(id);
        int result = pointService.CheckAttend(id);
        //Log.info(String.valueOf(result));
        if(result == 3){
            pointService.BonusPoint(id);
            return "Bonus";
        } else {
            return "None";
        }
    }

    @RequestMapping("/member/searchAttend.do")
    public ModelAndView searchAttend() throws Exception {
        ModelAndView mv = new ModelAndView();
        List<?> PointRanking = pointService.PointRanking();
        List<?> MemberList = memberService.MemberList();
        List<?> AttendCount = attendanceService.AttendCount();
        mv.addObject("PointRanking", PointRanking);
        mv.addObject("MemberList", MemberList);
        mv.addObject("AttendCount", AttendCount);
        mv.setViewName("/member/searchAttend");
        return mv;
    }
}
