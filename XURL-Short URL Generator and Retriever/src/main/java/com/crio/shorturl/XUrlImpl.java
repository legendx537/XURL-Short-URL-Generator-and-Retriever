package com.crio.shorturl;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XUrlImpl implements XUrl{
    
    private String alphaNumeric = "abc111111";
    private Map<String , String> shortToLongUrl;
    private Map<String ,Integer> longUrlCount;

    public XUrlImpl(){
        shortToLongUrl =new HashMap<String , String>();
        longUrlCount =new HashMap<String , Integer>();
    }

    // Now we have write a method to increment the 
    // alphanumeric string 
    private void incrementAlphaNumeric(){

        //Extract the numeric part of the string (last value)
        Pattern pattern = Pattern.compile( "(\\d+)$");
        Matcher matcher = pattern.matcher(alphaNumeric);
        matcher.find();
        String numericPart = matcher.group();

        //Increment the numeric part by 1
        Integer newNumeric = Integer.parseInt(numericPart) + 1;
        //Convert the new Integer part to zero -paaded String 
        String  incrementedPart = String.format("%0" + numericPart.length() + "d" ,newNumeric);

        // Replace the old numeric part with the new one 
        String newAlphaNumeric = alphaNumeric.replaceFirst( "(\\d+)$" , incrementedPart);
          
        // Now set the Increment value to the alphaNumeric 
        setNewAlphanumeric( newAlphaNumeric );
        
        // The below is just another way of assigning the newly incremented alphanumeric 
        // this.alphaNumeric = newAlphaNumeric;
    }

    // now write a method to set the alphanumeric value 
    public void setNewAlphanumeric(String newAplhaNumeric){
        this.alphaNumeric = newAplhaNumeric;
    }

    @Override
    public String registerNewUrl(String longUrl) {
        // TODO Auto-generated method stub
        
        // First we will Check if the LongUrl is alreadu registered
        for(Map.Entry<String ,String> entry : shortToLongUrl.entrySet() ){
            // If we find the correspondng url 
            if( entry.getValue().equals(longUrl) ){
              // then we will return the short url for the same 
              return entry.getKey();
            }
        }
        
        // Other wise we will register the longUrl in the Map 
        // in the format , shortUrl First then LongUrl 

        String ans = "http://short.url/"+alphaNumeric;
         
        // store in the map 
        shortToLongUrl.put( ans , longUrl);

        // then increment the alphanumeric for storing the next 
        incrementAlphaNumeric();
        
        // return the corresponding shortUrl
        return ans;
    }

    @Override
    public String registerNewUrl(String longUrl, String shortUrl) {
        // TODO Auto-generated method stub

        // If the short Url already exist return null
        for(Map.Entry<String , String> entry: shortToLongUrl.entrySet() ){

            // find the short url if already exist 
            if( entry.getKey().equals(shortUrl) ){
                return null;
            }
        }

        // If the shortUrl is not present then register the mapping 
        // and return the shortUrl
        
        // register
        shortToLongUrl.put( shortUrl , longUrl);
        
        // return 
        return shortUrl;
    }

    @Override
    public String getUrl(String shortUrl) {
        // TODO Auto-generated method stub

        for(Map.Entry<String , String> entry: shortToLongUrl.entrySet() ){
            // find the corresponding short url 
            if( entry.getKey().equals(shortUrl) ){
                // Before retrning the longUrl 
                // We have to update the lonUrl Count also  
                longUrlCount.put( entry.getValue() , longUrlCount.getOrDefault(entry.getValue() , 0) + 1 );

                // then return the corresponding short Url 
                return entry.getValue();
            }
        }

        // if the long Url is not found corresponding to the 
        // short Url then return null in that case 
        return null;
    }

    @Override
    public Integer getHitCount(String longUrl) {
        // TODO Auto-generated method stub
        
        // Find the count corresponding to the Url 
        // and return the count 
        // If the longUrl does not exist in the map 
        // that means we have never hit the Url 
        // in that case return null 
        for(Map.Entry<String , Integer> entry: longUrlCount.entrySet() ){

            if( entry.getKey().equals(longUrl) ){
                return entry.getValue();
            }
        }

        // If we return 0 that means we have not hit the Url even onece
        return 0;
    }

    @Override
    public String delete(String longUrl) {
        // TODO Auto-generated method stub
        
        // save the Key to be deleted 
        String delete= "";
        for(Map.Entry<String , String> entry: shortToLongUrl.entrySet() ){

            // find the corresponding key 
            if( entry.getValue().equals(longUrl) ){
                 delete = entry.getKey() ;
            }
        }

         if( shortToLongUrl.containsKey(delete) ){
            shortToLongUrl.remove(delete);
         }

        return null;
    }

}