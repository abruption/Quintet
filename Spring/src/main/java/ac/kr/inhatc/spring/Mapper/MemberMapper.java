package ac.kr.inhatc.spring.Mapper;

import ac.kr.inhatc.spring.DTO.MemberDTO;

import java.util.List;

public interface MemberMapper {

    int processJoinMember(MemberDTO dto) throws Exception;
    MemberDTO processMemberInfo(MemberDTO dto) throws Exception;
    int processUpdateMember(MemberDTO dto) throws Exception;
    int idCheck(String id) throws Exception;
    int memberCheck(String phone) throws Exception;
    int loginCheck(String id, String password) throws Exception;
    int processDeleteMember(String id) throws Exception;
    List<?> MemberList() throws Exception;
}
