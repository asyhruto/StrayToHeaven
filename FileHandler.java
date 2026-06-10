package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileHandler {
    public void saveFav(ArrayList<FavouritePage> favouriteList){
     
       // save pets into file ( fav file of each user ) 
       try {
            // create file
             FileWriter fw = new FileWriter("favourites.txt");
           
            for(FavouritePage f : favouriteList){ // loop 
               fw.write(f.getUserID() + "," + f.getPetID() + "\n"); // save 
            }
           
           fw.close();
           
           System.out.println("Favourites saved!");
           
        } catch (IOException e){
           System.out.println("Error saving favourites.."); // display errors if saving fails
        }
    } 
   
    // load fav pets // search 
    public ArrayList<FavouritePage> loadFav(){
       ArrayList<FavouritePage> favList = new ArrayList<>();
       
       try {
           BufferedReader br = new BufferedReader(new FileReader("favourites.txt"));
           String line;

           while((line = br.readLine()) != null){
               String[] data = line.split(",");
               
               FavouritePage fav = new FavouritePage(data[0], data[1]);
               favList.add(fav);
            }
           br.close();
        } catch(IOException e) {
           System.out.println("Error loading..");
        }
        return favList;
    }
   
   // save admin logs 
   public void saveAdminLog(String actions) {
       try{
           FileWriter fw = new FileWriter("adminLogs.txt" , true);
           fw.write(actions + "\n");
           fw.close();
        } catch(IOException e) {
           System.out.println("Error saving logs..");
        }
    }
}
