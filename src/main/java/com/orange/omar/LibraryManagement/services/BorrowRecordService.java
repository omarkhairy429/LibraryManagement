package com.orange.omar.LibraryManagement.services;

import com.orange.omar.LibraryManagement.models.Book;
import com.orange.omar.LibraryManagement.models.BorrowRecord;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class BorrowRecordService {
    private Map<String , BorrowRecord> records = new HashMap<>();

   public BorrowRecord borrowBook(BorrowRecord record) {
        String unique_id = UUID.randomUUID().toString();
        record.setBorrowID(unique_id);
        records.put(record.getBorrowID(), record);
        return record;
    }
}
