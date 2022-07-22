package entity;

public class UsersEntity {
    private Long id;
    private String nick_name;
    private String password;
    private Long phone;
    private Boolean is_root;

    public UsersEntity(Long id, String nick_name, String password, Long phone, Boolean is_root) {
        this.id = id;
        this.nick_name = nick_name;
        this.password = password;
        this.phone = phone;
        this.is_root = is_root;
    }

    public UsersEntity() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public Boolean getIs_root() {
        return is_root;
    }

    public void setIs_root(Boolean is_root) {
        this.is_root = is_root;
    }

    @Override
    public String toString() {
        return "UsersEntity{" +
                "id=" + id +
                ", nick_name='" + nick_name + '\'' +
                ", password='" + password + '\'' +
                ", phone=" + phone +
                ", is_root=" + is_root +
                '}';
    }
}
