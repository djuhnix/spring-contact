package com.cnam.contact.controller;

import com.cnam.contact.bean.Contact;
import com.cnam.contact.bean.User;
import com.cnam.contact.repository.ContactRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
/*import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;*/

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/contact")
public class ContactController {

    private ContactRepository contactRepository;

    @GetMapping("/contact")
    public String contact() {
        return "contact/index";
    }

    public List<Contact> getAllContact(User user) {
        return contactRepository.findAllByUser(user);
    }

    @GetMapping("/contact/{id}")
    public Contact getContact(@PathVariable int id) {
        Contact contact = contactRepository.findById(id);

        /*if (contact==null) throw NoContactException("Ce contact n'existe pas")
        else if (contact.getUser() == user)*/

        return contact;
    }

    @PostMapping
    public String addContact(@RequestBody Contact contact) {
        Contact newContact = contactRepository.save(contact);
        return "contact/index";
        /*
        if (newContact == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newContact.getId())
                .toUri();
        return ResponseEntity.created(location).build();
         */
    }

    /*
    @PostMapping
    public String removeContact(@RequestBody Contact contact) {
        contactRepository.delete(contact);
        return "contact/index";
    }
     */
}
