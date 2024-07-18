package com.jaiylonbabb.uprightcleaningservices.controller;

import com.jaiylonbabb.uprightcleaningservices.entity.Item;
import com.jaiylonbabb.uprightcleaningservices.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin/items")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @GetMapping
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        Optional<Item> item = itemService.getItemById(id);
        if (item.isPresent()) {
            return ResponseEntity.ok(item.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("getInventory")
    public String getInventory(Model model) {
        model.addAttribute("items", itemService.getAllItems());
        return "inventory";
    }
    @GetMapping("/add")
    public String showAddItemForm(Model model) {
//        model.addAttribute("item", new Item());
        return "itemForm";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute("item") Item item) {
        itemService.saveItem(item);
        return "redirect:/admin/dashboard";
    }
    @PostMapping
    public Item createItem(@RequestBody Item item) {
        return itemService.saveItem(item);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable Long id, @RequestBody Item itemDetails) {
        Optional<Item> optionalItem = itemService.getItemById(id);
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            item.setName(itemDetails.getName());
            item.setDescription(itemDetails.getDescription());
            item.setQuantity(itemDetails.getQuantity());
            item.setSupplier(itemDetails.getSupplier());
            item.setReorderLevel(itemDetails.getReorderLevel());
            return ResponseEntity.ok(itemService.saveItem(item));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}
