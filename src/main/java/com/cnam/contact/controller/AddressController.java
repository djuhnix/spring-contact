package com.cnam.contact.controller;

import com.cnam.contact.bean.Address;
import com.cnam.contact.bean.Contact;
import com.cnam.contact.repository.AddressRepository;
import com.cnam.contact.repository.ContactRepository;
import com.cnam.contact.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

@Controller
public class AddressController {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private UserService userService;

    @GetMapping("/contact/{id}/address")
    public String getAddress(
            @ModelAttribute(name = "addresses") Set<Address> addressSet,
            @PathVariable(name = "id") Long id
    ) {
        Contact contact = contactRepository.findByIdAndUser(id, userService.getLoggedUser());
        addressSet = addressRepository.findByContacts(Set.of(contact));
        return "address/index";
    }
}
