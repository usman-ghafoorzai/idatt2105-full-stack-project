package no.ntnu.idatt2105.marketplace.service;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional    // The transactional annotation is used to manage transactions in the service layer. It ensures that all database operations within the method are executed within a single transaction, providing consistency and rollback capabilities in case of errors.
public class ItemService {
  
}
