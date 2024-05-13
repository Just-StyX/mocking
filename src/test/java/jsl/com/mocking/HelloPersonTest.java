package jsl.com.mocking;

import jsl.com.mocking.first.entity.Person;
import jsl.com.mocking.first.repository.PersonRepository;
import jsl.com.mocking.first.repository.TranslationService;
import jsl.com.mocking.first.service.HelloPerson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HelloPersonTest {
    @Mock
    private PersonRepository personRepository;

    @Mock
    private TranslationService translationService;

    @InjectMocks
    private HelloPerson helloPerson;

    @Test
    @DisplayName("Greet Captain Shantosh")
    void greetAnExistencePerson() {
        // tell what you expect from mocking
        when(personRepository.findById(anyInt())).thenReturn(Optional.of(
                new Person(1,"Antoine", "Shantosh", LocalDate.of(1877, Month.DECEMBER, 25))));
        when(translationService.translate("Hello, Antoine, from the universe", "en", "fr"))
                .thenReturn("Bonjour Antoine, de l'univers");

        // test the method in the HelloPerson class
        var greeting = helloPerson.hello(1, "en", "fr");
        assertEquals("Bonjour Antoine, de l'univers", greeting);

        // are the methods called once and in the right order ?
        InOrder inOrder = Mockito.inOrder(personRepository, translationService);
        inOrder.verify(personRepository).findById(anyInt());
        inOrder.verify(translationService).translate(anyString(), eq("en"), eq("fr"));
    }

    @Test
    @DisplayName("Greet a non-existing person")
    void greetANonExistingPerson() {
        // tell what you expect from mocking
        when(personRepository.findById(anyInt())).thenReturn(Optional.empty());
        when(translationService.translate("Hello, World, from the universe", "en", "fr"))
                .thenReturn("Bonjour, Monde, de l'univers");

        // test the method in the HelloPerson class
        var greeting = helloPerson.hello(100, "en", "fr");
        assertEquals("Bonjour, Monde, de l'univers", greeting);

        // are the methods called once and in the right order ?
        InOrder inOrder = Mockito.inOrder(personRepository, translationService);
        inOrder.verify(personRepository).findById(anyInt());
        inOrder.verify(translationService).translate(anyString(), eq("en"), eq("fr"));
    }
}
