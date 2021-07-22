package ac.kr.inhatc.spring.Service;

import ac.kr.inhatc.spring.DTO.MemberDTO;
import ac.kr.inhatc.spring.Mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    @Autowired
    MemberMapper memberMapper;

    public MemberDTO processMemberInfo(MemberDTO dto) throws Exception{
        return memberMapper.processMemberInfo(dto);
    }

    public int processJoinMember(MemberDTO dto) throws Exception{
         return memberMapper.processJoinMember(dto);
    }

    public int processUpdateMember(MemberDTO dto) throws Exception {
        return memberMapper.processUpdateMember(dto);
    }

    public int idCheck(String id) throws Exception{
        return memberMapper.idCheck(id);
    }

    public int memberCheck(String phone) throws Exception {
        return memberMapper.memberCheck(phone);
    }

    public int loginCheck(String id, String password) throws Exception {
        return memberMapper.loginCheck(id, password);
    }

    public int processDeleteMember(String id) throws Exception {
        return memberMapper.processDeleteMember(id);
    }

//     로그인 성공&실패 기능 구현으로 DTO 미사용
//     public memberDto processLoginMember(memberDto dto) throws Exception{
//        return memberMapper.processLoginMember(dto);
//    }
}
