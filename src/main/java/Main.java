import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import  java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Main {
    private static HttpURLConnection conection;
    public static void main(String[] args) {
        BufferedReader reader;
        String line;
        StringBuffer response=new StringBuffer();
        try {
            URL url=new URL("https://jsonplaceholder.typicode.com/albums");
            try {
                conection=(HttpURLConnection) url.openConnection();

                //setup
                conection.setRequestMethod("GET");
                conection.setConnectTimeout(5000);
                conection.setReadTimeout(5000);

                int status=conection.getResponseCode() ;
                System.out.println(status);

                if(status>299){
                    reader=new BufferedReader(new InputStreamReader(conection.getErrorStream()));
                    while((line=reader.readLine())!=null)
                    {
                        response.append(line);
                    }
                    reader.close();
                }else {
                    reader=new BufferedReader(new InputStreamReader(conection.getInputStream()));
                    while((line=reader.readLine())!=null)
                    {
                        response.append(line);
                    }
                    reader.close();
                }
//                System.out.println(response.toString());
                parse(response.toString());
                postRequest();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        finally {
            conection.disconnect();
        }
    }
    public static String parse(String responseBody){
        JSONArray albums=new JSONArray(responseBody);
        for(int i=0;i<albums.length();i++)
        {
            JSONObject album=albums.getJSONObject(i);
            int id=album.getInt("id");
            int userId=album.getInt("userId");
            String title=album.getString("title");
            System.out.println(id+" "+title+" "+userId);
        }
        return null;

    }
    public static void  postRequest(){


      try {
          String post_data="key=value";
          URL url=new URL("https://jsonplaceholder.typicode.com/albums");

                conection=(HttpURLConnection) url.openConnection();
                conection.setRequestMethod("POST");
                conection.setDoOutput(true);
                OutputStream outputStream=conection.getOutputStream();
                outputStream.write(post_data.getBytes());
                outputStream.flush();
                outputStream.close();

                BufferedReader readerPost;
                String linePost;
                StringBuffer responsePost=new StringBuffer();
                int status=conection.getResponseCode() ;
          if(status>299){
              readerPost=new BufferedReader(new InputStreamReader(conection.getErrorStream()));
              while((linePost=readerPost.readLine())!=null)
              {
                  responsePost.append(linePost);
              }
              readerPost.close();
          }else {
              readerPost=new BufferedReader(new InputStreamReader(conection.getInputStream()));
              while((linePost=readerPost.readLine())!=null)
              {
                  responsePost.append(linePost);
              }
              readerPost.close();

          }
          System.out.println(responsePost.toString());

      } catch (MalformedURLException e) {
          throw new RuntimeException(e);
      } catch (ProtocolException e) {
          throw new RuntimeException(e);
      } catch (IOException e) {
          throw new RuntimeException(e);
      }
    }
}
