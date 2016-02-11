
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Bart
 */
public class OutputWriter {
    
    
        public String makeLineGeneralCommand(int droneId, char command, int warehouseId, int type, int amount)
        {
            return String.format("%d %c %d %d %d", droneId, command, warehouseId, type, amount);
        }
        
        public String makeLineUnloadCommand(int droneId, int warehouseId, int type, int amount)
        {
            return makeLineGeneralCommand(droneId, 'U', warehouseId, type, amount);
        }
        
        public String makeLineLoadCommand(int droneId, int warehouseId, int type, int amount)
        {
            return makeLineGeneralCommand(droneId, 'L', warehouseId, type, amount);
        }
                
        public String makeLineDeliverCommand(int droneId, int customerId, int type, int amount)
        {
            return makeLineGeneralCommand(droneId, 'D', customerId, type, amount);
        }
        
        public String makeLineWaitCommand(int droneId, int amount)
        {
            return String.format("%d %c %d", droneId, 'W', amount);
        }
    
        public void makeOutput(List<String> commands, String fileName) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("output/"+fileName+".txt"), "utf-8"))) {
            writer.write(commands.size()+"\n");
            for(String command : commands) {
                writer.write(command+"\n");
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(OutputWriter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OutputWriter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OutputWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
