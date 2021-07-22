package ac.kr.inhatc.spring.DTO;

public class PointDTO {
    // 포인트 정보를 불러오고 저장하기 위한 Data Transfer Object
    private String id;
    private String name;
    private String phone;
    private int save_point;
    private String description;
    private String type;
    private String save_date;
    private String expire_date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getSave_point() {
        return save_point;
    }

    public void setSave_point(int save_point) {
        this.save_point = save_point;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSave_date() {
        return save_date;
    }

    public void setSave_date(String save_date) {
        this.save_date = save_date;
    }

    public String getExpire_date() {
        return expire_date;
    }

    public void setExpire_date(String expire_date) {
        this.expire_date = expire_date;
    }

    @Override
    public String toString() {
        return "PointDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", save_point=" + save_point +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", save_date='" + save_date + '\'' +
                ", expire_date='" + expire_date + '\'' +
                '}';
    }
}
