import java.io.*;

public class ImageEditor {
	
	public static void usageMesssage(){
		System.out.println("USAGE: java ImageEditor in-file out-file (grayscale|invert|emboss|motionblur motion-blur-length)");
	}

	public static void main(String[] args) throws FileNotFoundException, IOException{
		new ImageEditor().run(args);
	}
	
	public void run(String[] args) throws IOException{
		if (args.length == 3) {
			if (args[2].equals("motionblur")) {
				ImageEditor.usageMesssage();
			}
			if (args[2].equals("grayscale")) {
				File inputFile = new File(args[0]);
				Image originalImg = PpmConverter.convertToImage(inputFile);
				Pixel[][] imageArray = originalImg.ImageArray;
				for (int j = 0; j< imageArray[0].length; j++){
				     for (int i = 0; i< imageArray.length; i++){
				    	 int[] colors = imageArray[i][j].getPixel();
				    	 int gray_color = (colors[0] + colors[1] + colors[2]) / 3;
				    	 imageArray[i][j].changePixel(gray_color, gray_color, gray_color);
				     }
				}
				// Write to output
				File outputFile = new File(args[1]);
				PpmConverter.convertToPpm(originalImg, outputFile);
				
			}
			else if (args[2].equals("invert")) {
				File inputFile = new File(args[0]);
				Image originalImg = PpmConverter.convertToImage(inputFile);
				Pixel[][] imageArray = originalImg.ImageArray;
				for (int j = 0; j< imageArray[0].length; j++){
				     for (int i = 0; i< imageArray.length; i++){
				    	 int[] colors = imageArray[i][j].getPixel();
				    	 imageArray[i][j].changePixel(255 - colors[0], 255 - colors[1], 255 - colors[2]);
				     }
				}
				// Write to output
				File outFile = new File(args[1]);
				PpmConverter.convertToPpm(originalImg, outFile);
				
			}
			
			// HOW TO COPY ARAY OF PIXELS??
			else if (args[2].equals("emboss")) {
				File inputFile = new File(args[0]);
				Image originalImg = PpmConverter.convertToImage(inputFile);
				Pixel[][] imageArray = originalImg.ImageArray;
				//Image changedImg = new Image(originalImg.height, originalImg.width);
				for (int row = originalImg.height - 1; row >= 0; row--){
				     for (int col = originalImg.width - 1; col >= 0; col--){
				    	 if(row == 0 || col == 0){
				    		 int diff = 128;
				    		 imageArray[row][col].changePixel(diff, diff, diff);
				    	 }
				    	 else {
				    		 int[] currentPixel = imageArray[row][col].getPixel();
				    		 int[] leftPixel = imageArray[row-1][col - 1].getPixel();
				    		 int redDiff = currentPixel[0] - leftPixel[0];
				    		 int greenDiff = currentPixel[1] - leftPixel[1];
				    		 int blueDiff = currentPixel[2] - leftPixel[2];
				    		 int colorMax = Math.max(Math.abs(blueDiff), Math.max(Math.abs(greenDiff), Math.abs(redDiff))); //NICE WAY TO DO THIS??
				    		 int colorDiff = 0;
				    		 if (Math.abs(redDiff) == colorMax){
				    			 colorDiff = redDiff;
				    		 }
				    		 else if (Math.abs(greenDiff) == colorMax){
				    			 colorDiff = greenDiff;
				    		 }
				    		 else if (Math.abs(blueDiff) == colorMax){
				    			 colorDiff = blueDiff;
				    		 }
				    		 colorDiff += 128;
				    		 if (colorDiff < 0){
				    			 colorDiff = 0;
				    		 }
				    		 if (colorDiff > 255){
				    			 colorDiff = 255;
				    		 }
				    		 imageArray[row][col].changePixel(colorDiff, colorDiff, colorDiff);
				    	 }
				     }
				}
				// Write to output
				File outFile = new File(args[1]);
				PpmConverter.convertToPpm(originalImg, outFile);
			}
			else {
				ImageEditor.usageMesssage();
			}
			
				
		}
		else if (args.length == 4) {
			//:TODO add check for int
			int blurNumber = Integer.parseInt(args[3]);
			
			if (!args[2].equals("motionblur")) {
				ImageEditor.usageMesssage();
			}
			if (blurNumber < 0){
				ImageEditor.usageMesssage();
			}
			
			File inputFile = new File(args[0]);
			Image originalImg = PpmConverter.convertToImage(inputFile);
			Pixel[][] imageArray = originalImg.ImageArray;
			
			for (int i = 0; i < originalImg.height; i++){
			     for (int j = 0; j< originalImg.width; j++){
			    	 int n = 0;
			    	 if (j + blurNumber >= originalImg.width){
			    		 n = originalImg.width - j;
			    	 } else {
			    		 n = blurNumber;
			    	 }
			    	 int totalRed = 0;
			    	 int totalGreen = 0;
			    	 int totalBLue = 0;
			    	 for (int a = 0; a < n; a++){
			    		 totalRed += imageArray[i][j+a].red;
			    		 totalGreen += imageArray[i][j+a].green;
			    		 totalBLue += imageArray[i][j+a].blue;
			    	 }
			    	 if (n == 0){
			    		 n = 1;
			    	 }
			    	 totalRed = totalRed / n;
			    	 totalGreen = totalGreen / n;
			    	 totalBLue = totalBLue / n;
			    	 if (totalRed > 255) {
			    		 totalRed = 255;
			    	 }
			    	 if (totalGreen > 255) {
			    		 totalGreen = 255;
			    	 }
			    	 if (totalBLue > 255) {
			    		 totalBLue = 255;
			    	 }
			    	 
			    	 imageArray[i][j].changePixel(totalRed, totalGreen, totalBLue); 
			     }
			}
			
			// Write to output
			File outFile = new File(args[1]);
			PpmConverter.convertToPpm(originalImg, outFile);
		}
		else {
			ImageEditor.usageMesssage();
		}	
	}
}
