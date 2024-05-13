package jsl.com.mocking.repository;

public interface TranslationService {
    default String translate(String text, String sourceLang, String targetLang) {
        return text;
    }
}
