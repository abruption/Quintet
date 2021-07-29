package ac.kr.inhatc.spring.Controller;

import ac.kr.inhatc.spring.Service.EventService;
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

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static java.lang.Integer.parseInt;

@Controller
public class EventController {
    @Autowired
    EventService eventService;

    @Autowired
    PointService pointService;

    private static final Logger Log = LoggerFactory.getLogger(EventController.class);

    // 관리자용 이벤트 관리(등록 및 추첨) 페이지 Mapping
    @RequestMapping("/event/manageEvent.do")
    public ModelAndView manageEvent() throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/event/manageEvent");
        List<?> EventList = eventService.EventList();
        mv.addObject("EventList", EventList);
        return mv;
    }

    // 관리자용 이벤트 추가 페이지 Mapping
    @RequestMapping("/event/AddEvent.do")
    public ModelAndView AddEvent() throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/event/AddEvent");
        return mv;
    }

    // DB에 이벤트를 추가하는 주소 Mapping
    @ResponseBody
    @RequestMapping(value = "/AddEvent", method = RequestMethod.POST)
    public void processAddEvent(String title, String start_date, String end_date, String use_point, String winner) throws Exception {
        eventService.AddEvent(title, start_date, end_date, use_point, winner);
    }

    // 사용자용 이벤트 목록 페이지 Mapping
    @RequestMapping("/event/eventList.do")
    public ModelAndView eventList() throws Exception {
        ModelAndView mv = new ModelAndView();
        List<?> EventList = eventService.EventList();
        mv.setViewName("/event/eventList");
        mv.addObject("EventList", EventList);
        return mv;
    }

    // 이벤트에 참여할 경우 DB에 관련 정보를 남기는 처리 주소 Mapping
    @RequestMapping("/event/JoinEvent.do")
    public ModelAndView JoinEvent(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        String id = session.getAttribute("userId").toString();
        String event = request.getParameter("event");
        String use_point = request.getParameter("use_point");
        pointService.JoinEvent(id, event, parseInt(use_point));
        eventService.JoinEvent(id, event);
        return eventList();
    }

    // 이벤트 참석여부를 판단하여 중복 참여를 막는 주소 Mapping
    @ResponseBody
    @RequestMapping(value = "/CheckEvent", method = RequestMethod.POST)
    public int CheckEvent(HttpServletRequest request, String event) throws Exception {
        HttpSession session = request.getSession();
        String id = session.getAttribute("userId").toString();
        int result = eventService.CheckAttend(id, event);
        Log.warn(String.valueOf(result));

        if(result != 1)
            return 0;
        else
            return 1;
    }

    @RequestMapping("/event/drawEvent.do")
    public ModelAndView drawEvent(HttpServletRequest request) throws Exception {
        String title = request.getParameter("title");
        String winner = request.getParameter("winner");
        List<String> drawEvent = eventService.DrawEvent(title, parseInt(winner));
        ModelAndView mv = new ModelAndView();
        mv.addObject("drawEvent", drawEvent);
        mv.addObject("title", title);
        mv.addObject("use_point", request.getParameter("use_point"));
        mv.setViewName("/event/drawEvent");
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/updateWinner", method = RequestMethod.POST)
    public int updateWinner(String id, String title, String use_point) throws Exception {
        Log.warn(id);
        Log.warn(title);
        int result = eventService.checkWinner(title);
        if(result != 0) {
            return 0;
        } else {
            eventService.updateWinner(id, title);
            pointService.winnerPoint(id, title, parseInt(use_point));
            return 1;
        }
    }
}
