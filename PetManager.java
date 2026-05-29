package com.example;
//using arraylist and list to manage the pets in the shelter
import java.util.*;
public class PetManager {
    private List<Pet> petList;

    //method to add a pet to the shelter
    public PetManager() {
        petList = new ArrayList<>();
    }

    public void addPet ( Pet pet) {
        petList.add(pet);
        System.out.println("Pet added successfully!");
    }

    public void deletePetInfo (String petID) {
        for (Pet pet : petList) {
            if (pet.getPetID().equals(petID)) {
                petList.remove(pet);
                System.out.println("Pet deleted successfully!");
                return;
            }
        }
        System.out.println("Pet not found!");
    }
}
