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

import java.util.List;

@Controller
public class AddressController {
    private final AddressRepository addressRepository;
    private final ContactRepository contactRepository;
    private final UserService userService;

    public AddressController(
            AddressRepository addressRepository,
            ContactRepository contactRepository,
            UserService userService
    ) {
        this.addressRepository = addressRepository;
        this.contactRepository = contactRepository;
        this.userService = userService;
    }

    @GetMapping("/contact/{id}/address")
    public String getAddress(
            @ModelAttribute(name = "addresses") List<Address> addresses,
            @PathVariable(name = "id") Long id
    ) {
        Contact contact = contactRepository.findByIdAndUser(id, userService.getLoggedUser());
        addresses = addressRepository.findByContacts(contact);
        return "address/index";
    }
}
