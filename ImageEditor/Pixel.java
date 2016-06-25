
public class Pixel {
	
	public int red;
	public int green;
	public int blue;
	
	public Pixel(){
		this.red = 0;
		this.blue = 0;
		this.green = 0;
	}
	
	public int[] getPixel(){
		int[] pixelColors = {this.red, this.green, this.blue};
		return pixelColors;
	}
		
	public void changePixel(int new_red, int new_green, int new_blue) {
		this.red = new_red;
		this.green = new_green;
		this.blue = new_blue;
	}

}
