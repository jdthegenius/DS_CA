package rmi;

import java.io.Serializable;

/**
 *
 * @author kinut
 */
public class Email implements Serializable{
    private String sender,recipient,mail,status;

    public Email() {
    }

    public Email(String sender, String recipient, String mail, String status) {
        this.sender = sender;
        this.recipient = recipient;
        this.mail = mail;
        this.status = status;
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
    
}
