package com.test.rest.service.controller;

import com.test.data.source.FileDataSource;
import com.test.model.Account;
import com.test.utils.Constant;
import com.test.utils.PATCH;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;

// Plain old Java Object it does not extend as class or implements
// an interface

// The class registers its methods for the HTTP GET request using the @GET annotation.
// Using the @Produces annotation, it defines that it can deliver several MIME types,
// text, XML and HTML.

// The browser requests per default the HTML MIME type.

//Sets the path to base URL + /hello
@Path("/account")
public class AccountRestController {

    //  TODO : Need to inject dependency via Spring
    FileDataSource fileDataSource = new FileDataSource();


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCustommerAccounts() {

        String message = "No Record Found !";
        try{
            org.json.JSONArray allCustomerAccounts = fileDataSource.readObjects();
            return Response.status(200).entity(allCustomerAccounts).build();

        }catch (Exception e){
            e.printStackTrace();
        }

        return Response.status(200).entity(message).build();
    }

    @GET
    @Path("{accountId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustommerAccount(@PathParam("accountId")int accountId) {

        return Response.status(200).entity(fileDataSource.checkRecord(accountId)).build();
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveRecord(String accountData) {

        System.out.println(accountData);
        com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();

        try{
            Account account = objectMapper.readValue(accountData, Account.class);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", account.getId());
            jsonObject.put("customerName", account.getCustomerName());
            jsonObject.put("currency", account.getCurrency());
            jsonObject.put("amount", account.getAmount());
            JSONObject tempObject = new JSONObject();
            tempObject.put("account", jsonObject);

            org.json.JSONArray jsonArray = fileDataSource.readObjects();
            if(jsonArray == null){
                jsonArray = new org.json.JSONArray();
            }
            jsonArray.put(tempObject);


            try (FileWriter file = new FileWriter(Constant.DATA_SOURCE_FILE_LOCATION)) {
                file.write(jsonArray.toString());
                file.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }


        }catch (IOException e){
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return Response.status(200).entity("Record Save Successfuly").build();
    }

    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    public Response partialUpdateCustommerAccount(String dataForUpdate) {


        return Response.status(200).entity("Record updated successfully").build();
    }

}