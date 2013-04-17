import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

//Helper class to downsize images for different resolutions/screen sizes for
//Android aps. Currently can only reduce size; if I need to increase sizes at
//some point, I'll extend this class then.
public class AutoResizer {
	private static final boolean DEBUG = true;
	//Alter this variable to alter how much size is changed. For example, if
	//DENOMINATOR is 2, the images produced will be half size. If it's 4, they
	//will be quarter size.
	private static final int DENOMINATOR = 4;
	
	private static void debug(String strIn){
		if(DEBUG){
			System.out.println(strIn);
		}
	}
	
	//Source: http://www.javalobby.org/articles/ultimate-image/#11
	public static BufferedImage resize(BufferedImage imgIn, int newW, int newH) {  
        int w = imgIn.getWidth();  
        int h = imgIn.getHeight();  
        BufferedImage imgOut = new BufferedImage(newW, newH, imgIn.getType());  
        Graphics2D g = imgOut.createGraphics();  
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);  
        g.drawImage(imgIn, 0, 0, newW, newH, 0, 0, w, h, null);  
        g.dispose();  
        return imgOut;  
    }  

	public static void main(String[] args) {
		String imageSourceDir = 
				"C:\\Users\\Proven Paradox\\Dropbox\\Java Workspace\\PuzzleStrikeRandomizer\\Images"; 
		//String androidResDir = 
		//		"C:\\Users\\Proven Paradox\\Dropbox\\Java Workspace\\PuzzleStrikeRandomizerForAndroid\\res";
		String outputDir =
				"C:\\Users\\Proven Paradox\\Dropbox\\Java Workspace\\temp\\";
		File folder = new File(imageSourceDir);
		File[] fileList = folder.listFiles();
		for (File curFile: fileList){
			String curName = curFile.getName().toLowerCase();
			//This is hack-ish maybe, but it should work for my purposes
			String imageType = curName.substring(curName.length()-3);
			//String curPath = curFile.getPath();
			BufferedImage curImage = null;
			try {
				curImage = ImageIO.read(curFile);
				
				int curHeight = curImage.getHeight();
				int curWidth = curImage.getWidth();
				BufferedImage newImage = resize(curImage, curHeight/DENOMINATOR, curWidth/DENOMINATOR);
				File outputFile = new File(outputDir+""+curName);
				ImageIO.write(newImage, imageType, outputFile);
			} catch (IOException e) {
				debug("IOException catch");
			}
		}
	}
}