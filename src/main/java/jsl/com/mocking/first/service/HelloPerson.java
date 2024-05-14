package jsl.com.mocking.first.service;

import jsl.com.mocking.first.entity.Person;
import jsl.com.mocking.first.repository.PersonRepository;
import jsl.com.mocking.first.repository.TranslationService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<String> lastNames() {
        var people = personRepository.findAll();
        return people.stream().map(Person::lastName).collect(Collectors.toList());
    }

    public Person save(Person person) {
        return personRepository.save(person);
    }

    public void delete(Person person) {
        personRepository.delete(person);
    }

    public long count() {
        return personRepository.count();
    }

    public List<Person> findByIds(int ...ids) {
        return Arrays.stream(ids)
                .mapToObj(id -> personRepository.findById(id))
                .filter(person -> person.isPresent())
                .map(person -> person.get())
                .collect(Collectors.toList());
    }
}
