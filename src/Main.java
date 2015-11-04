import org.apache.pdfbox.exceptions.CryptographyException;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//System.nanoTime();
		//System.currentTimeMillis() is only accurate above 15ms. 
		final long startTime = System.currentTimeMillis();;
		run();
		final long duration = System.currentTimeMillis() - startTime;
		System.out.println(duration);
	}

	private static void run() {
		String path1 = "/Users/kristinaslekyte/Desktop/Measuring Browser Performance.pdf";
        String path = "/Users/kristinaslekyte/Downloads/Kamus Ekonomi.pdf";
		try {
			PDFReading.pdfreader(path);
		} catch (CryptographyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
