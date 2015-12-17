package io.swagger.client.api;

import java.util.List;
import io.swagger.client.model.*;

public class Responses {
  
  
    public static interface PetResponse {
        public void onResponse(Pet pet);
    }
    public static interface PetListResponse {
        public void onResponse(List<Pet> petList);
    }
    
  
}