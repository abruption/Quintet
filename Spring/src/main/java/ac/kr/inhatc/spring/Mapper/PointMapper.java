package ac.kr.inhatc.spring.Mapper;

import java.util.List;

public interface PointMapper {

    int JoinPoint(String id, String name, String phone, int point, String description, String type) throws Exception;
    int EmailPoint(String id, String name, String phone, int point, String description, String type) throws Exception;
    int SMSPoint(String id, String name, String phone, int point, String description, String type) throws Exception;
    int TotalPoint(String id) throws Exception;
    int processDeleteMember(String id) throws Exception;
    List<?> PointList(String id) throws Exception;
    List<?> PointRanking() throws Exception;
    void attendPoint(String id) throws Exception;
    int CheckAttend(String id) throws Exception;
    void BonusPoint(String id) throws Exception;
}
