package com.orange.omar.LibraryManagement.controllers;

import com.orange.omar.LibraryManagement.models.BorrowRecord;
import com.orange.omar.LibraryManagement.services.BorrowRecordService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/borrowRecord/{borrowID}")
    public BorrowRecord returnBook(@PathVariable String borrowID) {
        return borrowRecordService.returnBook(borrowID);
    }
}
