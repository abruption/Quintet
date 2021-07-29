package ac.kr.inhatc.spring.Service;

import ac.kr.inhatc.spring.Mapper.EventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    @Autowired
    EventMapper eventMapper;

    // 이벤트 추가
    public void AddEvent(String title, String start_date, String end_date, String use_point, String winner) throws Exception{
        eventMapper.AddEvent(title, start_date, end_date, use_point, winner);
    }

    // 이벤트 목록 조회
    public List<?> EventList() throws Exception{
        return eventMapper.EventList();
    }

    // 이벤트 참여 여부 조회 (중복 방지)
    public int CheckAttend(String id, String event) throws Exception {
        return eventMapper.CheckAttend(id, event);
    }

    // 이벤트 참여처리 (중복이 아닐 경우)
    public void JoinEvent(String id, String event) throws Exception {
        eventMapper.JoinEvent(id, event);
    }

    // 이벤트 추첨 처리
    public List<String> DrawEvent(String title, int winner) throws Exception {
        return eventMapper.DrawEvent(title, winner);
    }

    // 이벤트 당첨자 업데이트
    public void updateWinner(String id, String title) throws Exception {
        eventMapper.updateWinner(id, title);
    }

    // 이벤트 중복 담청 확인
    public int checkWinner(String title) throws Exception {
        return eventMapper.checkWinner(title);
    }
}
