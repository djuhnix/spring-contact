package com.cnam.contact.repository;

import com.cnam.contact.bean.Address;
import com.cnam.contact.bean.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Set;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByContacts(Contact contact);

}
