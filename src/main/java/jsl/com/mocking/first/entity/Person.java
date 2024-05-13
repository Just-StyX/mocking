package jsl.com.mocking.first.entity;

import java.time.LocalDate;

public record Person(int id, String firstName, String lastName, LocalDate dateOfBirth) {
}
