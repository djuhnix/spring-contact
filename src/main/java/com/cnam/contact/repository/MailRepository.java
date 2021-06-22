package com.cnam.contact.repository;

import com.cnam.contact.bean.Contact;
import com.cnam.contact.bean.Mail;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MailRepository {
    List<Mail> findAllByContact(Contact contact);
}
