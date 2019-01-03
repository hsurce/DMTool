package XMLHandler;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


public class XMLHandler {
    private HashMap<String, Monster> monsterHashMap = null;
    private HashMap<String, Spell> spellHashMap = null;
    private final String path = "XMLHandler/Directories";
    private String encodedJarFile = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
    private File jarFile = new File(URLDecoder.decode(encodedJarFile, "UTF-8"));
    int count = 0;

    public XMLHandler() throws IOException {
        System.out.println(jarFile);
        System.out.println(jarFile.isFile());
        Start();
    }

    public void Start() throws IOException {


        if(jarFile.isFile()) {  // Run with JAR file
            final JarFile jar = new JarFile(jarFile);
            final Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries in jar
            while (entries.hasMoreElements()) {
                final String name = entries.nextElement().getName();
                if (name.startsWith(path + "/")) { //filter according to the path
                    if(name.endsWith(".bin")){
                        System.out.println("hej");
                        Parse(extract(name));
                        count++;
                    }
                }
            }
            jar.close();
        }
        else { // Run with IDE
            File folder = null;
            try {
                folder = new File(this.getClass().getResource("Directories/").toURI());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            File[] fileNames = new File[0];
            if (folder != null) {
                fileNames = folder.listFiles();
            }
            for (File file : fileNames) {
                // if directory call the same method again
                Parse(file);
                count++;

            }
        }
    }
    public void Parse(File file) throws IOException {

        FileInputStream fis = null;
        ObjectInputStream ois;
        fis = new FileInputStream(file);
        ois = new ObjectInputStream(fis);
        try {
            switch(count) {
                case 0:
                    monsterHashMap = (HashMap<String, Monster>)ois.readObject();
                    break;
                case 1:
                    spellHashMap = (HashMap)ois.readObject();
                    break;

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
}
