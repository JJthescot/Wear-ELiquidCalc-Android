package com.homeapps.john.weareliquidcalc.Helpers;

import android.content.Context;

import com.homeapps.john.weareliquidcalc.Pojo.Recipe;
import com.homeapps.john.weareliquidcalc.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

// Todo: Add Save/Load Data exception handling
// Todo: Correct filename and location
public class DataManager {

    public static boolean fileSaveData(Context context, ArrayList<Recipe> recipes){
        try{
            FileOutputStream fileOutputStream = context.openFileOutput(context.getString(R.string.DataFilename), Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream= new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(recipes);
            objectOutputStream.close();
            fileOutputStream.close();
            return true;
        }catch(IOException ioEx){
            // Add exception handler
            return false;
        }
    }

    public static ArrayList<Recipe> fileLoadData(Context context) {
        try
        {
            ArrayList<Recipe> result;
            FileInputStream fileInputStream = context.openFileInput(context.getString(R.string.DataFilename));
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            result = (ArrayList) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
            return result;
        }catch(IOException ioEx){
            // Add exception handler
            return null;
        }catch(ClassNotFoundException cEx){
            // Add exception handler
            return null;
        }
    }

    public static boolean fileExists(Context context) {
        File file = context.getFileStreamPath(context.getString(R.string.DataFilename));
        return file.exists();
//        throw new UnsupportedOperationException("Not yet implemented.");
    }
    public static boolean fileDelete(Context context) {
        File file = context.getFileStreamPath(context.getString(R.string.DataFilename));
        return file.delete();
//        throw new UnsupportedOperationException("Not yet implemented.");
    }

    public static boolean getRecipeCount(){
        throw new UnsupportedOperationException("Not yet implemented.");
    }

}
