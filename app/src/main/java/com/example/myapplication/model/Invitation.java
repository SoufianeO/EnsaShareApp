package com.example.myapplication.model;




public class Invitation {
    private String invitationId ;
    private String userName ;
    private String firstName ;
    private String lastName  ;
    private String profilePic ;
    private String admin ;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getInvitationId() {
        return invitationId;
    }

    public Invitation() {
    }

    public Invitation(String invitationId,String userName ,String firstName, String lastName, String profilePic, String admin) {
        this.invitationId=invitationId;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profilePic = profilePic;
        this.admin = admin;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "Invitation{" +
                "invitationId='" + invitationId + '\'' +
                ", userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", profilePic='" + profilePic + '\'' +
                ", admin='" + admin + '\'' +
                '}';
    }
}
