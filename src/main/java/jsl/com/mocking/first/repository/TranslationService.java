package jsl.com.mocking.first.repository;

public interface TranslationService {
    default String translate(String text, String sourceLang, String targetLang) {
        return text;
    }
}
