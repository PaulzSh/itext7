import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;

import java.io.File;
import java.io.IOException;

public class PdfWithHeaderAndLogo {
    public static void main(String[] args) throws IOException {
        String dest = "output.pdf";
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        
        // 加载logo图片
        Image logo = new Image(ImageDataFactory.create("path/to/your/logo.png"));
        
        // 设置页眉和logo处理器
        HeaderAndLogoHandler handler = new HeaderAndLogoHandler(logo, "公司名称/文档标题");
        pdf.addEventHandler(PdfDocumentEvent.START_PAGE, handler);
        
        // 添加文档内容
        for (int i = 0; i < 10; i++) {
            document.add(new Paragraph("这是文档内容 " + i));
            document.add(new AreaBreak()); // 创建新页面
        }
        
        document.close();
    }
}