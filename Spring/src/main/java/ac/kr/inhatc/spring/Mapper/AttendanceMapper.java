package ac.kr.inhatc.spring.Mapper;

import java.util.List;

public interface AttendanceMapper {

    int attCheck(String id) throws Exception;
    void Attendance(String id) throws Exception;
    List<?> AttendCount() throws Exception;
}
