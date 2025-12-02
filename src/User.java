public class User {
  private  String name;
  private String ip_address;

    public User(String name, String ip_address) {
        this.name = name;
        this.ip_address = ip_address;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Users{" +
                "name='" + name + '\'' +
                ", ip_address='" + ip_address + '\'' +
                '}';
    }
}
