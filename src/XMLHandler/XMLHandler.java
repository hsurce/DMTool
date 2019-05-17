package XMLHandler;

import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;


public class XMLHandler {
    private HashMap<String, Monster> monsterHashMap = null;
    private HashMap<String, Spell> spellHashMap = null;
    private HashMap<String, Monster> customMonsterHashMap = null;
    private final String path = "XMLHandler/Directories";
    private String encodedJarFile = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
    private File jarFile = new File(URLDecoder.decode(encodedJarFile, "UTF-8"));
    int count = 0;

    public XMLHandler() throws IOException {
        System.out.println(jarFile);
        System.out.println(jarFile.isFile());
        Start();
        monsterHashMap.putAll(customMonsterHashMap);
    }

    public void Start() {
        try {
    /*
     * Set the file name with it's
     * specific location.
     */
            String tmpPath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
            File tmpFile = new File(tmpPath);
            tmpFile = tmpFile.getParentFile();

            String file = tmpFile.getAbsolutePath() + "/Directories/";
            File absFile = new File(file);
            for (File f : absFile.listFiles()) {
                Parse(f);
                count++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Parse(File file) throws IOException {

        FileInputStream fis = null;
        ObjectInputStream ois;
        fis = new FileInputStream(file);
        ois = new ObjectInputStream(fis);
        System.out.println(file.getName());
        try {
            if(file.getName().equals("customMonsters.bin")) {
                customMonsterHashMap = (HashMap<String, Monster>) ois.readObject();
            }
            if(file.getName().equals("MonstersManual.bin")){
                monsterHashMap = (HashMap<String, Monster>) ois.readObject();
            }
            if(file.getName().equals("Spells.bin")) {
                spellHashMap = (HashMap) ois.readObject();
            }



            /**
             int content;
             while ((content = fis.read()) != -1) {
             // convert to char and display it
             System.out.print((char) content);
             }
             */

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


    public File extract(String filePath) {
        try {
            File f = File.createTempFile(filePath, null);
            FileOutputStream resourceOS = new FileOutputStream(f);
            byte[] byteArray = new byte[1024];
            int i;
            InputStream classIS = getClass().getClassLoader().getResourceAsStream(filePath);
//While the input stream has bytes
            while ((i = classIS.read(byteArray)) > 0) {
//Write the bytes to the output stream
                resourceOS.write(byteArray, 0, i);
            }
//Close streams to prevent errors
            classIS.close();
            resourceOS.close();
            return f;
        } catch (Exception e) {
            System.out.println("An error has occurred while extracting the database. This may mean the program is unable to have any database interaction, please contact the developer.\nError Description:\n" + e.getMessage());
            return null;
        }
    }

    public HashMap<String, Spell> getSpellHashMap() {
        return spellHashMap;
    }

    public HashMap<String, Monster> getMonsterHashMap() {
        return monsterHashMap;
    }

    public HashMap<String, Monster> getCustomMonsterHashMap() {return customMonsterHashMap; }
}