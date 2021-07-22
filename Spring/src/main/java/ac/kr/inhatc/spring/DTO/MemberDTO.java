package ac.kr.inhatc.spring.DTO;

public class MemberDTO {
    // 회원정보를 불러오고 저장하기 위한 Data Transfer Object
    private String id;
    private String password;
    private String name;
    private String mail;
    private String phone;
    private String gender;
    private String birth;
    private String address;
    private String email_agree;
    private String sms_agree;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail_agree() {
        return email_agree;
    }

    public void setEmail_agree(String email_agree) {
        this.email_agree = email_agree;
    }

    public String getSms_agree() {
        return sms_agree;
    }

    public void setSms_agree(String sms_agree) {
        this.sms_agree = sms_agree;
    }

    @Override
    public String toString() {
        return "memberDto{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", mail='" + mail + '\'' +
                ", phone='" + phone + '\'' +
                ", gender='" + gender + '\'' +
                ", birth='" + birth + '\'' +
                ", address='" + address + '\'' +
                ", email_agree='" + email_agree + '\'' +
                ", sms_agree='" + sms_agree + '\'' +
                '}';
    }
}
