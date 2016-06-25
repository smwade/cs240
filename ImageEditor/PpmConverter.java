import java.io.*;
import java.util.*;

public class PpmConverter {
	
	public static Image convertToImage(File srcFile) throws FileNotFoundException {
		int height = 0;
		int width = 0;
		int max_val = 0;
		
		Scanner scan = new Scanner(srcFile);
		scan.useDelimiter("(#[^\\n]+\\n|\\s+)+");
		scan.next();
		width = scan.nextInt();
		height = scan.nextInt();
		max_val = scan.nextInt();
		Image originalImg = new Image(height, width);
		
		for(int row = 0; row < height; row++){
			for(int col = 0; col < width; col++){
				int red = scan.nextInt();
				int green = scan.nextInt();
				int blue = scan.nextInt();
				originalImg.ImageArray[row][col].changePixel(red, green, blue);
			}
		}
		scan.close();
		originalImg.max_val = max_val;
		return originalImg;
	}
	
	public static void convertToPpm(Image altImage, File outputFile) throws IOException{
		StringBuilder outString = new StringBuilder();
		outString.append("P3\n");
		outString.append(String.valueOf(altImage.width) + " " + String.valueOf(altImage.height) + "\n");
		outString.append("255\n");
		for (int row = 0; row < altImage.height; row++) {
			for (int col = 0; col < altImage.width; col++) {
				outString.append(String.valueOf(altImage.ImageArray[row][col].red) + " " + String.valueOf(altImage.ImageArray[row][col].green + " " + String.valueOf(altImage.ImageArray[row][col].blue)) + "\n");
			}
		}
		FileWriter writer = new FileWriter(outputFile);
		writer.write(outString.toString());
		writer.close();
		
	}
}
