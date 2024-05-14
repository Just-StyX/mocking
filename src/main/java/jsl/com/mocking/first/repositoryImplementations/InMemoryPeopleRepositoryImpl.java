package jsl.com.mocking.first.repositoryImplementations;

import jsl.com.mocking.first.entity.Person;
import jsl.com.mocking.first.repository.PersonRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class InMemoryPeopleRepositoryImpl {
    static List<Person> people = new ArrayList<>();

    public static Person save(Person person) {
        synchronized (people) {
            people.add(person);
        }
        return person;
    }

    public static Optional<Person> findById(int id) {
        Map<Integer, Person> mappingPeopleWithId = people.stream().collect(Collectors.toMap(Person::id, person -> person));
        return Optional.ofNullable(mappingPeopleWithId.get(id));
    }

    public static List<Person> findAll() {
        return people;
    }

    public static long count() {
        return people.size();
    }

    public static void delete(Person person) {
        synchronized (people) {
            people.remove(person);
        }
    }

    public static void deleteAll() {
        people = new ArrayList<>();
    }

    public static List<Person> initialData() {
        var list = List.of(
                new Person(1, "Jiwani", "Andreas", LocalDate.of(1888, 11, 24)),
                new Person(2, "Juan", "Mata", LocalDate.of(1805, 1, 31)),
                new Person(3, "Shaun", "Kapoor", LocalDate.of(1945, 8, 9)),
                new Person(4, "Micheal", "Tey", LocalDate.of(1667, 5, 1)),
                new Person(5, "Jamal", "Piers", LocalDate.of(1709, 6, 15))
        );
        for (Person person: list) save(person);
        return list;
    }
}
