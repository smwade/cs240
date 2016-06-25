
public class Image {
	
	public Pixel[][] ImageArray;
	public int height;
	public int width;
	public int max_val = 0;
	
	public Image(int height, int width) {
		this.ImageArray = new Pixel[height][width];
		for(int row = 0; row < height; row++){
			for(int col = 0; col < width; col++){
				this.ImageArray[row][col] = new Pixel();
			}
		}
		this.height = height;
		this.width = width;
		
	}

}
