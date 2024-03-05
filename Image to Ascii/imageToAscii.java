import java.io.*;
import java.util.Scanner;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.math.*;

public class imageToAscii {
    public static void main(String[] args) throws IOException{
        Scanner in = new Scanner(System.in); // scanner object to read input
        
        System.out.print("Enter the name of the image file(ie: vangough.jpg): "); // prompt user for image file
        String imageName = in.nextLine(); // read the image file name
        
        File  file = new File(imageName); // create a file object with the image file name
        BufferedImage image = ImageIO.read(file); // read the image file
        
        boolean isImage = false; // initialize the boolean variable to check if the file is an image
        while (!isImage) {  
            try {
                image = ImageIO.read(file);
                if (image == null) {
                    System.out.print("The file is not an image. Enter the name of the image file(ie: vangough.jpg): ");
                    // Handle the case where the file is not an image.
                    imageName = in.nextLine(); // read the image file name
                    file = new File(imageName); // create a file object with the valid image file name
                    while(!file.exists()) { // check if the file exists
                        System.out.println("File not found. Please enter a valid file name."); // prompt user that file is not valid and to input a valid file name
                        imageName = in.nextLine(); // read the image file name
                        file = new File(imageName); // create a file object with the valid image file name
                    }
                } else {
                    // Your existing code that handles the image.
                    isImage = true; // set the boolean variable to true
                }
            } catch (IOException e) {
                // Handle the case where the file could not be read.
                System.out.println("File not found. Please enter a valid file name."); // prompt user that file is not valid and to input a valid file name
                imageName = in.nextLine(); // read the image file name
                file = new File(imageName); // create a file object with the valid image file name
            }
        }
        
        System.out.print("Enter the name of the txt file: "); // prompt user for txt file
        String fileName = in.nextLine(); // read the txt file name
        FileOutputStream out = new FileOutputStream(fileName); // create a file output stream object with the txt file name
		PrintWriter newFile = new PrintWriter(out); // create a print writer object with the file output stream object
        

        try{ // try block to catch any exceptions
            
            double width = image.getWidth(); // get the width of the image
            double height = image.getHeight();  // get the height of the image 
            System.out.println("Width: " + width + " Height: " + height); // print the width and height of the image
            
            int scaledWidth; 
            int scaledHeight;
            
            // scaled down image
            while (width > 500) { 
                width = width / 1.2;
            }
            while (height > 500) {
                height = height / 1.2;
            }
            scaledWidth = (int)Math.round(width); 
            scaledHeight = (int)Math.round(height);
        
            System.out.println("Width: " + scaledWidth + " Height: " + scaledHeight); // print the scaled width and height of the image

            Image scaledImage = image.getScaledInstance(scaledWidth, scaledHeight, image.SCALE_SMOOTH); // scale the image
            BufferedImage newImage = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB); // create a new buffered image with the scaled width and height
            newImage.createGraphics().drawImage(scaledImage, 0, 0, null); // draw the scaled image
            //save the new image
            File output = new File("scaled_"+ imageName); // create a new file object with the scaled image name

            ImageIO.write(newImage, "jpg", output); // write the new image to the file
            
            for (int y = 0; y < scaledHeight; y++){
                for(int x = 0;x < scaledWidth; x++){
                    int pixel = newImage.getRGB(x, y); // get the pixel value 
                    int red = (pixel >> 16) & 0xff; // get the red value
                    int green = (pixel >> 8) & 0xff; // get the green value 
                    int blue = (pixel) & 0xff; // get the blue value
                    int gray = (int) (0.299 * red + 0.587 * green + 0.114 * blue); // calculate the gray value
                    char ascii = ' '; // initialize the ascii character
                    
                    if(gray < 25) { // check the gray value and assign the ascii character
                        ascii = '#'; 
                    }else if(gray < 50) {
                        ascii = 'X';
                    } else if(gray < 75) {
                        ascii = '%';
                    } else if(gray < 100) {
                        ascii = '&';
                    } else if(gray < 125) {
                        ascii = '*';
                    } else if(gray < 150) {
                        ascii = '+';
                    } else if(gray < 175) {
                        ascii = '!';
                    } else if(gray < 200) {
                        ascii = '/';
                    } else if(gray < 225) {
                        ascii = '(';
                    } else if(gray < 250) {
                        ascii = ')';
                    } else {
                        ascii = ' ';
                    }
                    System.out.print(ascii); // print the ascii character
                    newFile.print(ascii); // write the ascii character to the file
                }
                System.out.println(); // print a new line
                newFile.println(); // write a new line to the file
            }
        } catch(IOException e) {
            System.out.println("Error: "); // print error message
        }
        in.close(); // close the scanner object
        newFile.close(); // close the print writer object
    }   
}
