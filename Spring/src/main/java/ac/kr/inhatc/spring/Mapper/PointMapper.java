package ac.kr.inhatc.spring.Mapper;

import java.util.List;

public interface PointMapper {
    // 회원가입 시 지급되는 기본 포인트
    int JoinPoint(String id, String name, String phone, int point, String description, String type) throws Exception;

    // 이메일 수신동의 시 지급되는 포인트
    int EmailPoint(String id, String name, String phone, int point, String description, String type) throws Exception;

    // SMS 수신동의 시 지급되는 포인트
    int SMSPoint(String id, String name, String phone, int point, String description, String type) throws Exception;

    // 회원의 가용포인트를 조회
    int TotalPoint(String id) throws Exception;

    // 회원탈퇴 시 포인트 정보를 삭제
    int processDeleteMember(String id) throws Exception;

    // 포인트 적립내역 조회
    List<?> PointList(String id) throws Exception;

    // 포인트 적립순위 조회
    List<?> PointRanking() throws Exception;

    // 출석 포인트 지급
    void attendPoint(String id) throws Exception;

    // 3일 연속 출석 포인트 중복 지급 여부 조회
    int checkBonus(String id) throws Exception;

    // 3일 연속 출석 시 지급되는 포인트
    void BonusPoint(String id) throws Exception;
}
