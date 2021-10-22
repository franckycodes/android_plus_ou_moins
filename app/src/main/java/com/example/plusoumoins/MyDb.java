package com.example.plusoumoins;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStreamReader;


public class MyDb {

    private String m_filename;
    public MyDb()
    {
        m_filename="db.rov";
    }
    public MyDb(String filename)
    {
        m_filename=filename+".rov";
    }

    public void saveFile(String mycontent) throws IOException {
        FileOutputStream fos=null;
        File file;

        try {
            file = new File(m_filename);

            fos = new FileOutputStream(file);

            if (!file.exists()) {
                file.createNewFile();
            }

            fos.write(mycontent.getBytes());
            fos.flush();
        }catch(IOException e)
        {

        }finally{
            try{
                if(fos!=null)
                {
                    fos.close();
                }
            }catch(IOException e)
            {

            }
        }
    }

    public String loadFile() throws IOException
    {
        FileInputStream fis;
        File myfile=new File(m_filename);

        fis=new FileInputStream(myfile);
        InputStreamReader isr=new InputStreamReader(fis);

        BufferedReader br=new BufferedReader(isr);

        StringBuilder sb=new StringBuilder();

        String mytext;

        while((mytext=br.readLine())!=null)
        {
            sb.append(mytext).append("\n");

        }
        return sb.toString();
    }
}
