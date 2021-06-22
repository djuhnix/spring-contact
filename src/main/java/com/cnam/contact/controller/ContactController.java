package com.cnam.contact.controller;

import com.cnam.contact.bean.Contact;
import com.cnam.contact.bean.User;
import com.cnam.contact.repository.ContactRepository;
import com.cnam.contact.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
/*import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;*/

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/contact")
public class ContactController {
    @Autowired
    private UserService userService;
    private ContactRepository contactRepository;

    @GetMapping("/contact")
    public String contact(Model model) {
        List<Contact> contacts = contactRepository.findAllByUser(userService.getLoggedUser());
        model.addAttribute(contacts);
        return "contact/index";
    }

    @GetMapping("/contact/{id}")
    public String getContact(@PathVariable Long id, Model model) {
        Contact contact = contactRepository.findByIdAndUser(id, userService.getLoggedUser());
        /*if (contact==null) throw NoContactException("Ce contact n'existe pas")
        else if (contact.getUser() == user)
        else*/
        model.addAttribute("contact", contact);
        return "contact/contact";
    }

    @GetMapping("/contact/add")
    public String formAddContact(Model model) {
        Contact contact = new Contact();
        model.addAttribute("contact", contact);
        return "contact/add";
    }

    @PostMapping("/contact/add")
    public String addContact(@RequestBody Contact contact) {
        contact.setUser(userService.getLoggedUser());
        /*if()*/
        contactRepository.save(contact);
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

    @GetMapping("/contact/modify/{id}")
    public String modifyContact(@PathVariable long id, Model model) {
        Contact contact = contactRepository.findByIdAndUser(id, userService.getLoggedUser());
        model.addAttribute("contact", contact);
        return "contact/modify";
    }

    @PostMapping
    public String updateContact(@RequestBody Contact contact) {
        contactRepository.save(contact);
        return "redirect:/contact/"+contact.getId();
    }

    @DeleteMapping("/contact/{id}")
    public String removeContact(@PathVariable Long id) {
        contactRepository.delete(contactRepository.findByIdAndUser(id, userService.getLoggedUser()));
        return "contact/index";
    }
}
