package com.example.Task_3.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Task_3.Model.Register;
import com.example.Task_3.Model.Store;
import com.example.Task_3.Service.StoreService;

@RestController
@RequestMapping("/api/stores")
public class StoreController {

    @Autowired
    private StoreService storeService;

    // Validate if the store exists
    @GetMapping("/validateStore/{storeId}")
    public ResponseEntity<String> validateStore(@PathVariable Long storeId) {
        if (storeService.isStoreValid(storeId)) {
            return ResponseEntity.ok("Store exists");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Store not found. Please register the store.");
        }
    }

    // Register a new store
    @PostMapping("/registerStore")
    public ResponseEntity<Store> registerStore(@RequestBody Store store) {
        Store savedStore = storeService.saveStore(store);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStore);
    }

    // Validate if the register is valid for a specific store
    @GetMapping("/validateRegister/{storeId}/{registerId}")
    public ResponseEntity<String> validateRegister(@PathVariable Long storeId, @PathVariable Long registerId) {
        if (!storeService.isStoreValid(storeId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Store not found. Please register the store first.");
        }

        if (storeService.isRegisterValid(storeId, registerId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Register: Register already exists.");
        } else {
            return ResponseEntity.ok("New register, please proceed with registration.");
        }
    }

 // Register a new register for a store
    @PostMapping("/registerRegister/{storeId}")
    public ResponseEntity<String> registerRegister(@PathVariable Long storeId, @RequestBody Register register) {
        // Check if store exists
        if (!storeService.isStoreValid(storeId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Store not found. Please register the store first.");
        }

        // Check if a register with the same registerId already exists for the store
        if (storeService.isRegisterValid(storeId, register.getRegisterId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid: Register with this ID already exists for the store.");
        }

        // Save the new register if it doesn't exist
        Register savedRegister = storeService.saveRegister(storeId, register);
        return ResponseEntity.status(HttpStatus.CREATED).body("Register successfully created.");
    }

}
