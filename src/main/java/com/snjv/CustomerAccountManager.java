package com.snjv;

import com.snjv.pojo.Account;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.FileWriter;

// Plain old Java Object it does not extend as class or implements
// an interface

// The class registers its methods for the HTTP GET request using the @GET annotation.
// Using the @Produces annotation, it defines that it can deliver several MIME types,
// text, XML and HTML.

// The browser requests per default the HTML MIME type.

//Sets the path to base URL + /hello
@Path("/hello")
public class CustomerAccountManager {

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
            fileWriter.close();

        }catch (Exception e){
            e.printStackTrace();
        }


        return "Hello Jersey";
    }


}