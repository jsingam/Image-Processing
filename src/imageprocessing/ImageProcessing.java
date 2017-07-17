/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imageprocessing;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;


/**
 *
 * @author Jeyanthasingam
 */
public class ImageProcessing {
    static mainframe man;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Controller.intiate();
        
        // TODO code application logic here
    }
    
    public static BufferedImage meanfil(BufferedImage img){
        BufferedImage temp= new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_INT_RGB);
        Color color;
        for(int x=0;x<img.getWidth();x++){
            for(int y=0;y<img.getHeight();y++){
                int div=0;
                int red=0;
                int green=0;
                int blue=0;
                int alpha=0;
                for(int i=-1;i<2;i++){
                    for(int j=-1;j<2;j++){
                        if(i==0 &&j==0 ){
                            color= new Color(img.getRGB(x, y));
                            red+=color.getRed()*4;
                            green+=color.getGreen()*4;
                            blue+=color.getBlue()*4;
                            alpha=color.getAlpha();
                            div+=4;
                        }
                        else if(x+i>-1 && x+i<img.getWidth() && y+j>0 && y+j<img.getHeight()){
                            if(i==0 || j==0){
                                color= new Color(img.getRGB(x+i, y+j));
                            red+=color.getRed()*2;
                            green+=color.getGreen()*2;
                            blue+=color.getBlue()*2;
                            div+=2;
                            }
                            else{
                                    color= new Color(img.getRGB(x+i, y+j));
                            red+=color.getRed();
                            green+=color.getGreen();
                            blue+=color.getBlue();
                            div+=1;
                            }
                        }

                    }
                }
                red=red/div;
                green=green/div;
                blue=blue/div;
                color= new Color(red,green,blue,alpha);
                temp.setRGB(x, y, color.getRGB());
                
                
                
            }
        }
        return temp;

    }
    
    
    
    
    public static BufferedImage medianfilter(BufferedImage img){
        BufferedImage temp= new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_INT_RGB);
        int[] values = new int[9];
        
        for(int x=0;x<img.getWidth();x++){
            for(int y=0;y<img.getHeight();y++){
                int v=0;
                for(int i=-1;i<2;i++){
                    for(int j=-1;j<2;j++){
                        if(x+i>-1 && x+i<img.getWidth() && y+j>0 && y+j<img.getHeight()){
                            values[v]=img.getRGB(x+i, y+j);
                        }else values[v]=img.getRGB(x,y);  
                        v++;
                    }
                }
               Arrays.sort(values);
               temp.setRGB(x,y,values[4]);
            }
        }
   
        
        return temp;
    }
    
    
    public static BufferedImage greyscale(BufferedImage img){
        BufferedImage temp= new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_INT_RGB);
        int red,green,blue,tot;
        for(int x=0; x<img.getWidth();x++){
            for(int y=0; y<img.getHeight();y++){
                Color color= new Color(img.getRGB(x, y));
                red=(int) (color.getRed()*0.299);
                green=(int) (color.getGreen()*0.587);
                blue=(int) (color.getBlue()*0.114);
                tot=green+red+blue;
                temp.setRGB(x, y, new Color(tot,tot,tot).getRGB());
                
            }
        }
        return temp;
    }
    
    public static BufferedImage edgeS(BufferedImage img){
        BufferedImage temp= new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_INT_RGB);
        int[][] mask1={{-1,0,1},{-2,0,2},{-1,0,1}};
        int[][] mask2={{-1,-2,-1},{0,0,0},{1,2,1}};
        int g1,g2,red,green,blue,tot,g;
    
        for(int x=0; x<img.getWidth();x++){
            for(int y=0;y<img.getHeight();y++){
                g1=0;g2=0;
                for(int i=-1;i<2;i++){
                    for(int j=-1;j<2;j++){
                        if(x+i>-1 && x+i<img.getWidth() && y+j>-1 && y+j<img.getHeight()){
//                            System.out
                            Color color= new Color(img.getRGB(x+i, y+j));
                            red=(int) (color.getRed()*0.299);
                            green=(int) (color.getGreen()*0.587);
                            blue=(int) (color.getBlue()*0.114);
                            tot=green+red+blue;
                            g1+=tot*mask1[i+1][j+1];
                            g2+=tot*mask2[i+1][j+1];
                        }

                        
                    }
                }
                
                g=255-((int)(Math.sqrt(g1*g1+g2*g2)));
                if(g<0){
                    g=0;
                }else if(g>100){
                    g=255;
                }
                temp.setRGB(x,y,new Color(g,g,g).getRGB());
                
                
            }
        }
        return temp;
        
        
    }
    
    
    public static BufferedImage gaussian(BufferedImage img){
        BufferedImage temp= new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_INT_RGB);
        int[][] mask={{2,4,5,4,2},{4,9,12,9,4},{5,12,15,12,5},{4,9,12,9,4},{2,4,5,4,2}};
        int g1,g2,red,g,green,blue,tot;
    
        for(int x=0; x<img.getWidth();x++){
            for(int y=0;y<img.getHeight();y++){
                g=0;
                red=0;
                blue=0;
                green=0;
                for(int i=-2;i<3;i++){
                    for(int j=-2;j<3;j++){
                        if(x+i>-1 && x+i<img.getWidth() && y+j>-1 && y+j<img.getHeight()){
//                            System.out
                            Color color= new Color(img.getRGB(x+i, y+j));
                            red+=color.getRed()*mask[i+2][j+2];
                            green+=color.getGreen()*mask[i+2][j+2];
                            blue+=color.getBlue()*mask[i+2][j+2];
                        }

                        
                    }
                }
                
               red=red/159;
               green=green/159;
               blue=blue/159;
               
                temp.setRGB(x,y,new Color(red,green,blue).getRGB());
                
                
            }
        }
        return temp;
    }
    
    
    
    
//    public static BufferedImage Canny(BufferedImage img){
//        BufferedImage temp= new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_INT_RGB);
//        img=gaussian(img);
//        int[][] mask1={{-1,0,1},{-2,0,2},{-1,0,1}};
//        int[][] mask2={{-1,-2,-1},{0,0,0},{1,2,1}};
//        int g1,g2,red,green,blue,tot,g,ang = 0,tan;
//        int[][] values=new int[img.getWidth()][img.getHeight()];
//        int[][] angle=new int[img.getWidth()][img.getHeight()];
//    
//        for(int x=0; x<img.getWidth();x++){
//            for(int y=0;y<img.getHeight();y++){
//                g1=0;g2=0;
//                for(int i=-1;i<2;i++){
//                    for(int j=-1;j<2;j++){
//                        if(x+i>-1 && x+i<img.getWidth() && y+j>-1 && y+j<img.getHeight()){
////                            System.out
//                            Color color= new Color(img.getRGB(x+i, y+j));
//                            red=(int) (color.getRed()*0.299);
//                            green=(int) (color.getGreen()*0.587);
//                            blue=(int) (color.getBlue()*0.114);
//                            tot=green+red+blue;
//                            g1+=tot*mask1[i+1][j+1];
//                            g2+=tot*mask2[i+1][j+1];
//                        }
//
//                        
//                    }
//                }
//                g=255-((int)(Math.sqrt(g1*g1+g2*g2)));
//                if(g<0){
//                    g=0;
//                }else if(g>100){
//                    g=255;
//                }
//                
//                tan=(int)((Math.atan2(g1,g2)/3.14159) * 180.0);
//                if((tan>-22.5 && tan<22.5) && (tan>157.5 || tan<-157.5)){
//                    ang=0;
//                }
//                else if((tan>22.5 && tan<67.5) && (tan<-122.5 || tan>-157.5)){
//                    ang=45;
//                }
//                else if((tan>67.5 && tan<122.5) && (tan<-67.5 || tan>-122.5)){
//                    ang=90;
//                }
//                else if((tan>122.5 && tan<157.5) && (tan<-22.5 || tan>-67.5)){
//                    ang=135;
//                }
//                values[x][y]=g;
//                temp.setRGB(x, y, g);
//                angle[x][y]=ang;
//                
//            }
//        }
//        
//        
//        int lowerThreshold=100;
//        int upperThreshold=200;
//        Boolean edge=false;
//         for(int x=0; x<img.getWidth();x++){
//            for(int y=0;y<img.getHeight();y++){
//                
//                edge=false;
//                if(values[x][y]>upperThreshold){
//                    switch(angle[x][y]){
//                        case 0: 
//                            findEdge(0,1,x,y,0,lowerThreshold);
//                            break;
//                        case 45:
//                            findEdge(1,1,x,y,45,lowerThreshold);
//                            break;
//                        case 90:
//                            findEdge(1,0,x,y,90,lowerThreshold);
//                            break;
//                        case 135:
//                            findEdge(-1,-1,x,y,135,lowerThreshold);
//                            break;
//                        default:
//                            
//                            
//                        
//                    }
//                    
//                }
//                
//                
//                
//            }
//         }
//        
//        
//        
//        
//        
//        
//        
//        
//        
//        
//        return temp;
//    }
    
    public static BufferedImage canny(BufferedImage img){
        CannyEdgeDetector detector = new CannyEdgeDetector();
   detector.setLowThreshold(0.5f);
  detector.setHighThreshold(1f);

   detector.setSourceImage(img);
   detector.process();
   BufferedImage edges = detector.getEdgesImage();
   return edges;
        
    }
    
    
    
    
    


public static class CannyEdgeDetector {


	
	private final static float GAUSSIAN_CUT_OFF = 0.005f;
	private final static float MAGNITUDE_SCALE = 100F;
	private final static float MAGNITUDE_LIMIT = 1000F;
	private final static int MAGNITUDE_MAX = (int) (MAGNITUDE_SCALE * MAGNITUDE_LIMIT);

	
	private int height;
	private int width;
	private int picsize;
	private int[] data;
	private int[] magnitude;
	private BufferedImage sourceImage;
	private BufferedImage edgesImage;
	
	private float gaussianKernelRadius;
	private float lowThreshold;
	private float highThreshold;
	private int gaussianKernelWidth;
	private boolean contrastNormalized;

	private float[] xConv;
	private float[] yConv;
	private float[] xGradient;
	private float[] yGradient;

	
	public CannyEdgeDetector() {
		lowThreshold = 2.5f;
		highThreshold = 7.5f;
		gaussianKernelRadius = 2f;
		gaussianKernelWidth = 16;
		contrastNormalized = false;
	}


	
	public BufferedImage getSourceImage() {
		return sourceImage;
	}

	
	public void setSourceImage(BufferedImage image) {
		sourceImage = image;
	}

	public BufferedImage getEdgesImage() {
		return edgesImage;
	}
 

	
	public void setEdgesImage(BufferedImage edgesImage) {
		this.edgesImage = edgesImage;
	}


	
	public float getLowThreshold() {
		return lowThreshold;
	}
	

	
	public void setLowThreshold(float threshold) {
		if (threshold < 0) throw new IllegalArgumentException();
		lowThreshold = threshold;
	}
 

	
	public float getHighThreshold() {
		return highThreshold;
	}
	

	
	public void setHighThreshold(float threshold) {
		if (threshold < 0) throw new IllegalArgumentException();
		highThreshold = threshold;
	}

	
	public int getGaussianKernelWidth() {
		return gaussianKernelWidth;
	}

	public void setGaussianKernelWidth(int gaussianKernelWidth) {
		if (gaussianKernelWidth < 2) throw new IllegalArgumentException();
		this.gaussianKernelWidth = gaussianKernelWidth;
	}


	public float getGaussianKernelRadius() {
		return gaussianKernelRadius;
	}
	

	public void setGaussianKernelRadius(float gaussianKernelRadius) {
		if (gaussianKernelRadius < 0.1f) throw new IllegalArgumentException();
		this.gaussianKernelRadius = gaussianKernelRadius;
	}
	

	
	public boolean isContrastNormalized() {
		return contrastNormalized;
	}

	public void setContrastNormalized(boolean contrastNormalized) {
		this.contrastNormalized = contrastNormalized;
	}
	
	// methods
	
	public void process() {
		width = sourceImage.getWidth();
		height = sourceImage.getHeight();
		picsize = width * height;
		initArrays();
		readLuminance();
		if (contrastNormalized) normalizeContrast();
		computeGradients(gaussianKernelRadius, gaussianKernelWidth);
		int low = Math.round(lowThreshold * MAGNITUDE_SCALE);
		int high = Math.round( highThreshold * MAGNITUDE_SCALE);
		performHysteresis(low, high);
		thresholdEdges();
		writeEdges(data);
	}
 
	// private utility methods
	
	private void initArrays() {
		if (data == null || picsize != data.length) {
			data = new int[picsize];
			magnitude = new int[picsize];

			xConv = new float[picsize];
			yConv = new float[picsize];
			xGradient = new float[picsize];
			yGradient = new float[picsize];
		}
	}

	
	private void computeGradients(float kernelRadius, int kernelWidth) {
		
		//generate the gaussian convolution masks
		float kernel[] = new float[kernelWidth];
		float diffKernel[] = new float[kernelWidth];
		int kwidth;
		for (kwidth = 0; kwidth < kernelWidth; kwidth++) {
			float g1 = gaussian(kwidth, kernelRadius);
			if (g1 <= GAUSSIAN_CUT_OFF && kwidth >= 2) break;
			float g2 = gaussian(kwidth - 0.5f, kernelRadius);
			float g3 = gaussian(kwidth + 0.5f, kernelRadius);
			kernel[kwidth] = (g1 + g2 + g3) / 3f / (2f * (float) Math.PI * kernelRadius * kernelRadius);
			diffKernel[kwidth] = g3 - g2;
		}

		int initX = kwidth - 1;
		int maxX = width - (kwidth - 1);
		int initY = width * (kwidth - 1);
		int maxY = width * (height - (kwidth - 1));
		
		//perform convolution in x and y directions
		for (int x = initX; x < maxX; x++) {
			for (int y = initY; y < maxY; y += width) {
				int index = x + y;
				float sumX = data[index] * kernel[0];
				float sumY = sumX;
				int xOffset = 1;
				int yOffset = width;
				for(; xOffset < kwidth ;) {
					sumY += kernel[xOffset] * (data[index - yOffset] + data[index + yOffset]);
					sumX += kernel[xOffset] * (data[index - xOffset] + data[index + xOffset]);
					yOffset += width;
					xOffset++;
				}
				
				yConv[index] = sumY;
				xConv[index] = sumX;
			}
 
		}
 
		for (int x = initX; x < maxX; x++) {
			for (int y = initY; y < maxY; y += width) {
				float sum = 0f;
				int index = x + y;
				for (int i = 1; i < kwidth; i++)
					sum += diffKernel[i] * (yConv[index - i] - yConv[index + i]);
 
				xGradient[index] = sum;
			}
 
		}

		for (int x = kwidth; x < width - kwidth; x++) {
			for (int y = initY; y < maxY; y += width) {
				float sum = 0.0f;
				int index = x + y;
				int yOffset = width;
				for (int i = 1; i < kwidth; i++) {
					sum += diffKernel[i] * (xConv[index - yOffset] - xConv[index + yOffset]);
					yOffset += width;
				}
 
				yGradient[index] = sum;
			}
 
		}
 
		initX = kwidth;
		maxX = width - kwidth;
		initY = width * kwidth;
		maxY = width * (height - kwidth);
		for (int x = initX; x < maxX; x++) {
			for (int y = initY; y < maxY; y += width) {
				int index = x + y;
				int indexN = index - width;
				int indexS = index + width;
				int indexW = index - 1;
				int indexE = index + 1;
				int indexNW = indexN - 1;
				int indexNE = indexN + 1;
				int indexSW = indexS - 1;
				int indexSE = indexS + 1;
				
				float xGrad = xGradient[index];
				float yGrad = yGradient[index];
				float gradMag = hypot(xGrad, yGrad);

				//perform non-maximal supression
				float nMag = hypot(xGradient[indexN], yGradient[indexN]);
				float sMag = hypot(xGradient[indexS], yGradient[indexS]);
				float wMag = hypot(xGradient[indexW], yGradient[indexW]);
				float eMag = hypot(xGradient[indexE], yGradient[indexE]);
				float neMag = hypot(xGradient[indexNE], yGradient[indexNE]);
				float seMag = hypot(xGradient[indexSE], yGradient[indexSE]);
				float swMag = hypot(xGradient[indexSW], yGradient[indexSW]);
				float nwMag = hypot(xGradient[indexNW], yGradient[indexNW]);
				float tmp;

				if (xGrad * yGrad <= (float) 0 /*(1)*/
					? Math.abs(xGrad) >= Math.abs(yGrad) /*(2)*/
						? (tmp = Math.abs(xGrad * gradMag)) >= Math.abs(yGrad * neMag - (xGrad + yGrad) * eMag) /*(3)*/
							&& tmp > Math.abs(yGrad * swMag - (xGrad + yGrad) * wMag) /*(4)*/
						: (tmp = Math.abs(yGrad * gradMag)) >= Math.abs(xGrad * neMag - (yGrad + xGrad) * nMag) /*(3)*/
							&& tmp > Math.abs(xGrad * swMag - (yGrad + xGrad) * sMag) /*(4)*/
					: Math.abs(xGrad) >= Math.abs(yGrad) /*(2)*/
						? (tmp = Math.abs(xGrad * gradMag)) >= Math.abs(yGrad * seMag + (xGrad - yGrad) * eMag) /*(3)*/
							&& tmp > Math.abs(yGrad * nwMag + (xGrad - yGrad) * wMag) /*(4)*/
						: (tmp = Math.abs(yGrad * gradMag)) >= Math.abs(xGrad * seMag + (yGrad - xGrad) * sMag) /*(3)*/
							&& tmp > Math.abs(xGrad * nwMag + (yGrad - xGrad) * nMag) /*(4)*/
					) {
					magnitude[index] = gradMag >= MAGNITUDE_LIMIT ? MAGNITUDE_MAX : (int) (MAGNITUDE_SCALE * gradMag);
					//NOTE: The orientation of the edge is not employed by this
					//implementation. It is a simple matter to compute it at
					//this point as: Math.atan2(yGrad, xGrad);
				} else {
					magnitude[index] = 0;
				}
			}
		}
	}
 
	//NOTE: It is quite feasible to replace the implementation of this method
	//with one which only loosely approximates the hypot function. I've tested
	//simple approximations such as Math.abs(x) + Math.abs(y) and they work fine.
	private float hypot(float x, float y) {
		return (float) Math.hypot(x, y);
	}
 
	private float gaussian(float x, float sigma) {
		return (float) Math.exp(-(x * x) / (2f * sigma * sigma));
	}
 
	private void performHysteresis(int low, int high) {
		//NOTE: this implementation reuses the data array to store both
		//luminance data from the image, and edge intensity from the processing.
		//This is done for memory efficiency, other implementations may wish
		//to separate these functions.
		Arrays.fill(data, 0);
 
		int offset = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (data[offset] == 0 && magnitude[offset] >= high) {
					follow(x, y, offset, low);
				}
				offset++;
			}
		}
 	}
 
	private void follow(int x1, int y1, int i1, int threshold) {
		int x0 = x1 == 0 ? x1 : x1 - 1;
		int x2 = x1 == width - 1 ? x1 : x1 + 1;
		int y0 = y1 == 0 ? y1 : y1 - 1;
		int y2 = y1 == height -1 ? y1 : y1 + 1;
		
		data[i1] = magnitude[i1];
		for (int x = x0; x <= x2; x++) {
			for (int y = y0; y <= y2; y++) {
				int i2 = x + y * width;
				if ((y != y1 || x != x1)
					&& data[i2] == 0 
					&& magnitude[i2] >= threshold) {
					follow(x, y, i2, threshold);
					return;
				}
			}
		}
	}

	private void thresholdEdges() {
		for (int i = 0; i < picsize; i++) {
			data[i] = data[i] > 0 ? -1 : 0xff000000;
		}
	}
	
	private int luminance(float r, float g, float b) {
		return Math.round(0.299f * r + 0.587f * g + 0.114f * b);
	}
	
	private void readLuminance() {
		int type = sourceImage.getType();
		if (type == BufferedImage.TYPE_INT_RGB || type == BufferedImage.TYPE_INT_ARGB) {
			int[] pixels = (int[]) sourceImage.getData().getDataElements(0, 0, width, height, null);
			for (int i = 0; i < picsize; i++) {
				int p = pixels[i];
				int r = (p & 0xff0000) >> 16;
				int g = (p & 0xff00) >> 8;
				int b = p & 0xff;
				data[i] = luminance(r, g, b);
			}
		} else if (type == BufferedImage.TYPE_BYTE_GRAY) {
			byte[] pixels = (byte[]) sourceImage.getData().getDataElements(0, 0, width, height, null);
			for (int i = 0; i < picsize; i++) {
				data[i] = (pixels[i] & 0xff);
			}
		} else if (type == BufferedImage.TYPE_USHORT_GRAY) {
			short[] pixels = (short[]) sourceImage.getData().getDataElements(0, 0, width, height, null);
			for (int i = 0; i < picsize; i++) {
				data[i] = (pixels[i] & 0xffff) / 256;
			}
		} else if (type == BufferedImage.TYPE_3BYTE_BGR) {
            byte[] pixels = (byte[]) sourceImage.getData().getDataElements(0, 0, width, height, null);
            int offset = 0;
            for (int i = 0; i < picsize; i++) {
                int b = pixels[offset++] & 0xff;
                int g = pixels[offset++] & 0xff;
                int r = pixels[offset++] & 0xff;
                data[i] = luminance(r, g, b);
            }
        } else {
			throw new IllegalArgumentException("Unsupported image type: " + type);
		}
	}
 
	private void normalizeContrast() {
		int[] histogram = new int[256];
		for (int i = 0; i < data.length; i++) {
			histogram[data[i]]++;
		}
		int[] remap = new int[256];
		int sum = 0;
		int j = 0;
		for (int i = 0; i < histogram.length; i++) {
			sum += histogram[i];
			int target = sum*255/picsize;
			for (int k = j+1; k <=target; k++) {
				remap[k] = i;
			}
			j = target;
		}
		
		for (int i = 0; i < data.length; i++) {
			data[i] = remap[data[i]];
		}
	}
	
	private void writeEdges(int pixels[]) {
		//NOTE: There is currently no mechanism for obtaining the edge data
		//in any other format other than an INT_ARGB type BufferedImage.
		//This may be easily remedied by providing alternative accessors.
		if (edgesImage == null) {
			edgesImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		}
		edgesImage.getWritableTile(0, 0).setDataElements(0, 0, width, height, pixels);
	}
 
}
    
}
