package com.test.rest.service.controller;

import com.test.model.Account;
import com.test.utils.Constant;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;

// Plain old Java Object it does not extend as class or implements
// an interface

// The class registers its methods for the HTTP GET request using the @GET annotation.
// Using the @Produces annotation, it defines that it can deliver several MIME types,
// text, XML and HTML.

// The browser requests per default the HTML MIME type.

//Sets the path to base URL + /hello
@Path("/hello")
public class AccountRestController {

    // This method is called if TEXT_PLAIN is request
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayPlainTextHello() {
        return "Hello Jersey";
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String saveRecord(String accountData) {

        System.out.println(accountData);
        com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();

        FileWriter fileWriter = null;
        try{
            Account account = objectMapper.readValue(accountData, Account.class);

            fileWriter = new FileWriter("D:\\inteilljwork\\database.json", true);
            fileWriter.write(accountData);
            fileWriter.flush();

            System.out.println("customer : "+account.getCustomerName());

            File file = new File(Constant.DATA_SOURCE_FILE_LOCATION);

            FileInputStream inStream = new FileInputStream(file);
            byte dataBuffer[] = new byte[inStream.available()];
            inStream.read(dataBuffer);
            String dataJSONAsStr = new String(dataBuffer);

            System.out.println("dataJSONAsStr : "+ dataJSONAsStr);

            JSONArray allRecords = new JSONArray(dataJSONAsStr);
            System.out.println("Json Array : "+ allRecords);
            for (int i=0; i<allRecords.length(); i++) {
                JSONObject item = allRecords.getJSONObject(i);
                String name = item.getString("customerName");
                String surname = item.getString("amount");
            }
            fileWriter.close();

        }catch (Exception e){
            e.printStackTrace();
        }


        return "Hello Jersey";
    }


}