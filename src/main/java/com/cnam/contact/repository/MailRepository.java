package com.cnam.contact.repository;

import com.cnam.contact.bean.Contact;
import com.cnam.contact.bean.Mail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MailRepository extends JpaRepository<Mail, Long> {
    List<Mail> findAllByContact(Contact contact);
}
