package com.example;
//using arraylist and list to manage the pets in the shelter
import java.util.*;

// Manage the pets in the shelter ( add / update / delete ) and track their status ( available / adopted / rehomed )
public class PetManager {
    private static List<Pet> petList = new ArrayList<>();

    //method to add a pet to the shelter
    public PetManager() {
        // Constructor logic if needed
    }

    // for admin to view all pets in the shelter and manage them ( add / update / delete )
    public static List<Pet> getAllPets() {
        return petList;
    }

    // Search for a pet by its unique ID, allowing for quick retrieval of pet information for management and display purposes.
    public static Pet findPetByID(String petID) {
        if (petID == null) {
            return null;
        }
        for (Pet pet : petList) {
            if (petID.equalsIgnoreCase(pet.getPetID())) {
                return pet;
            }
        }
        return null;
    }

    // Add a new pet to the shelter, ensuring that duplicate entries are avoided and providing feedback on the success or failure of the operation for effective pet management.
    public static void addPet(Pet pet) {
        if (pet == null || pet.getPetID() == null) {
            System.out.println("Invalid pet data.");
            return;
        }
        if (findPetByID(pet.getPetID()) != null) {
            System.out.println("Pet already exists: " + pet.getPetID());
            return;
        }
        petList.add(pet);
        System.out.println("Pet added successfully!");
    }

    // Update the status of a pet (e.g., Available, Adopted, Rehomed) and track the current state of the pet for accurate management and display of pet information within the shelter system.
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

    // Delete a pet from the shelter based on its unique ID, providing feedback on the success or failure of the operation to ensure effective management of pet records within the system.
    public static void deletePetInfo(String petID) {
        boolean removed = petList.removeIf(pet -> pet.getPetID().equals(petID));
        if (removed) {
            System.out.println("Pet deleted successfully!");
        } else {
            System.out.println("Pet not found!");
        }
    }
}
