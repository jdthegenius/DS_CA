package rmi;

import java.io.Serializable;


public class Email implements Serializable{
    private static final long serialVersionUID = 12358903454875L;
    private String sender,recipient,mail,status,subject;

    public Email() {
    }

    public Email(String sender, String recipient, String subject,String mail, String status) {
        this.sender = sender;
        this.recipient = recipient;
        this.mail = mail;
        this.status = status;
        this.subject=subject;
    }
    
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return subject+"\n By: "+ sender;
    }
    public String displayEmail(){
        return "FROM: "+sender+"\nSUBJECT: "+subject+"\n\n"+mail;
    }
    
    
    
}
