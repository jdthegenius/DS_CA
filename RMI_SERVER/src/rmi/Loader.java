package rmi;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Loader {

    public String[] readFile(String filename) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("./src/rmi/"+filename));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            String everything = sb.toString();
            String[] blocks = everything.split("\n");
            return blocks;
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void saveData(String filename, String[] data) {
        BufferedWriter bw = null;
        FileWriter fw = null;

        try {
            File file = new File("./src/rmi/"+filename);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            } else {//recreate the file
                file.delete();
                file.createNewFile();
            }
            // true = append file
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);
            for (int i = 0; i < data.length; i++) {
                bw.write(data[i]);
                if (i < data.length - 1) {
                    bw.write("\n");
                }
            }
        } catch (IOException e) {

            System.out.println(e.getMessage());

        } finally {

            try {

                if (bw != null) {
                    bw.close();
                }

                if (fw != null) {
                    fw.close();
                }

            } catch (IOException ex) {

                ex.printStackTrace();

            }
        }
    }
    public void appendData(String filename, String data) {
        BufferedWriter bw = null;
        FileWriter fw = null;

        try {
            File file = new File("./src/rmi/"+filename);

            // true = append file
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);
            bw.append("\n"+data);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {

            try {

                if (bw != null) {
                    bw.close();
                }

                if (fw != null) {
                    fw.close();
                }

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}


