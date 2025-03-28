import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;

public class HeaderAndLogoHandler implements IEventHandler {
    private Image logo;
    private String headerText;
    
    public HeaderAndLogoHandler(Image logo, String headerText) {
        this.logo = logo;
        this.headerText = headerText;
    }
    
    @Override
    public void handleEvent(Event event) {
        PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
        PdfDocument pdfDoc = docEvent.getDocument();
        PdfPage page = docEvent.getPage();
        Rectangle pageSize = page.getPageSize();
        
        // 获取页面 Canvas 用于绘制
        PdfCanvas pdfCanvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);
        Canvas canvas = new Canvas(pdfCanvas, pdfDoc, pageSize);
        
        // 在左上角添加页眉
        canvas.showTextAligned(new Paragraph(headerText), 
                              pageSize.getLeft() + 30, 
                              pageSize.getTop() - 30, 
                              TextAlignment.LEFT);
        
        // 在右上角添加logo
        if (logo != null) {
            float logoWidth = 50; // 根据需要调整logo宽度
            float logoHeight = logoWidth * (logo.getImageHeight() / logo.getImageWidth());
            logo.scaleToFit(logoWidth, logoHeight);
            canvas.add(logo
                .setFixedPosition(pageSize.getRight() - 30 - logoWidth, 
                                pageSize.getTop() - 30 - logoHeight));
        }
        
        canvas.close();
    }
}