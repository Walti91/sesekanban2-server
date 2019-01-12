package sese.responses;

import sese.entities.EnumPosition;
import sese.entities.Gender;
import sese.entities.Systemuser;

import java.util.Date;

public class SystemuserResponse {

    private Long id;
    private EnumPosition position;
    private String name;
    private Date birthday;
    private Gender gender;
    private String telephoneNumber;
    private String email;

    public SystemuserResponse(Systemuser systemuser) {
        this.id = systemuser.getId();
        this.position = systemuser.getPosition();
        this.name = systemuser.getName();
        this.birthday = systemuser.getBirthday();
        this.gender = systemuser.getGender();
        this.telephoneNumber = systemuser.getTelephoneNumber();
        this.email = systemuser.getEmail();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EnumPosition getPosition() {
        return position;
    }

    public void setPosition(EnumPosition position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "SystemuserResponse{" +
                "id=" + id +
                ", position=" + position +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", gender=" + gender +
                ", telephoneNumber='" + telephoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
