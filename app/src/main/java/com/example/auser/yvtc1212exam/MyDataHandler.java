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
    StringBuilder titleTemp = new StringBuilder();
    StringBuilder linkTemp = new StringBuilder();
    StringBuilder descTemp = new StringBuilder();
    StringBuilder imgTemp = new StringBuilder();

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
            if(inItem){
                titles.add(titleTemp.toString());
                titleTemp = new StringBuilder();
            }
        }
        if (qName.equals("link"))
        {
            inLink = false;
            if(inItem) {
                links.add(linkTemp.toString());
                linkTemp = new StringBuilder();
            }
        }
        if (qName.equals("description"))
        {
            inDesc = false;
            if(inItem) {
                if (!(descTemp.indexOf("<p>") == -1)) {
                    String temp = descTemp.toString();
                    String imgData = temp.substring((temp.indexOf("src='") + 5), (temp.indexOf("'>")));

                    String temp2 = temp.substring((temp.indexOf("</p><p>") + 7));

                    String conData = temp2.substring(0, (temp2.indexOf("</p>")));

                    imgs.add(imgData);
                    desc.add(conData);
                    descTemp = new StringBuilder();
                } else {
                    String noPic = descTemp.toString();
                    imgs.add("R.drawable/por.png");
                    desc.add(noPic);
                    descTemp = new StringBuilder();
                }
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);

        if (inTitle && inItem)
        {
            String data = new String(ch, start, length);
            titleTemp.append(data);
        }

        if (inLink && inItem)
        {
            String linksData = new String(ch, start, length);
            linkTemp.append(linksData);
        }

        if (inDesc && inItem)
        {
            String data = new String(ch, start, length);
            descTemp.append(data);
        }
    }
}
