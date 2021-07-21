package ac.kr.inhatc.spring.Mapper;

import ac.kr.inhatc.spring.DTO.memberDto;

public interface MemberMapper {

    int processJoinMember(memberDto dto) throws Exception;
    memberDto processMemberInfo(memberDto dto) throws Exception;
    int processUpdateMember(memberDto dto) throws Exception;
    int idCheck(String id) throws Exception;
    int memberCheck(String phone) throws Exception;
    int loginCheck(String id, String password) throws Exception;

    //    로그인 성공&실패 기능 구현으로 DTO 미사용
    //    memberDto processLoginMember(memberDto dto) throws Exception;
}
