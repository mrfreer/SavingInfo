package com.example.android.savinginfo;

import android.os.Environment;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.textView);
        textView.setVisibility(View.INVISIBLE);

    }

    public void readMessage(View view){
        StringBuffer stringBuffer = new StringBuffer();
        try{
            String message;
            FileInputStream fileInputStream = openFileInput("hello_file");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            while((message = bufferedReader.readLine()) != null){
                stringBuffer.append(message + "\n");
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        textView.setText(stringBuffer.toString());
        textView.setVisibility(View.VISIBLE);
    }

    public void writeMessage(View view) {
        String message = editText.getText().toString();
        //create an object of FileOutputStream
        String fileName = "hello_file";
        //MODE_PRIVATE means no other application can access this file
        try {
            FileOutputStream fileOutputStream = openFileOutput(fileName, MODE_PRIVATE);
            fileOutputStream.write(message.getBytes());
            Toast.makeText(getApplicationContext(), "Message Saved", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException f) {
            f.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        public void writeExternal(View view){

            //user may have removed the SD card!
            String state = Environment.getExternalStorageState();
            if(Environment.MEDIA_MOUNTED.equals(state)){
                //we have a working external storage!
                File root = Environment.getExternalStorageDirectory();
                File dir = new File(root.getAbsolutePath() + "/MyAppFile");
                if(!dir.exists()){
                    dir.mkdir(); //just like linux!
                }
                File file = new File(dir, "MyMessage.txt");
                String message = editText.getText().toString();
                try  {
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    fileOutputStream.write(message.getBytes());
                    fileOutputStream.close();
                    editText.setText("");
                    Toast.makeText(getApplicationContext(), "Message saved.", Toast.LENGTH_LONG).show();
                    //let the user know!
                }
                catch (FileNotFoundException e){
                    e.printStackTrace();
                }
                catch (IOException o){
                    o.printStackTrace();
                }


            }
            else{
                Toast.makeText(getApplicationContext(), "SD Card not found", Toast.LENGTH_LONG).show();

            }

    }

        public void readExternal(View view){
            File root = Environment.getExternalStorageDirectory();
            File dir = new File(root.getAbsolutePath() + "/MyAppFile");
            File file = new File(dir, "MyMessage.txt");
            String message;
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuffer stringBuffer = new StringBuffer();
                while((message = bufferedReader.readLine()) != null){
                    stringBuffer.append(message + "\n");
                }
                textView.setText(stringBuffer.toString());
                textView.setVisibility(View.VISIBLE);
            }
            catch (FileNotFoundException e){
                e.printStackTrace();
            }
            catch (IOException o){
                o.printStackTrace();
            }
        }

        //filename


}
