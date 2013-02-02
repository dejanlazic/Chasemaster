package com.chasemaster.ws.data;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable {
   private static final long serialVersionUID = 568862642915512683L;

   @Id
   @GeneratedValue
   protected Long id;

   @Version
   private Integer version;
   
   /*
    * "UUID" and "UID" are Oracle reserved keywords -> "ENTITY_UID"
    * 
    * @Column(name = "ENTITY_UID", unique = true, nullable = false, updatable =
    * false, length = 36) private String uid;
    */

   public Long getId() {
      return id;
   }

   public Integer getVersion() {
      return version;
   }

   @Override
   public boolean equals(Object o) {
      return (o == this || (o instanceof AbstractEntity && getId().equals(((AbstractEntity) o).getId())));
   }

   @Override
   public int hashCode() {
      return getId().hashCode();
   }

   /*
    * public static class AbstractEntityListener {
    * 
    * @PrePersist public void onPrePersist(AbstractEntity abstractEntity) {
    * abstractEntity.uid(); } }
    */
   /*
    * private String uid() { if (uid == null) uid =
    * UUID.randomUUID().toString(); return uid; }
    *//**
    * @return the uid
    */
   /*
    * public String getUid() { if (uid == null) uid =
    * UUID.randomUUID().toString(); return uid; }
    */
}