package ac.kr.inhatc.spring.Mapper;

import ac.kr.inhatc.spring.DTO.MemberDTO;

public interface MemberMapper {

    int processJoinMember(MemberDTO dto) throws Exception;
    MemberDTO processMemberInfo(MemberDTO dto) throws Exception;
    int processUpdateMember(MemberDTO dto) throws Exception;
    int idCheck(String id) throws Exception;
    int memberCheck(String phone) throws Exception;
    int loginCheck(String id, String password) throws Exception;
    int processDeleteMember(String id) throws Exception;

    //    로그인 성공&실패 기능 구현으로 DTO 미사용
    //    memberDto processLoginMember(memberDto dto) throws Exception;
}
