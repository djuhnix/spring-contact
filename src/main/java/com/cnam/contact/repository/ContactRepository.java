package com.cnam.contact.repository;

import com.cnam.contact.bean.Contact;
import com.cnam.contact.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findAllByUser(User user);
    Contact findByIdAndUser(Long id, User user);
}
