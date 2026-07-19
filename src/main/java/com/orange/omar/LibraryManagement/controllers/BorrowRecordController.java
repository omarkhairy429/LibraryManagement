package com.orange.omar.LibraryManagement.controllers;

import com.orange.omar.LibraryManagement.models.BorrowRecord;
import com.orange.omar.LibraryManagement.services.BorrowRecordService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BorrowRecordController {
    private BorrowRecordService borrowRecordService;
    public BorrowRecordController(BorrowRecordService borrowRecordService) {
        this.borrowRecordService = borrowRecordService;
    }


    @PostMapping("/borrowRecord")
    public BorrowRecord borrowBook(@RequestBody @Valid BorrowRecord record) {
        borrowRecordService.borrowBook(record);
        return record;
    }
}
