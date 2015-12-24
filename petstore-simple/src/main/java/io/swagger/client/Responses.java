package io.swagger.client;

import java.util.List;
import io.swagger.client.model.*;

public class Responses {
  
  
    public static interface PetResponse {
        public void onResponse(Pet pet);
    }
    public static interface PetListResponse {
        public void onResponse(List<Pet> petList);
    }
    
  
  
    public static interface NewPetResponse {
        public void onResponse(NewPet newPet);
    }
    public static interface NewPetListResponse {
        public void onResponse(List<NewPet> newPetList);
    }
    
  
  
    public static interface ErrorModelResponse {
        public void onResponse(ErrorModel errorModel);
    }
    public static interface ErrorModelListResponse {
        public void onResponse(List<ErrorModel> errorModelList);
    }
    
  
}