package com.test.rest.service.controller;

import com.test.data.source.FileDataSource;
import com.test.model.Account;
import com.test.utils.Constant;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.*;

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
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("customerName", account.getCustomerName());
            jsonObject.put("currency", account.getCurrency());
            jsonObject.put("amount", account.getAmount());
            JSONObject tempObject = new JSONObject();
            tempObject.put("account", jsonObject);

            JSONArray jsonArray = readJsonFile();
            if(jsonArray == null){
                jsonArray = new JSONArray();
            }
            jsonArray.add(tempObject);

            try (FileWriter file = new FileWriter("D:\\inteilljwork\\database.json")) {

                file.write(jsonArray.toJSONString());
                file.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }


        }catch (IOException e){
            e.printStackTrace();
        }


        return "Hello Jersey";
    }

    private JSONArray readJsonFile(){

        JSONArray employeeList = null;
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("D:\\inteilljwork\\database.json"))
        {
            //Read JSON file
            if(reader.read() == -1){
                return employeeList;
            }
            employeeList = (JSONArray)jsonParser.parse(reader);

            new JSONArray("xcvcx");
            //employeeList = (JSONArray) obj;
            System.out.println(employeeList);

            System.out.println("Size of list is : "+employeeList.size());

            //Iterate over employee array
            employeeList.forEach( emp -> parseEmployeeObject( (JSONObject) emp ) );

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return employeeList;
    }

    private void parseEmployeeObject(JSONObject employee)
    {
        //Get employee object within list
        JSONObject employeeObject = (JSONObject) employee.get("account");

        //Get employee first name
        String firstName = (String) employeeObject.get("customerName");
        System.out.println(firstName);

        //Get employee last name
        String lastName = (String) employeeObject.get("currency");
        System.out.println(lastName);

        //Get employee website name
        Double website = (Double) employeeObject.get("amount");
        System.out.println(website);
    }


}