package ac.kr.inhatc.spring.Service;

import ac.kr.inhatc.spring.DTO.MemberDTO;
import ac.kr.inhatc.spring.Mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    @Autowired
    MemberMapper memberMapper;      // MemberMapper 의존성 주입

    // 회원정보 수정을 위한 조회
    public MemberDTO processMemberInfo(MemberDTO dto) throws Exception{
        return memberMapper.processMemberInfo(dto);
    }

    // 회원가입을 위한 입력 값 삽입
    public int processJoinMember(MemberDTO dto) throws Exception{
         return memberMapper.processJoinMember(dto);
    }

    // 회원정보 수정을 위한 입력 값 수정
    public int processUpdateMember(MemberDTO dto) throws Exception {
        return memberMapper.processUpdateMember(dto);
    }

    // ID 중복 확인을 위한 DB 조회
    public int idCheck(String id) throws Exception{
        return memberMapper.idCheck(id);
    }

    // 회원가입 중복 확인을 위한 DB 조회
    public int memberCheck(String phone) throws Exception {
        return memberMapper.memberCheck(phone);
    }

    // 로그인 처리를 위한 DB 조회
    public int loginCheck(String id, String password) throws Exception {
        return memberMapper.loginCheck(id, password);
    }

    // 회원탈퇴 처리를 위한 DB 정보 삭제
    public int processDeleteMember(String id) throws Exception {
        return memberMapper.processDeleteMember(id);
    }

    // 회원 조회를 위한 DB 조회
    public List<?> MemberList() throws Exception {
        return memberMapper.MemberList();
    }

}
