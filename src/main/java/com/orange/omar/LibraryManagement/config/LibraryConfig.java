package com.orange.omar.LibraryManagement.config;

import com.orange.omar.LibraryManagement.models.Book;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Configuration
@ConfigurationProperties(prefix = "library")
public class LibraryConfig {
    private List<Book> initialBooks;

    public void setInitialBooks(List<Book> initialBooks) {
        this.initialBooks = initialBooks;
    }

    @Bean
    public Map<String, Book> defaultBooks() {
        Map<String, Book> storage = new HashMap<>();
            for (Book book : initialBooks) {
                String id = UUID.randomUUID().toString();
                book.setId(id);
                storage.put(id, book);
            }

        return storage;
    }
}
