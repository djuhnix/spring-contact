package com.cnam.contact.controller;

import com.cnam.contact.bean.Address;
import com.cnam.contact.bean.Contact;
import com.cnam.contact.bean.Mail;
import com.cnam.contact.bean.User;
import com.cnam.contact.repository.ContactRepository;
import com.cnam.contact.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/contact")
public class ContactController {
    private final UserService userService;
    private final ContactRepository contactRepository;

    public ContactController(
            UserService userService,
            ContactRepository contactRepository
    ) {
        this.userService = userService;
        this.contactRepository = contactRepository;
    }

    @GetMapping("")
    public String contact(Model model) {
        List<Contact> contacts = contactRepository.findAllByUser(userService.getLoggedUser());
        model.addAttribute(contacts);
        User user = userService.getLoggedUser();
        model.addAttribute(user);
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
        model.addAttribute("contact", new Contact());
        //model.addAttribute("contact", new Mail());
        //model.addAttribute("contact", new Address());
        return "contact/add";
    }

    @PostMapping("/add")
    public String addContact(@ModelAttribute("contact") Contact contact) {//,
                             //@ModelAttribute("mail") Mail mail,
                             //@ModelAttribute("address") Address address) {
        String root = "contact/add";

        if (contact != null) { //&& mail != null && address != null) {
            boolean b = true;

            String regex = "\\b[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b"; //"^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$"
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher;

            /*for (Mail m : mail.()) {
            matcher = pattern.matcher(mail.getEmail());
            if (!matcher.matches()) {b = false;}
            }*/

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

    @PostMapping("/modify/{id}")
    public String updateContact(@PathVariable long id, @RequestBody Contact contact) {
        Contact c = contactRepository.findByIdAndUser(id, userService.getLoggedUser());
        c.setLastName(contact.getLastName());
        c.setFirstName(contact.getFirstName());
        c.setBirthDate(contact.getBirthDate());
        c.setPhoneNumber(contact.getPhoneNumber());
        return "redirect:/contact/"+id;
    }

    @DeleteMapping("/{id}")
    public String removeContact(@PathVariable Long id) {
        contactRepository.delete(contactRepository.findByIdAndUser(id, userService.getLoggedUser()));
        return "contact/index";
    }
}
