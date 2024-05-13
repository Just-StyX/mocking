package jsl.com.mocking.first.service;

import jsl.com.mocking.first.entity.Person;
import jsl.com.mocking.first.repository.PersonRepository;
import jsl.com.mocking.first.repository.TranslationService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HelloPerson {
    private final PersonRepository personRepository;
    private final TranslationService translationService;

    private String message = "Hello, %s, from the universe";

    public HelloPerson(PersonRepository personRepository, TranslationService translationService) {
        this.personRepository = personRepository;
        this.translationService = translationService;
    }

    public String hello(int id, String sourceLang, String targetLang) {
        Optional<Person> person = personRepository.findById(id);
        var name = person.map(Person::firstName).orElse("World");
        return translationService.translate(String.format(message, name), sourceLang, targetLang);
    }
}
