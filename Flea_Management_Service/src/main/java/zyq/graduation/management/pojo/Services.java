package zyq.graduation.management.pojo;

/**
 * @author 周杨清
 * @date 2021/5/3 16:39
 */
public class Services {

    private String uuid;
    private String province;
    private String college;
    private String area;
    private String status;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "Service{" +
                "uuid='" + uuid + '\'' +
                ", province='" + province + '\'' +
                ", college='" + college + '\'' +
                ", area='" + area + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
