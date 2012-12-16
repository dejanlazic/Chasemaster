package com.chasemaster.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="Reponse")
public class Response {
   @XmlElement(name="MessageText")
   private String messageText;
   @XmlElement(name="MessageType")
   private String messageType;
      
   public Response() {
      super();
   }
   
   public Response(String messageText, String messageType) {
      super();
      this.messageText = messageText;
      this.messageType = messageType;
   }

   public String getMessageText() {
      return messageText;
   }
   public void setMessageText(String messageText) {
      this.messageText = messageText;
   }
   public String getMessageType() {
      return messageType;
   }
   public void setMessageType(String messageType) {
      this.messageType = messageType;
   }      
}