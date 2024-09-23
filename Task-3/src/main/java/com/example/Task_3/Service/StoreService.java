package com.example.Task_3.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Task_3.Model.Register;
import com.example.Task_3.Model.Store;
import com.example.Task_3.Repository.RegisterRepository;
import com.example.Task_3.Repository.StoreRepository;

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private RegisterRepository registerRepository;

    // Check if store is valid
    public boolean isStoreValid(Long storeId) {
        return storeRepository.findByStoreId(storeId).isPresent();
    }

    // Check if register is valid
    public boolean isRegisterValid(Long storeId, Long registerId) {
    	if(registerRepository.findByStoreIdAndRegisterId(storeId, registerId).isPresent()) {
    		return true;
    	}
    	else {
    		return false;
    	}
        
    }

    // Save a new register
    public Register saveRegister(Long storeId, Register register) {
        register.setStoreId(storeId);
        return registerRepository.save(register);
    }

    // Save a new store
    public Store saveStore(Store store) {
        return storeRepository.save(store);
    }
}
