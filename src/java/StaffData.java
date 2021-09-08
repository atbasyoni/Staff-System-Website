public class StaffData {
    int id;
    String firstname,lastname;
    String type;

    public StaffData() {
    }

    public StaffData(int id, String firstname, String lastname, String type) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
