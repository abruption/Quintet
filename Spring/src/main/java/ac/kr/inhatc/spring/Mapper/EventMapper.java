package ac.kr.inhatc.spring.Mapper;

import java.util.List;

public interface EventMapper {

    // 이벤트 추가
    void AddEvent(String title, String start_date, String end_date, String use_point, String winner) throws Exception;

    // 이벤트 목록 조회
    List<?> EventList();

    // 이벤트 참여 여부 조회 (중복 방지)
    int CheckAttend(String id, String event) throws Exception;

    // 이벤트 참여처리 (중복이 아닐 경우)
    void JoinEvent(String id, String event) throws Exception;

    // 이벤트 추첨처리
    List<String> DrawEvent(String title, int winner) throws Exception;

    // 이벤트 당첨차 등록 처리
    void updateWinner(String id, String title) throws Exception;

    // 중복 당첨 제외 확인
    int checkWinner(String title) throws Exception;
}
