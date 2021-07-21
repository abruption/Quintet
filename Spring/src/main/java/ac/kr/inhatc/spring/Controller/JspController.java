package ac.kr.inhatc.spring.Controller;

import ac.kr.inhatc.spring.DTO.memberDto;
import ac.kr.inhatc.spring.Service.MemberService;
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

@Controller
public class JspController {
    @Autowired
    MemberService memberService;

    private static final Logger Log = LoggerFactory.getLogger(MemberService.class);

    @RequestMapping("/")
    public String Redirect() {
        return "redirect:index.do";
    }

    @RequestMapping("/index.do")
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        mv.addObject("userId", request.getSession().getAttribute("userId"));
        return mv;
    }

    @RequestMapping("/member/JoinMember.do")
    public ModelAndView joinMember() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/member/joinMember");
        return mv;
    }

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

    @RequestMapping("/member/checkMember.do")
    public ModelAndView checkMember() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/member/checkMember");
        return mv;
    }

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

    @RequestMapping("/member/processJoinMember.do")
    public ModelAndView processJoinMember(memberDto dto) throws Exception {
        ModelAndView mv = new ModelAndView();
        memberService.processJoinMember(dto);
        Log.info(String.valueOf(dto));
        mv.setViewName("/index");
        return mv;
    }

    @RequestMapping("/member/LoginMember.do")
    public ModelAndView LoginMember(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/member/loginMember");
        return mv;
    }

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

    @RequestMapping("/member/LogoutMember.do")
    public ModelAndView logoutMember(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
        return index(request);
    }

    @RequestMapping("/member/updateMember.do")
    public ModelAndView updateMember(HttpServletRequest request) throws Exception{
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/member/updateMember");

        HttpSession session = request.getSession();
        memberDto dto = new memberDto();
        dto.setId(session.getAttribute("userId").toString());
        dto = memberService.processMemberInfo(dto);
        mv.addObject("member", dto);
        return mv;
    }

    @RequestMapping("/member/processUpdateMember.do")
    public ModelAndView processUpdateMember(HttpServletRequest request, memberDto dto) throws Exception {
        memberService.processUpdateMember(dto);
        return updateMember(request);
    }
}
