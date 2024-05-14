package jsl.com.mocking;

import jsl.com.mocking.first.entity.Person;
import jsl.com.mocking.first.repository.PersonRepository;
import jsl.com.mocking.first.repository.TranslationService;
import jsl.com.mocking.first.repositoryImplementations.InMemoryPeopleRepositoryImpl;
import jsl.com.mocking.first.service.HelloPerson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HelloPersonTest {
    @Mock
    private PersonRepository personRepository;

    @Mock
    private TranslationService translationService;

    @InjectMocks
    private HelloPerson helloPerson;

    private List<Person> people = new ArrayList<>();
    @BeforeEach
    void setPersonRepository() {
        people = InMemoryPeopleRepositoryImpl.initialData();
    }

    @AfterEach
    void afterEachTest() {
        InMemoryPeopleRepositoryImpl.deleteAll();
    }

    @Test
    void getLastNamesByMocking() {
        when(personRepository.findAll()).thenReturn(people);
        List<String> lastNames = helloPerson.lastNames();
        assertThat(lastNames).contains("Andreas", "Mata", "Kapoor", "Tey", "Piers");
        verify(personRepository).findAll(); // verifying if findAll() method was called.
    }

    @Test
    void savePerson() {
        Person person = new Person(1000, "test", "testing", LocalDate.now());
        when(personRepository.save(person)).thenReturn(InMemoryPeopleRepositoryImpl.save(person));
        var savedPerson = helloPerson.save(person);
        assertAll(
                () -> assertNotNull(savedPerson),
                () -> assertThat(1000).isEqualTo(savedPerson.id()),
                () -> assertThat(6).isEqualTo(InMemoryPeopleRepositoryImpl.findAll().size()),
                () -> {
                    when(personRepository.count()).thenReturn(InMemoryPeopleRepositoryImpl.count());
                    assertThat(6).isEqualTo(helloPerson.count());
                    verify(personRepository).count();
                }
        );
        verify(personRepository).save(person);
    }

    @Test
    void deletePerson() {
        var person = people.get(0);
        doAnswer((invocationOnMock -> {
            InMemoryPeopleRepositoryImpl.delete(invocationOnMock.getArgument(0));
            return null;
        })).when(personRepository).delete(person);
        helloPerson.delete(person);
        assertThat(4).isEqualTo(InMemoryPeopleRepositoryImpl.findAll().size());
    }

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
