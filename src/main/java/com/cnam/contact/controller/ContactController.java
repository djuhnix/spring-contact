package com.cnam.contact.controller;

import com.cnam.contact.bean.Contact;
import com.cnam.contact.bean.Mail;
import com.cnam.contact.bean.User;
import com.cnam.contact.repository.ContactRepository;
import com.cnam.contact.repository.MailRepository;
import com.cnam.contact.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
/*import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;*/

import java.net.URI;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/contact")
public class ContactController {
    @Autowired
    private UserService userService;
    private ContactRepository contactRepository;
    private MailRepository mailRepository;

    @GetMapping("/")
    public String contact(Model model) {
        List<Contact> contacts = contactRepository.findAllByUser(userService.getLoggedUser());
        model.addAttribute(contacts);
        return "contact/index";
    }

    @GetMapping("/{id}")
    public String getContact(@PathVariable Long id, Model model) {
        Contact contact = contactRepository.findByIdAndUser(id, userService.getLoggedUser());

        /*if (contact==null) throw NotFoundContactException("Ce contact n'existe pas") {
            return "contact/index";
        }*/

        model.addAttribute("contact", contact);
        return "contact/contact";
    }

    @GetMapping("/add")
    public String formAddContact(Model model) {
        Contact contact = new Contact();
        model.addAttribute("contact", contact);
        return "contact/add";
    }

    @PostMapping("/add")
    public String addContact(@RequestBody Contact contact) {
        String root = "contact/add";

        if (contact != null) {
            boolean b = true;

            String regex = "\\b[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b"; //"^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$"
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher;


            for (Mail mail : contact.getMails()) {
                matcher = pattern.matcher(mail.getEmail());
                if (!matcher.matches()) {
                    b = false;
                }
            }

            regex = "^(?:(?:\\+|00)33|0)\\s*[1-9](?:[\\s.-]*\\d{2}){4}$";
            pattern = Pattern.compile(regex);
            matcher = pattern.matcher(contact.getPhoneNumber());
            if (!matcher.matches()) {
                b = false;
            }

            if (b) {
                contact.setUser(userService.getLoggedUser());
                contactRepository.save(contact);
                root = "redirect:/contact/" + contact.getId();
            }
        }

        return root;

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

    @GetMapping("/modify/{id}")
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

    @DeleteMapping("/{id}")
    public String removeContact(@PathVariable Long id) {
        contactRepository.delete(contactRepository.findByIdAndUser(id, userService.getLoggedUser()));
        return "contact/index";
    }
}
