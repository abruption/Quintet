package ac.kr.inhatc.spring.Service;

import ac.kr.inhatc.spring.Mapper.AttendMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendService {
    @Autowired
    AttendMapper attendMapper;

    // 회원의 출석여부 조회
    public int attCheck(String id) throws Exception {
        return attendMapper.attCheck(id);
    }

    // 출석정보를 DB에 저장
    public void Attendance(String id) throws Exception {
        attendMapper.Attendance(id);
    }

    // 출석한 ID와 출석일수를 가져옴
    public List<?> AttendCount() throws Exception {
        return attendMapper.AttendCount();
    }

    // 회원의 출석현황을 조회
    public List<?> detailAttend(String id) throws Exception {
        return attendMapper.detailAttend(id);
    }

    // 3일 연속 출석체크 여부를 조회
    public int checkAttend(String id) throws Exception {
        return attendMapper.checkAttend(id);
    }

}
