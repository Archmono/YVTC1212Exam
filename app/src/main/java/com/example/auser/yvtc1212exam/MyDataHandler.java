package com.example.auser.yvtc1212exam;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by auser on 2017/12/12.
 */

public class MyDataHandler extends DefaultHandler {
    boolean inTitle = false;
    boolean inLink = false;
    boolean inItem = false;
    boolean inDesc = false;
    public ArrayList<String> titles = new ArrayList();
    public ArrayList<String> links = new ArrayList();
    public ArrayList<String> imgs = new ArrayList();
    public ArrayList<String> desc = new ArrayList();

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if (qName.equals("item"))
        {
            inItem = true;
        }
        if (qName.equals("title"))
        {
            inTitle = true;
        }
        if (qName.equals("link"))
        {
            inLink = true;
        }
        if (qName.equals("description"))
        {
            inDesc = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (qName.equals("item"))
        {
            inItem = false;
        }
        if (qName.equals("title"))
        {
            inTitle = false;
        }
        if (qName.equals("link"))
        {
            inLink = false;
        }
        if (qName.equals("description"))
        {
            inDesc = false;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);

        if (inTitle && inItem)
        {
            String data = new String(ch, start, length);
            Log.d("MyTitle", data);
            titles.add(data);
        }

        if (inLink && inItem)
        {
            String linksData = new String(ch, start, length);
            Log.d("MyLink", linksData);
            links.add(linksData);
        }

        String conData;
        String imgData;
        if (inDesc && inItem)
        {
            String data = new String(ch, start, length);
            Log.d("DATA",data);
            if(data.indexOf("<p>") != -1){
                String temp = data;
                Log.d("ddda", temp + "");

                imgData = data.substring((data.indexOf("src='")+5),(data.indexOf("'>")));
                Log.d("dddb",imgData + "");

                String temp2 = temp.substring((temp.indexOf("</p><p>")+7));
//                Log.d("dddc", temp);
                conData = temp2.substring(0,(temp2.indexOf("</p>")));

                Log.d("dddc",conData + "");

                imgs.add(imgData);
                desc.add(conData);
            } else {
                String noPic = data;
                Log.d("dddd", noPic + "");
                imgs.add("R.drawable/por.png");
                desc.add(noPic);
            }
        }
    }
}
