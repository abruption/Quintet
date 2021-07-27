package ac.kr.inhatc.spring.Service;

import ac.kr.inhatc.spring.Mapper.AttendanceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceService {
    @Autowired
    AttendanceMapper attendanceMapper;

    // 회원의 출석여부 조회
    public int attCheck(String id) throws Exception {
        return attendanceMapper.attCheck(id);
    }

    // 출석정보를 DB에 저장
    public void Attendance(String id) throws Exception {
        attendanceMapper.Attendance(id);
    }

    // 출석한 ID와 출석일수를 가져옴
    public List<?> AttendCount() throws Exception {
        return attendanceMapper.AttendCount();
    }

    // 회원의 출석현황을 조회
    public List<?> detailAttend(String id) throws Exception {
        return attendanceMapper.detailAttend(id);
    }
}
