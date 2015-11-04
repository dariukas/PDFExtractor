import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.exceptions.CryptographyException;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.TextPosition;


public class PDFReading extends PDFTextStripper {

	 public PDFReading() throws IOException {
         super.setSortByPosition(true);
     }
	 
	
	
	public static void pdfreader(String path) throws CryptographyException {
	    {PDFTextStripper pdfStripper = null;
	    PDDocument pdDoc = null;
	    COSDocument cosDoc = null;
	    File file = new File(path);
	    try {
	        PDFParser parser = new PDFParser(new FileInputStream(file));
	         
	        parser.parse();
	        cosDoc = parser.getDocument();
	        pdfStripper = new PDFTextStripper() 
	        {   String prevBaseFont = "";
	        String prevFont = "";

	        protected void writeString(String text, List<TextPosition> textPositions) throws IOException
	        {
	            StringBuilder builder = new StringBuilder();

	            for (TextPosition position : textPositions)
	            {
	                String baseFont = position.getFont().getBaseFont();
	                String font = position.getFont().getFontDescriptor().getFontName();
	                
	                //if (position.getFont().getFontDescriptor().isForceBold()){ System.out.println("Bold!");}
	
	                if (font != null && !font.equals(prevFont))
	                {
	                    builder.append('[').append(font).append(']');
	                    prevFont = font;
	                }
	                builder.append(position.getCharacter());
	            }

	            writeString(builder.toString());
	        }
	    };
	        pdDoc = new PDDocument(cosDoc);
	        if (pdDoc.isEncrypted()){//To check for Password protected PDF.
	        	pdDoc.decrypt("");//Trying to decrypt with a empty string.
	        	pdDoc.setAllSecurityToBeRemoved(true);
	        }
	        pdfStripper.setStartPage(22);
	        pdfStripper.setEndPage(22);
	        String parsedText = pdfStripper.getText(pdDoc);
	        System.out.println(parsedText);
	        System.out.println("Done!");
	        
	        
	        /*List allPages = pdDoc.getDocumentCatalog().getAllPages();
	        for (int i = 22; i < 23; i++) {
                PDPage page = (PDPage) allPages.get(i);
                System.out.println("Processing page: " + i);
                PDStream contents = page.getContents();
                if (contents != null) {
                	Map<String,PDFont> map = page.findResources().getFonts();
                	for ( Map.Entry<String,PDFont> entry : map.entrySet()) {
                	    String key = entry.getKey();
                	    PDFont font = entry.getValue();
                	    String n = font.getFontDescriptor().getFontName();
                	    System.out.println(n);
                	}      	
                }
	        }
	        */
	        
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	        } 
	    }
	}

}
