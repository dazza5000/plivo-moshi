import com.plivo.api.Plivo;
import com.plivo.api.exceptions.PlivoRestException;
import com.plivo.api.models.message.Message;
import com.plivo.api.models.message.MessageCreateResponse;
import com.plivo.api.models.message.MessageType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//package com.plivo.examples;
//
//import com.plivo.api.Plivo;
//import com.plivo.api.exceptions.PlivoRestException;
//import com.plivo.api.models.base.ListResponse;
//import com.plivo.api.models.media.Media;
//import com.plivo.api.models.media.MediaResponse;
//import com.plivo.api.models.media.MediaUploader;
//import com.plivo.api.models.message.Message;
//import com.plivo.api.models.message.MessageCreateResponse;
//import com.plivo.api.models.message.MessageType;
//import com.plivo.api.models.message.MmsMedia;
//
//import java.io.IOException;
//import java.util.Collections;
//
public class Messages {

  public static void main(String[] args) {
    Plivo.init(authId, authToken);
    sendmms();
    // getMessage();
    // listMedia();
    //getMedia();
    //deleteMedia();
//    uploadMedia();

  }
//
  // send mms
  private static void sendmms() {
    Plivo.init(authId, authToken);
    try {
      List<String> numbers = new ArrayList<>();
      numbers.add("+15126937499");
      MessageCreateResponse response = Message.creator("phone from", Collections.singletonList("phone to"), "Hello, world! What are you doing?")
        .create();
      System.out.println(response);
      System.out.println(response.getMessageUuid());
    } catch (PlivoRestException | IOException e) {
      e.printStackTrace();
    }
  }
//
//  // get message detail
//  private static void getMessage() {
//    try {
//      Message response = Message.getter("message_uuid")
//        .get();
//
//      System.out.println(response);
//
//    } catch (PlivoRestException | IOException e) {
//      e.printStackTrace();
//    }
//  }
//
//  //list media detail
//  private static void listMedia() {
//    try {
//      ListResponse<MmsMedia> response = Message.getter("message_uuid")
//        .get().listMedia().list();
//
//      System.out.println(response);
//
//    } catch (PlivoRestException | IOException e) {
//      e.printStackTrace();
//    }
//  }
//
//  // get single media
//  private static void getMedia() {
//    try {
//      MmsMedia response = Message.getter("message_uuid")
//        .get().getMedia("media_id").get();
//
//      System.out.println(response);
//
//    } catch (PlivoRestException | IOException e) {
//      e.printStackTrace();
//    }
//  }
//
//  //delete media
//  private static void deleteMedia() {
//    try {
//      Message.getter("message_uuid")
//        .get().deleteMedia().delete();
//      System.out.println("Deleted successfully.");
//
//    } catch (PlivoRestException | IOException e) {
//      e.printStackTrace();
//    }
//  }
//
//  private static void uploadMedia(){
//
//    try {
//      System.out.println("'Before Media upload");
//      MediaResponse mediaResponse = Media.creator(new String[]{"/Users/xz/Downloads/image2.png",
//    		  "/yourpath/Jira.csv.txt"}).create();
//      System.out.println("'Response got " + mediaResponse.getApiId());
//    } catch (IOException e) {
//      e.printStackTrace();
//    } catch (PlivoRestException e) {
//      e.printStackTrace();
//    }
//  }
//
//  private static  void getMedia(string mediaId){
//    try {
//      Media p = Media.getter(mediaId).get();
//       System.out.println(p);
//    } catch (IOException e) {
//      e.printStackTrace();
//    } catch (PlivoRestException e) {
//      e.printStackTrace();
//    }
//  }
//  private static  void listMedia(){
//    try {
//      ListResponse<Media> p = Media.lister().list();
//      System.out.println(p);
//    } catch (IOException e) {
//      e.printStackTrace();
//    } catch (PlivoRestException e) {
//      e.printStackTrace();
//    }
//  }
}
//
