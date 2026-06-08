package com.example;
//using arraylist and list to manage the pets in the shelter
import java.util.*;

public class PetManager {
    private static List<Pet> petList = new ArrayList<>();

    //method to add a pet to the shelter
    public PetManager() {
        // Constructor logic if needed
    }

    public static List<Pet> getAllPets() {
        return petList;
    }

    public static void addPet ( Pet pet) {
        petList.add(pet);
        System.out.println("Pet added successfully!");
    }

    public static void updatePetStatus(String petID, String newStatus) {
        for (Pet pet : petList) {
            if (pet.getPetID().equals(petID)) {
                pet.updateStatus(newStatus); 
                System.out.println("Pet " + petID + " status updated to: " + newStatus);
                return;
            }
        }
        System.out.println("Error: Pet ID " + petID + " not found!");
    }

    public static void deletePetInfo (String petID) {
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
