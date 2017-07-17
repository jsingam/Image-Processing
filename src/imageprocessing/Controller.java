/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imageprocessing;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.util.Arrays;
import java.util.Stack;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Jeyanthasingam
 */
public abstract class Controller {
    
    static JFileChooser chooser;
    static BufferedImage image;
    static BufferedImage limage;
    static BufferedImage oimage;
    static JLabel imagepanel;
    static double mul=1.0;
    static Stack undo=new Stack();
    static Stack redo=new Stack();
    static boolean enableRedo=false;
    
    
    
    public static void openfiles(){
        chooser= new SpecificFileChooser();
        if (chooser.showOpenDialog(chooser) == JFileChooser.APPROVE_OPTION) {
            try {
                image = ImageIO.read(chooser.getSelectedFile());
                display(image);
                oimage=image;
                limage=image;
                undo.push(image);
                mul=1;
                
            } catch (IOException e) {
                JOptionPane.showMessageDialog(chooser, "This is not an image!", "Error", JOptionPane.WARNING_MESSAGE);
            }

        }
    }
    
    
    public static void display(BufferedImage img){
        imagepanel.setSize(img.getWidth(),img.getHeight());
        imagepanel.setIcon(new ImageIcon(img));
        image=img;
    }
    

    public static void intiate(){
        mainframe man=new mainframe();
        imagepanel=man.getLabel();
        man.setVisible(true);
    }
    
    public static void rotateleft(){
        BufferedImage temp= new BufferedImage(image.getHeight(),image.getWidth(),BufferedImage.TYPE_INT_RGB);
        for(int x=0; x<image.getHeight();x++){
            for(int y=0; y<image.getWidth();y++){
                temp.setRGB(x,image.getWidth()-y-1,image.getRGB(y, x));
            }
        }
        display(temp);
        limage=temp;
        
    }
    
    public static void rotateright(){
        BufferedImage temp= new BufferedImage(image.getHeight(),image.getWidth(),BufferedImage.TYPE_INT_RGB);
        for(int x=0; x<image.getHeight();x++){
            for(int y=0; y<image.getWidth();y++){
                temp.setRGB(image.getHeight()-x-1,y,image.getRGB(y, x));
            }
        }
        display(temp);
        limage=temp;
    }
    
    public static void verticalFlip(){
        BufferedImage temp= new BufferedImage(image.getHeight(),image.getWidth(),BufferedImage.TYPE_INT_RGB);
        for(int x=0; x<image.getHeight();x++){
            for(int y=0; y<image.getWidth();y++){
                temp.setRGB(x,y,image.getRGB(x,image.getHeight()-1-y));
            }
        }
        display(temp);
        limage=temp;
        
    }
    
    
    public static void horizFlip(){
        BufferedImage temp= new BufferedImage(image.getHeight(),image.getWidth(),BufferedImage.TYPE_INT_RGB);
        for(int x=0; x<image.getHeight();x++){
            for(int y=0; y<image.getWidth();y++){
                temp.setRGB(x,y,image.getRGB(image.getWidth()-1-x,y));
            }
        }
        display(temp);
        limage=temp;
    }
    
    
    public static void scale2down(){
        if(mul<8){
            mul*=2;
            BufferedImage temp= new BufferedImage((int)(limage.getWidth()/mul),(int)(limage.getHeight()/mul),BufferedImage.TYPE_INT_RGB);
            System.out.println((int)(limage.getHeight()/mul)+"  "+(int)(limage.getWidth()/mul)+"  "+limage.getHeight()+"  "+limage.getWidth());
            if(mul>0.9){
            
            for(int x=0; x<limage.getWidth();x+=mul){
                for(int y=0; y<limage.getHeight();y+=mul){
                    if((int)(x/mul)<(int)(limage.getWidth()/mul) && (int)(y/mul)<(int)(limage.getHeight()/mul)){
                    temp.setRGB((int)(x/mul),(int)(y/mul),limage.getRGB(x,y));
                    }
                }
            }

            }else{
                            for(int x=0;x<(int)(limage.getHeight()/mul);x++){
                for(int y=0;y<(int)(limage.getWidth()/mul);y++){
                    temp.setRGB(x, y, limage.getRGB((int)(x*mul),(int)(y*mul)));
                }
            }
            }
                        undo.push(temp);
            enableRedo=false;
            display(temp);
        }
    }
    
    
        public static void scale2up(){
        if(mul>0.25){
        mul/=2;
        System.out.println(mul);
        BufferedImage temp= new BufferedImage((int)(limage.getHeight()/mul),(int)(limage.getWidth()/mul),BufferedImage.TYPE_INT_RGB);
        if(mul>0.9){
        
        for(int x=0; x<limage.getHeight();x+=mul){
            for(int y=0; y<limage.getWidth();y+=mul){
                if((int)(x/mul)<(int)(limage.getWidth()/mul) && (int)(y/mul)<(int)(limage.getHeight()/mul)){
                    temp.setRGB((int)(x/mul),(int)(y/mul),limage.getRGB(x,y));
                    }
            }
        }
        
        }
        else{
            for(int x=0;x<(int)(limage.getHeight()/mul);x++){
                for(int y=0;y<(int)(limage.getWidth()/mul);y++){
                    temp.setRGB(x, y, limage.getRGB((int)(x*mul),(int)(y*mul)));
                }
            }
        }
        undo.push(temp);
        enableRedo=false;
        display(temp);
        }
        
        
        
        
    }
        public static void undo(){
            redo.push(image);
            enableRedo=true;
            image=(BufferedImage) undo.pop();
            display(image);
        }
        
        public static void redo(){
            if(enableRedo){
                undo.push(image);
                image=(BufferedImage) redo.pop();
                undo.push(image);
                display(image);
                
            }
            
        }
        
        public static void preview(int X,int Y,int x,int y){
            System.out.println("preview"+X+" "+Y+" "+x+" "+y);
            int pX= (int) image.getWidth()*X/100;
            
            int pY=  (int) image.getHeight()*Y/100;
            int px=  (int) image.getWidth()*x/100;
            int py=  (int) image.getHeight()*y/100;
            BufferedImage temp= deepCopy(image);
            for(int i=0; i<px ; i++){
                for(int z=-2;z<0;z++){
                    if(pY+z>0 && pY+py+z<temp.getHeight()){
                        temp.setRGB(pX+i, pY+z, 0);
                        temp.setRGB(pX+i,pY+py+z,0);
                    }
                }
            }
            for(int i=0; i<py ; i++){
                for(int z=-2;z<0;z++){
                    if(pX+z>0 && pX+px+z<temp.getWidth()){
                        temp.setRGB(pX+z, pY+i, 0);
                        temp.setRGB(pX+px+z,pY+i,0);
                    }
                }
            }
            
            imagepanel.setIcon(new ImageIcon(temp));
            
        }
        
        
    static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
    
    
    public static void crop(int X,int Y,int px,int py){
            X= (int) image.getWidth()*X/100;
            
            Y=  (int) image.getHeight()*Y/100;
            px=  (int) image.getWidth()*px/100;
            py=  (int) image.getHeight()*py/100;
        BufferedImage temp= new BufferedImage(px,py,BufferedImage.TYPE_INT_RGB);
        System.out.println("preview selected"+X+" "+Y+" "+px+" "+py);
        for(int x=0;x<px;x++){
            for(int y=0;y<py;y++){
                temp.setRGB(x, y, image.getRGB(X+x, Y+y));
            }
            
        }
        
        limage=image;
        display(temp);
    }
    
    public static void mean(){
        image=ImageProcessing.meanfil(image);
        limage=ImageProcessing.meanfil(limage);
        display(image);
    }
    
        public static void median(){
        image=ImageProcessing.medianfilter(image);
        limage=ImageProcessing.medianfilter(limage);
        display(image);
    }
        
        public static void grey(){
            image=ImageProcessing.greyscale(image);
            limage=ImageProcessing.greyscale(limage);
            display(image);
        }
        
        public static void edgeS(){
            image=ImageProcessing.edgeS(image);
            limage=ImageProcessing.edgeS(limage);
            display(image);
        }
        
        public static void gaussian(){
            image=ImageProcessing.gaussian(image);
            limage=ImageProcessing.gaussian(limage);
            display(image);
        }
        
        public static void canny(){
             image=ImageProcessing.canny(image);
            limage=ImageProcessing.canny(limage);
            display(image);
        }
        
        
        public static void cfun(float val){
            
            image=function(val);
            display(image);
        }
        
        
        
        
        public static BufferedImage function(float val){
            mul/=val;
            int x1,x2,y1,y2,col,r,g,b;
            double xf,yf;
            int[][] red=new int[limage.getWidth()][limage.getHeight()];
            int[][] green=new int[limage.getWidth()][limage.getHeight()];
            int[][] blue=new int[limage.getWidth()][limage.getHeight()];
            BufferedImage temp = new BufferedImage((int)(limage.getWidth()/mul),(int)(limage.getHeight()/mul),BufferedImage.TYPE_INT_RGB);
           for(int x=0;x<limage.getWidth();x++){
                for(int y=0; y<limage.getHeight();y++){
                    red[x][y]= new Color(limage.getRGB(x,y)).getRed();
                    green[x][y]= new Color(limage.getRGB(x,y)).getGreen();
                    blue[x][y]= new Color(limage.getRGB(x,y)).getBlue();
                    System.out.println(x+"  " +y+"  "+red[x][y]+"  "+green[x][y]+"  "+blue[x][y]);
                } 
           }
            
            for(int x=0;x<temp.getWidth();x++){
                for(int y=0; y<temp.getHeight();y++){
                    
                    xf=x*mul;
                    yf=y*mul;
                    x1=(int)(xf);
                    y1=(int)(yf);
                    x2=(int)Math.ceil(xf);

                    
                    y2=(int)Math.ceil(yf);

                    if(x2<limage.getWidth() && y2<limage.getHeight()){
                        if(x1==x2 && y1==y2){
                            r=(int) red[x1][y1];
                            g=(int) green[x1][y1];
                            b=(int) blue[x1][y1];
                        }
                        else if(x1==x2){
                            r=(int)((y2-yf)*red[x1][y1]
                                +(yf-y1)*red[x2][y2]);
                        g=(int)((y2-yf)*green[x1][y1]
                                +(yf-y1)*green[x2][y2]);
                        b=(int)((y2-yf)*blue[x1][y1]
                                +(yf-y1)*blue[x2][y2]);
                            
                        }
                        else if(y1==y2){
                            r=(int)((x2-xf)*red[x1][y1]
                                +(xf-x1)*red[x2][y2]);
                        g=(int)((x2-xf)*green[x1][y1]
                                +(xf-x1)*green[x2][y2]);
                        b=(int)((x2-xf)*blue[x1][y1]
                                +(xf-x1)*blue[x2][y2]);
                        }
                        else{
                        
                            r=(int)((x2-xf)*(y2-yf)*red[x1][y1]
                                    +(xf-x1)*(y2-yf)*red[x2][y1]
                                    +(xf-x1)*(yf-y1)*red[x2][y2]
                                    +(x2-xf)*(y2-yf)*red[x1][y2]);
                            g=(int)((x2-xf)*(y2-yf)*green[x1][y1]
                                    +(xf-x1)*(y2-yf)*green[x2][y1]
                                    +(xf-x1)*(yf-y1)*green[x2][y2]
                                    +(x2-xf)*(y2-yf)*green[x1][y2]);
                            b=(int)((x2-xf)*(y2-yf)*blue[x1][y1]
                                    +(xf-x1)*(y2-yf)*blue[x2][y1]
                                    +(xf-x1)*(yf-y1)*blue[x2][y2]
                                    +(x2-xf)*(y2-yf)*blue[x1][y2]);
                        }
                        
                        System.out.println(x+"  "+y+"  "+xf+"  "+yf+"  "+x1+"  "+y1+"  "+x2+"  "+y2+"  "+r+"  "+g+"  "+b);
                        try{
                            temp.setRGB(x,y,new Color(r,g,b).getRGB());
                        }catch(Exception e){
                            temp.setRGB(x,y,Color.WHITE.getRGB());
                        }
                    }
                    
                }
            }
            return temp;
            
        }
        



}