package com.test.data.source;

import com.test.utils.Constant;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;

public class FileDataSource {

    private File fileAsDataBase = null;

    com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();

    public File openFile() throws Exception{

       File file = new File(Constant.DATA_SOURCE_FILE_LOCATION);

       if(!file.exists()){
           throw new Exception("The database file at path " + Constant.DATA_SOURCE_FILE_LOCATION + " does not exists.");
       }

        return file;
    }

    public void closeFile(){
        if(null != fileAsDataBase){
            fileAsDataBase = null;
        }
    }


    public synchronized File writeObject(Object object) throws Exception{

        File file = openFile();
        FileInputStream inStream = new FileInputStream(file);
        byte dataBuffer[] = new byte[inStream.available()];
        inStream.read(dataBuffer);
        inStream.close();
        String dataJSONAsStr = new String(dataBuffer);

        // convert dataJSONAsStr to JSONArray
        // if object already exists update object otherwise append object by calling toJSON

        FileOutputStream outStream = new FileOutputStream(file);
        //outStream.write(JSONArray);
        outStream.flush();
        outStream.close();

        return file;
    }

    public synchronized File readObject(Object object) throws Exception{

        File file = openFile();
        FileInputStream inStream = new FileInputStream(file);
        byte dataBuffer[] = new byte[inStream.available()];
        inStream.read(dataBuffer);
        String dataJSONAsStr = new String(dataBuffer);

        JSONArray allRecords = new JSONArray(dataJSONAsStr);
        for (int i=0; i<allRecords.length(); i++) {
            JSONObject item = allRecords.getJSONObject(i);
            String name = item.getString("customerName");
            String surname = item.getString("amount");
        }

        // convert dataJSONAsStr into JSONArray
        // iterate over array to get a record in basis of getID
        //return the Object

        return file;
    }




}