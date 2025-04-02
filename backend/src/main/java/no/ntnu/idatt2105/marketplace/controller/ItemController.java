package no.ntnu.idatt2105.marketplace.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import no.ntnu.idatt2105.marketplace.model.Item;
import no.ntnu.idatt2105.marketplace.service.ItemService;

@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ItemController {
  private final ItemService itemService;
  
  @PostMapping
  public ResponseEntity<Item> createItem(@RequestBody Item item) {
    Item newItem = itemService.saveItem(item);
    return ResponseEntity.ok(newItem);
  }
}
