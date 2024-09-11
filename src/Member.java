import java.util.Arrays;

public class Member {
    String id;
    String Firstname;
    String Lastname;
    String Email;
    String Password;
    String rawPassword;
    String PhoneNumber;
    String point;

    public Member(String id, String firstname, String lastname, String email, String password, String phoneNumber, String point) {
        this.id = id;
        Firstname = firstname;
        Lastname = lastname;
        Email = email;
        rawPassword = password;
        Password = decryptPassword(password);
        PhoneNumber = phoneNumber;
        this.point = point;
    }
    boolean checkPassword(String password){
        return this.Password.equals(password);
    }
    String decryptPassword(String password){
        StringBuilder sb = new StringBuilder();
        sb.append(password.charAt(9));
        sb.append(password.charAt(10));
        sb.append(password.charAt(13));
        sb.append(password.charAt(14));
        sb.append(password.charAt(15));
        sb.append(password.charAt(16));
        return sb.toString();
    }
    //get first
    public String getFirstname() {
        return Firstname;
    }

    public String getEmail() {
        return Email;
    }
    public String censorEmail(){
        String[] parts = Email.split("@");
        String[] censored = new String[2];
        censored[0] = parts[0].substring(0, 2) + "***";
        censored[1] = parts[1].substring(0, 2) + "***";
        return censored[0] + "@" + censored[1];
    }
    public String getCensoredName() {
        return Lastname.charAt(0) + ". " + Firstname;
    }
    public String getPhoneNumber() {
        return PhoneNumber.substring(0, 3) + "-" + PhoneNumber.substring(3,6) + "-" + PhoneNumber.substring(6);
    }
    String getPoint(){
        String[] parts = point.split("\\.");
        return parts[0];
    }
    char getStatus() {
        return rawPassword.charAt(2);
    }
    char getRoleID() {
        return rawPassword.charAt(6);// `getRoleID Frank do already//
    }
}
