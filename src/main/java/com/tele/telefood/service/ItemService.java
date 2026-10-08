package com.tele.telefood.service;

import com.tele.telefood.dto.CreateItemRequest;
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
}
