package com.tele.telefood.service;

import com.tele.telefood.dto.CreateItemRequest;
import com.tele.telefood.dto.UpdateItemRequest;
import com.tele.telefood.entity.Item;
import com.tele.telefood.entity.Vendor;
import com.tele.telefood.repository.ItemRepository;
import com.tele.telefood.repository.VendorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @Transactional
    public void createItem(Integer userId, CreateItemRequest request) {
        Vendor vendor = vendorRepository.findById(request.getVendorId()).orElseThrow(() -> new RuntimeException("Vendor not found"));

        if (userId.equals(vendor.getId())) {
            Item item = new Item(request, vendor);

            itemRepository.save(item);
        } else {
            throw new IllegalArgumentException("You can't create an item for another vendor other than your own.");
        }
    }

    @Transactional
    public void deleteItem(Integer userId, Integer itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Item not found"));

        if (item.getVendor().getId().equals(userId)) {
            itemRepository.delete(item);
        } else {
            throw new IllegalArgumentException("You can't delete an item for another vendor.");
        }
    }

    @Transactional
    public void updateName(Integer userId, Integer itemId, String name) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Item not found"));

        if (item.getVendor().getId().equals(userId)) {
            item.setName(name);
            itemRepository.save(item);
        } else {
            throw new IllegalArgumentException("You can't update an item for another vendor other than your own.");
        }
    }

    @Transactional
    public void updateDescription(Integer userId, Integer itemId, String description) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Item not found"));

        if (item.getVendor().getId().equals(userId)) {
            item.setDescription(description);
            itemRepository.save(item);
        } else {
            throw new IllegalArgumentException("You can't update an item for another vendor other than your own.");
        }
    }

    @Transactional
    public void updatePrice(Integer userId, Integer itemId, Double price) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Item not found"));

        if (item.getVendor().getId().equals(userId)) {
            item.setPrice(price);
            itemRepository.save(item);
        } else {
            throw new IllegalArgumentException("You can't update an item for another vendor other than your own.");
        }
    }

    @Transactional
    public void updateCategory(Integer userId, Integer itemId, String category) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Item not found"));

        if (item.getVendor().getId().equals(userId)) {
            item.setCategory(category);
            itemRepository.save(item);
        } else {
            throw new IllegalArgumentException("You can't update an item for another vendor other than your own.");
        }
    }

    @Transactional
    public void updateDiscount(Integer userId, Integer itemId, Double discount) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Item not found"));

        if (item.getVendor().getId().equals(userId)) {
            item.setDiscount(discount);
            itemRepository.save(item);
        } else {
            throw new IllegalArgumentException("You can't update an item for another vendor other than your own.");
        }
    }
}
