package ac.kr.inhatc.spring.Mapper;

import ac.kr.inhatc.spring.DTO.MemberDTO;

import java.util.List;

public interface MemberMapper {
    // 회원가입을 위한 입력 값 삽입 후, 실행 결과는 int형으로 반환
    int processJoinMember(MemberDTO dto) throws Exception;

    // 회원정보 수정을 위한 조회 후, 실행 결과는 DTO로 반환
    MemberDTO processMemberInfo(MemberDTO dto) throws Exception;

    // 회원정보 수정을 위한 입력 값 수정 후, 실행 결과는 int으로 반환
    int processUpdateMember(MemberDTO dto) throws Exception;

    // ID 중복 확인을 위한 DB 조회 후, 실행 결과는 int형으로 반환
    int idCheck(String id) throws Exception;

    // 회원가입 중복 확인을 위한 DB 조회 후, 실행 결과는 int형으로 반환
    int memberCheck(String phone) throws Exception;

    // 로그인 처리를 위한 DB 조회 후, 실행 결과는 int형으로 반환
    int loginCheck(String id, String password) throws Exception;

    // 회원탈퇴 처리를 위한 DB 정보 삭제 후, 실행 결과는 int형으로 반환
    int processDeleteMember(String id) throws Exception;

    // 전체회원 조회를 위한 DB 조회
    List<?> MemberList() throws Exception;
}
