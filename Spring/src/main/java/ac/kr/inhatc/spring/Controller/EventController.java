package ac.kr.inhatc.spring.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;

@Controller
public class EventController {

    // 관리자용 이벤트 관리 페이지 Mapping
    @RequestMapping("/event/manageEvent.do")
    public ModelAndView manageEvent() throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/event/manageEvent");
        return mv;
    }

    // 사용자용 이벤트 목록 페이지 Mapping
    @RequestMapping("/event/eventList.do")
    public ModelAndView eventList() throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/event/eventList");
        return mv;
    }
}
