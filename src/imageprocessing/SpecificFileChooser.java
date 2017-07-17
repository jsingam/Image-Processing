/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imageprocessing;

import java.awt.Component;
import java.awt.HeadlessException;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Jeyanthasingam
 */
public class SpecificFileChooser extends JFileChooser{
    
    public SpecificFileChooser(){
        super();
        this.addChoosableFileFilter(new FileNameExtensionFilter("JPG", "JPG", "JPEG"));
        this.addChoosableFileFilter(new FileNameExtensionFilter("PNG", "PNG"));
        
    }
    
    protected JDialog createDialog(Component parent)
                throws HeadlessException {
            JDialog dialog = super.createDialog(parent);
            dialog.setLocation(20, 20);
            return dialog;
        }
    
}
