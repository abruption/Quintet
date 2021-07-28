package ac.kr.inhatc.spring.Mapper;

import java.util.List;

public interface AttendMapper {
    // 회원의 출석여부 조회 후, 결과 값을 int으로 반환
    int attCheck(String id) throws Exception;

    // 출석정보를 DB에 저장
    void Attendance(String id) throws Exception;

    // 전체 회원의 출석일수를 조회 후, List 형식으로 반환
    List<?> AttendCount() throws Exception;

    // 회원의 출석현황을 조회 후,  List 형식으로 반환
    List<?> detailAttend(String id) throws Exception;

    // 3일 연속 출석체크 여부를 조회 후, 결과 값을 int형으로 반환
    int checkAttend(String id) throws Exception;
}
