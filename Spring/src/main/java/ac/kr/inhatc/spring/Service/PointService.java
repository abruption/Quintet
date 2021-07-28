package ac.kr.inhatc.spring.Service;

import ac.kr.inhatc.spring.Mapper.PointMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class PointService {
    @Autowired
    PointMapper pointMapper;    // PointMapper 의존성 주입

    // 회원가입 축하 포인트 지급
    public int JoinPoint(String id, String name, String phone, int point, String description, String type) throws Exception {
        return pointMapper.JoinPoint(id, name, phone, point, description, type);
    }

    // 이메일 수신동의 포인트 지급
    public int EmailPoint(String id, String name, String phone, int point, String description, String type) throws Exception {
        return pointMapper.JoinPoint(id, name, phone, point, description, type);
    }

    // SMS 수신동의 포인트 지급
    public int SMSPoint(String id, String name, String phone, int point, String description, String type) throws Exception {
        return pointMapper.JoinPoint(id, name, phone, point, description, type);
    }

    // 총 합계 포인트 조회
    public int TotalPoint(String id) throws Exception {
        return pointMapper.TotalPoint(id);
    }

    // 회원탈퇴 시 포인트 정보 삭제
    public int processDeleteMember(String id) throws Exception {
        return pointMapper.processDeleteMember(id);
    }

    // 포인트 상세내역 조회
    public List<?> PointList(String id) throws Exception {
        return pointMapper.PointList(id);
    }

    // 포인트 적립순위 조회
    public List<?> PointRanking() throws Exception {
        return pointMapper.PointRanking();
    }

    // 출석 포인트 지급
    public void attendPoint(String id) throws Exception{
        pointMapper.attendPoint(id);
    }

    // 3일 연속 출석 포인트 중복 지급 조회
    public int checkBonus(String id) throws Exception {
        return pointMapper.checkBonus(id);
    }

    // 3일 연속 출석 시 지급되는 포인트
    public void BonusPoint(String id) throws Exception {
        pointMapper.BonusPoint(id);
    }

}
