package com.company;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    public static Map<String, String> AVdata = new HashMap<String, String>();
    public static Map<String, String> VAdata = new HashMap<String, String>();
    public static ArrayList<String> historyData = new ArrayList<>();
    public static String AnhViet = "Anh_Viet.xml";
    public static String VietAnh = "Viet_Anh.xml";
    public static String AnhVietNew = AnhViet;
   // public static String VietAnhNew = VietAnh;

 	//  public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        // write your code here
        readHistoryLog ((ArrayList<String>) historyData);
        while (true) {
            System.out.println("DICTIONARY");
            System.out.println("1.Eng-Vie");
         //   System.out.println("2.Vie-Eng");
            System.out.println("3.History");
            System.out.println("4.Exit"+ "\n");
            System.out.println("Your Option:");
            Scanner scanner = new Scanner(System.in);
            int result = scanner.nextInt();
            switch(result)
            {
                case(1):
                {
                    if(AVdata.isEmpty())
                    {
                        getData(AnhViet, (HashMap<String, String>) AVdata);
                    }
                    boolean flag = true;

                    while(flag)
                    {
                        System.out.println("END-VIE DICTIONARY");
                        System.out.println("1.Search");
                        System.out.println("2.Add new Word");
                        System.out.println("3.Delete Word");
                        System.out.println("4.Main Menu");
                        System.out.println("5.Exit");
                        System.out.println("Your Option:");
                        Scanner scanner1 = new Scanner(System.in);
                        switch(scanner1.nextInt())
                        {
                            case(1):
                            {
                                System.out.println("Word to search:");
                                Scanner scanner2 = new Scanner(System.in);
                                String keyWord = scanner2.nextLine();
                                System.out.println(findWord(keyWord.toLowerCase(), (HashMap<String, String>) AVdata));
                                System.out.println("Press s to save this word to favorite or any other key to continue");
                                writeHistoryData(keyWord, (ArrayList<String>) historyData);
                                Scanner save = new Scanner(System.in);
                                String event = save.nextLine();
                                saved(event, keyWord);
                                break;
                            }
                            case(2):
                            {
                                System.out.println("ADD NEW WORD");
                                Scanner scanner2 = new Scanner(System.in);
                                System.out.println("Key:");
                                String key = scanner2.nextLine();
                                System.out.println("Meaning:");
                                String value = scanner2.nextLine();
                                if(addWord(key, value, (HashMap<String, String>) AVdata))
                                {
                                    System.out.println("Add new word successful"); //snap shot
                                }
                                else
                                {
                                    System.out.println("Add new word failed, please try again");
                                }
                                PressAnyKeytoContinue();
                                break;
                            }
                            case(3):
                            {
                                System.out.println("DELETE WORD");
                                Scanner scanner2 = new Scanner(System.in);
                                System.out.println("Enter Word to delete:");
                                String key = scanner2.nextLine();
                                System.out.println("Are you sure to delete " + key + " ? [y/n]: ");
                                String yn = scanner2.nextLine();
                                yn.toLowerCase();
                                if(yn.equals("y"))
                                {
                                    if(delWord(key, (HashMap<String, String>) AVdata))
                                    {
                                        System.out.println("Delete Word Successful");
                                    }
                                    else
                                    {
                                        System.out.println("Delete failed, Please try again");
                                    }
                                }
                                PressAnyKeytoContinue();
                                break;
                            }
                            case(4):
                            {
                                writeFileData(AnhViet, (HashMap<String, String>) AVdata);
                                flag = false;
                                break;
                            }
                            case(5):
                            {
                                writeHistoryLog((ArrayList<String>) historyData);
                                System.exit(0);
                            }
                            default:{
                                System.out.println("Please try again");
                            }
                        }
                    }
                    break;
                }
                case(2):
                {
                    if(VAdata.isEmpty())
                    {
                        getData(VietAnh, (HashMap<String, String>) VAdata);
                    }
                    boolean flag = true;
                    while(flag)
                    {
                        System.out.println("VIE-ENG DICTIONARY");
                        System.out.println("1.Search");
                        System.out.println("2.Add new Word");
                        System.out.println("3.Delete Word");
                        System.out.println("4.Back to main menu");
                        System.out.println("5.Exit");
                        System.out.println("Enter your option:");
                        Scanner scanner1 = new Scanner(System.in);
                        switch(scanner1.nextInt())
                        {
                            case(1):
                            {
                                System.out.println("Word to search");
                                Scanner scanner2 = new Scanner(System.in);
                                String keyWord = scanner2.nextLine();
                                System.out.println(findWord(keyWord.toLowerCase(), (HashMap<String, String>) VAdata));
                                System.out.println("Press s to save this word to favorite or any other key to continue");
                                Scanner save = new Scanner(System.in);
                                String event = save.nextLine();
                                saved(event, keyWord);
                                writeHistoryData(keyWord, (ArrayList<String>) historyData);
                                break;
                            }
                            case(2):
                            {
                                System.out.println("ADD NEW WORD");
                                Scanner scanner2 = new Scanner(System.in);
                                System.out.println("Key:");
                                String key = scanner2.nextLine();
                                System.out.println("Meaning:");
                                String value = scanner2.nextLine();
                                if(addWord(key, value, (HashMap<String, String>) VAdata))
                                {
                                    System.out.println("Add new word successful");
                                }
                                else
                                {
                                    System.out.println("Add new word failed, please try again");
                                }
                                PressAnyKeytoContinue();
                                break;
                            }
                            case(3):
                            {
                                PressAnyKeytoContinue();
                                break;
                            }
                            case(4):
                            {
                                writeFileData(VietAnh, (HashMap<String, String>) VAdata);
                                flag = false;
                                break;
                            }
                            case(5):
                            {
                                writeHistoryLog((ArrayList<String>) historyData);
                                System.exit(0);
                            }
                            default:{
                                System.out.println("Please try again");
                            }
                        }
                    }
                }
                case(3):
                {
                    printHistory((ArrayList<String>) historyData);
                    break;
                }
                case(4):
                {
                    writeHistoryLog((ArrayList<String>) historyData);
                    System.exit(0);
                }
                default:{
                    System.out.println("Please try again");
                }

            }
        }
    }
    public static void getData(String fileName, HashMap<String, String> dataFile) throws ParserConfigurationException, IOException, SAXException {
        File file = new File(fileName);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(file);
        document.getDocumentElement().normalize();
        NodeList keyValueNodes = document.getElementsByTagName("record");
        for(int i=0;i<keyValueNodes.getLength();i++) {
            Node keyValueNode = keyValueNodes.item(i);
            if(keyValueNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element keyValueElemet = (Element) keyValueNode;
                String word = keyValueElemet.getElementsByTagName("word").item(0).getTextContent();
                String meaning = keyValueElemet.getElementsByTagName("meaning").item(0).getTextContent();
                dataFile.put(word, meaning);
            }
        }
    }
   public static String findWord(String key, HashMap<String, String> dataFile)
   {
       if(dataFile.get(key) != null)
       {
           return dataFile.get(key);
       }
       else
       {
           return "Not Found";
       }
   }
   public static boolean addWord( String key, String value, HashMap<String, String> data)
   {
       data.put(key, value);
       return true;
   }
   public static boolean delWord(String key, HashMap<String, String> data) {
       if (findWord(key, data).equals("Not Found")) {
           System.out.println("Not Found");
           return false;
       } else {
           data.remove(key);
           return true;
       }
   }
   public static void writeFileData(String fileName, HashMap<String, String> data){
       try {

           DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
           Document dom = builder.newDocument();

           Element root = dom.createElement("dictionary");
           dom.appendChild(root);
           for (Map.Entry keyValue : data.entrySet())
           {
               Element record = dom.createElement("record");
               Element word = dom.createElement("word");
               word.setTextContent((String) keyValue.getKey());
               Element meaning = dom.createElement("meaning");
               meaning.setTextContent((String) keyValue.getValue());
               record.appendChild(word);
               record.appendChild(meaning);
               root.appendChild(record);
           }

           Transformer tr = TransformerFactory.newInstance().newTransformer();
           tr.setOutputProperty(OutputKeys.INDENT, "yes");
           tr.transform(new DOMSource(dom), new StreamResult(new File(fileName)));
       } catch (Exception ex) {
       }
   }
   public static void PressAnyKeytoContinue()
   {
       System.out.println("Press any key to continue");
       try
       {
           System.in.read();
       }
       catch(Exception e)
       {

       }
   }
   public static void saved(String save, String data)
   {
       if(save.toLowerCase().equals("s"))
       {
           if(saveFavorite(data))
           {
               System.out.println("Saved");
           }
            else
           {
               System.out.println("Save failed, please try again");
           }
       }
       else {
           //PressAnyKeytoContinue();
       }
   }
   public static boolean saveFavorite(String data) {
       File favorite = new File("favorite.txt");
           try{
               if(!favorite.exists())
               {
                   favorite.createNewFile();
               }
               FileWriter fileWriter = new FileWriter("favorite.txt", true);
               fileWriter.write(data);
               fileWriter.write("\n");
               fileWriter.close();
           }
           catch (IOException e)
           {
               return false;
           }
           return true;
   }
   public static void readHistoryLog(ArrayList<String> historyData) throws ParserConfigurationException, IOException, SAXException {
       File file = new File("history.xml");
       DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
       DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
       Document document = documentBuilder.parse(file);
       document.getDocumentElement().normalize();
       NodeList keyValueNodes = document.getElementsByTagName("log");
       for(int i=0;i<keyValueNodes.getLength();i++) {
           Node keyValueNode = keyValueNodes.item(i);
           if(keyValueNode.getNodeType() == Node.ELEMENT_NODE)
           {
               Element keyValueElemet = (Element) keyValueNode;
               String key = keyValueElemet.getElementsByTagName("key").item(0).getTextContent();
               String date = keyValueElemet.getElementsByTagName("date").item(0).getTextContent();
               String times = keyValueElemet.getElementsByTagName("times").item(0).getTextContent();
               historyData.add(key);
               historyData.add(date);
               historyData.add(times);
           }
       }
   }
   public static void writeHistoryLog(ArrayList<String> historyData)
   {
       try {

           DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
           Document dom = builder.newDocument();
           Element root = dom.createElement("root");

           dom.appendChild(root);
           for (String keyValue : historyData)
           {
               Element log = dom.createElement("log");
               Element key = dom.createElement("key");
               key.setTextContent(keyValue);
               Element date = dom.createElement("date");
               Element times = dom.createElement("times");
               date.setTextContent(keyValue);
               times.setTextContent(keyValue);
               log.appendChild(key);
               log.appendChild(date);
               log.appendChild(times);
               root.appendChild(log);
           }

           Transformer tr = TransformerFactory.newInstance().newTransformer();
           tr.setOutputProperty(OutputKeys.INDENT, "yes");
           tr.transform(new DOMSource(dom), new StreamResult(new File("history.xml")));
       } catch (Exception ex) {
       }
   }
   public static void writeHistoryData(String key, ArrayList<String> historyData)
   {
       Date date = Calendar.getInstance().getTime();
       DateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy");
       String dateString = dateFormat.format(date);
       boolean isKeyPresent = historyData.contains(key);
       if(!isKeyPresent)
       {
           historyData.add(key);
           historyData.add(dateString);
           historyData.add("1");

       }
       else
       {

           if(data.get(0).equals(dateString))
           {
               int temp = Integer.parseInt(data.get(1));
               temp++;
               data.add(dateString);
               data.add(String.valueOf(temp));
               historyData.replace(key, data);
           }
           else
           {
               data.add(dateString);
               data.add("1");
               historyData.put(key, data);
           }
       }
   }
    public static void printHistory(ArrayList<String> historyData)
    {
        final Object[][] table = new String[3][];
        System.out.println("     Word             Date                Times");
        int i=0;
        for(String entry : historyData)
        {
            String key = entry.getKey();
            ArrayList<String> value = entry.getValue();
            String date = value.get(0);
            String times = value.get(1);
            table[i] = new String[]{key, date, times};
            i++;
        }
        for(final Object[]row : table)
        {
            System.out.format("%15s%15s%15s\n", row);
        }
    }
}
