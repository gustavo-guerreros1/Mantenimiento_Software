package com.springboot.panecillos.app.pdf;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.time.LocalDate;

import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;
import com.springboot.panecillos.app.models.domain.Compras;
import com.springboot.panecillos.app.models.domain.DetalleCompras;
import com.springboot.panecillos.app.models.domain.Usuario;
import com.itextpdf.kernel.colors.*;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.canvas.draw.DottedLine;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Text;

@Component("miscompras")
public class ComprasViewPdf extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		document.setMargins(-10, -10, 30, 10);
		
		document.open();
		List<Compras> compras=(List<Compras>) model.get("compras");
		Usuario usuario= (Usuario) model.get("usuarioPrincipal");
		
		PdfPTable header = buildHeader();
		PdfPTable header2 = buildHeader2();
		PdfPTable tableCompras = buildTableCompras(compras);
		
		document.add(header);
		document.add(header2);
		document.add(tableCompras);
	}
	
	
	private PdfPTable buildHeader() {
		PdfPTable header=new PdfPTable(4);
		PdfPCell celdaHeader=null; 
		Font fuenteHeader=FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD);
		celdaHeader=new PdfPCell(new Phrase("Nombre Usuario: "+Usuario.getNombre(),fuenteHeader));
		celdaHeader.setColspan(2);
		celdaHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
		celdaHeader.setBackgroundColor(DeviceRgb.LIGHT_GRAY);
		celdaHeader.setPadding(5);
		header.addCell(celdaHeader);
		
		celdaHeader=new PdfPCell(new Phrase("Fecha: "+LocalDate.now(),fuenteHeader));
		celdaHeader.setColspan(2);
		celdaHeader.setHorizontalAlignment(Element.ALIGN_RIGHT);
		celdaHeader.setBackgroundColor(DeviceRgb.LIGHT_GRAY);
		celdaHeader.setPadding(5);
		header.addCell(celdaHeader);
		
		header.setWidthPercentage(100);
		return header;
	}
	
	private PdfPTable buildHeader2() {
		
		PdfPTable header2=new PdfPTable(4);
		PdfPCell celdaHeader2=null; 
		Font fuenteHeader2=FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD);
		
		celdaHeader2=new PdfPCell(new Phrase("ID Compra",fuenteHeader2));
		celdaHeader2.setHorizontalAlignment(Element.ALIGN_CENTER);
		celdaHeader2.setBackgroundColor(DeviceRgb.BLACK.LIGHT_GRAY);
		celdaHeader2.setPadding(5);
		header2.addCell(celdaHeader2);
		
		celdaHeader2=new PdfPCell(new Phrase("Producto",fuenteHeader2));
		celdaHeader2.setHorizontalAlignment(Element.ALIGN_CENTER);
		celdaHeader2.setBackgroundColor(DeviceRgb.LIGHT_GRAY);
		celdaHeader2.setPadding(5);
		header2.addCell(celdaHeader2);
		
		celdaHeader2=new PdfPCell(new Phrase("Fecha Compra",fuenteHeader2));
		celdaHeader2.setHorizontalAlignment(Element.ALIGN_CENTER);
		celdaHeader2.setBackgroundColor(DeviceRgb.LIGHT_GRAY);
		celdaHeader2.setPadding(5);
		header2.addCell(celdaHeader2);
		
		celdaHeader2=new PdfPCell(new Phrase("Precio",fuenteHeader2));
		celdaHeader2.setHorizontalAlignment(Element.ALIGN_CENTER);
		celdaHeader2.setBackgroundColor(DeviceRgb.LIGHT_GRAY);
		celdaHeader2.setPadding(5);
		header2.addCell(celdaHeader2);
		
		header2.setWidthPercentage(100);
		return header2 ;
	}

	/**
	 * @param compras
	 * @return
	 */
	private PdfPTable buildTableCompras(List<Compras> compras) {
		PdfPTable tableCompras=new PdfPTable(4);
		PdfPCell celdaCompras=null;
		Font fuenteCompras=FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL);
		for (Compras compra : compras) {
			celdaCompras=new PdfPCell(new Phrase(String.valueOf(compra.getIdCompra()),fuenteCompras));
			celdaCompras.setHorizontalAlignment(Element.ALIGN_CENTER);
			celdaCompras.setPadding(5);
			tableCompras.addCell(celdaCompras);
			
			celdaCompras=new PdfPCell(new Phrase(String.valueOf(compra.getProducto()),fuenteCompras));
			celdaCompras.setHorizontalAlignment(Element.ALIGN_CENTER);
			celdaCompras.setPadding(5);
			tableCompras.addCell(celdaCompras);
			
			celdaCompras=new PdfPCell(new Phrase(compra.getFecha().toString(),fuenteCompras));
			celdaCompras.setHorizontalAlignment(Element.ALIGN_CENTER);
			celdaCompras.setPadding(5);
			tableCompras.addCell(celdaCompras);
			
			celdaCompras=new PdfPCell(new Phrase(String.valueOf(compra.getPrecio()),fuenteCompras));
			celdaCompras.setHorizontalAlignment(Element.ALIGN_CENTER);
			celdaCompras.setPadding(5);
			tableCompras.addCell(celdaCompras);
		}
		
		return tableCompras;
	}

}
	
	

