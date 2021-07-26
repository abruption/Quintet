package ac.kr.inhatc.spring.Service;

import ac.kr.inhatc.spring.Mapper.AttendanceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceService {
    @Autowired
    AttendanceMapper attendanceMapper;

    public int attCheck(String id) throws Exception {
        return attendanceMapper.attCheck(id);
    }

    public void Attendance(String id) throws Exception {
        attendanceMapper.Attendance(id);
    }

    public List<?> AttendCount() throws Exception {
        return attendanceMapper.AttendCount();
    }
}
