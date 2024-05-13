package jsl.com.mocking.entity;

import java.time.LocalDate;

public record Person(int id, String firstName, String lastName, LocalDate dateOfBirth) {
}
