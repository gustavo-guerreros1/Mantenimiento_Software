package com.springboot.panecillos.app.pdf;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

@Component("miscompras")
public class ComprasViewPdf extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		document.setMargins(-10, -10, 30, 10);
		
		document.open();
		List<Compras> compras=(List<Compras>) model.get("compras");
		Usuario usuario= (Usuario) model.get("usuarioPrincipal");
		
		
		PdfPTable header=new PdfPTable(4);
		
		PdfPCell celdaHeader=null; 
		Font fuenteHeader=FontFactory.getFont("Helvetica", 17, Color.BLACK);
		celdaHeader=new PdfPCell(new Phrase("CELULARES MALMO", fuenteHeader));
		celdaHeader.setPadding(10);
		celdaHeader.setBorderWidthLeft(0);
		celdaHeader.setBorderWidthBottom(0);
		celdaHeader.setBorderWidthTop(0);
		celdaHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
		celdaHeader.setVerticalAlignment(Element.ALIGN_CENTER);
		celdaHeader.setColspan(2);
		
		
		PdfPCell celdaHeader1=null; 
		Font fuenteHeader2=FontFactory.getFont("Helvetica", 10, Color.BLACK);
		celdaHeader1=new PdfPCell(new Phrase("FACTURA ELECTRONICA", fuenteHeader2));
		celdaHeader1.setBorderColor(new Color(217,214,217));
		celdaHeader1.setBorderWidthBottom(0);
		celdaHeader1.setPadding(5);
;
		celdaHeader1.setHorizontalAlignment(Element.ALIGN_CENTER);
		celdaHeader1.setVerticalAlignment(Element.ALIGN_CENTER);
		celdaHeader1.setColspan(2);
		
		
		PdfPTable header2=new PdfPTable(4);
		
		PdfPCell celdaHeader2=null; 
		Font fuenteHeader3=FontFactory.getFont("Helvetica", 10, Color.BLACK);
		celdaHeader2=new PdfPCell(new Phrase("Avenida Las Palmeras 1554, Los Olivos"
				+ "\nTelefono: +51 975 602 085", fuenteHeader3));
		celdaHeader2.setPadding(5);
		celdaHeader2.setBorderWidthLeft(0);
		celdaHeader2.setBorderWidthBottom(0);
		celdaHeader2.setBorderWidthTop(0);
		celdaHeader2.setHorizontalAlignment(Element.ALIGN_LEFT);
		celdaHeader2.setVerticalAlignment(Element.ALIGN_CENTER);
		celdaHeader2.setColspan(2);
		
		
		PdfPCell celdaHeader3=null; 
		Font fuenteHeader4=FontFactory.getFont("Helvetica", 10, Color.BLACK);
		celdaHeader3=new PdfPCell(new Phrase("RUC: 20148092282", fuenteHeader4));
		celdaHeader3.setBorderColor(new Color(217,214,217));
		celdaHeader3.setBorderWidthBottom(5);
		celdaHeader3.setBorderWidthTop(0);
		celdaHeader3.setPadding(0);
;
		celdaHeader3.setHorizontalAlignment(Element.ALIGN_CENTER);
		celdaHeader3.setVerticalAlignment(Element.ALIGN_CENTER);
		celdaHeader3.setColspan(2);
		
	
		header.addCell(celdaHeader);
		header.addCell(celdaHeader1);
		
		header2.addCell(celdaHeader2);
		header2.addCell(celdaHeader3);
		
		header2.setSpacingAfter(15);
		
		//=================================================================================
		
		Font fuenteTitulo=FontFactory.getFont("Helvetica", 11, Color.BLACK);
		PdfPTable tabla=new PdfPTable(1);
		PdfPCell celdaDatos=new PdfPCell(new Phrase("Nombre:  "+usuario.getNombre().toUpperCase()+" "+usuario.getApellido().toUpperCase(), fuenteTitulo));
		celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
		celdaDatos.setVerticalAlignment(Element.ALIGN_CENTER);
		celdaDatos.setBorder(0);
		celdaDatos.setPadding(3);
		tabla.addCell(celdaDatos);
		
		tabla.setSpacingAfter(10);
		
		
		/*
		
		PdfPTable titulo=new PdfPTable(1);
		
		PdfPCell celda=null; 
		celda=new PdfPCell(new Phrase("MIS COMPRAS"));
		celda.setBorder(0);
		celda.setBackgroundColor(new Color(255,200,1));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setVerticalAlignment(Element.ALIGN_CENTER);
		celda.setPadding(10);
	
		titulo.addCell(celda);
		titulo.setSpacingAfter(10);
		*/
	
		document.add(header);
		document.add(header2);
		document.add(tabla);
		//document.add(titulo);
		
		Font fuente=FontFactory.getFont("Helvetica", 10, Color.WHITE);
		
		Color color=new Color(14,136,221);
		
		PdfPCell c1=null; 
		c1=new PdfPCell(new Phrase("CODIGO", fuente));
		c1.setBackgroundColor(color);
		c1.setPadding(5);
		
		PdfPCell c2=null; 
		c2=new PdfPCell(new Phrase("PRODUCTO", fuente));
		c2.setBackgroundColor(color);
		c2.setPadding(5);

		PdfPCell c3=null; 
		c3=new PdfPCell(new Phrase("PRECIO UNIT.", fuente));
		c3.setBackgroundColor(color);
		c3.setPadding(5);
		
		PdfPCell c4=null; 
		c4=new PdfPCell(new Phrase("CANTIDAD", fuente));
		c4.setBackgroundColor(color);
		c4.setPadding(5);
		
		PdfPCell c5=null; 
		c5=new PdfPCell(new Phrase("SUBTOTAL", fuente));
		c5.setBackgroundColor(color);
		c5.setPadding(5);
		
		
		Font fuente2=FontFactory.getFont("Helvetica", 10, Color.WHITE);
		
		
		for(Compras compra: compras) {
			PdfPTable tabla3=new PdfPTable(1);
			
			PdfPCell c6=null; 
			c6=new PdfPCell(new Phrase("FECHA: "+compra.getFecha(), fuente2));
			c6.setBackgroundColor(new Color(9,114,187));
			c6.setPadding(5);
			
			tabla3.addCell(c6);
			tabla3.setSpacingAfter(5);
			
			
			PdfPTable tabla2=new PdfPTable(5);
			//tabla2.setSpacingBefore(2);
			tabla2.addCell(c1);
			tabla2.addCell(c2);
			tabla2.addCell(c3);
			tabla2.addCell(c4);
			tabla2.addCell(c5);
			Double total=0.0;
			
			for(DetalleCompras detalle: compra.getDetalles()) {
				tabla2.addCell("C000-"+detalle.getIdDetalle());
				tabla2.addCell(detalle.getProducto().getNombre());
				tabla2.addCell("S/ "+detalle.getProducto().getPrecio().toString());
				tabla2.addCell(String.valueOf(detalle.getCantidad()));
				tabla2.addCell("S/ "+String.valueOf(detalle.getCantidad()*detalle.getProducto().getPrecio()));
				total+=detalle.getProducto().getPrecio()*detalle.getCantidad();
			}
			PdfPCell celdaTotal=new PdfPCell(new Phrase("TOTAL", fuente));
			celdaTotal.setColspan(4);
			celdaTotal.setHorizontalAlignment(Element.ALIGN_CENTER);
			celdaTotal.setBackgroundColor(new Color(14,136,221));
		
			tabla2.addCell(celdaTotal);
			tabla2.addCell("S/ "+Math.round(total*100.0)/100.0);
			
			tabla2.setSpacingAfter(20);
			document.add(tabla3);
			document.add(tabla2);
			
		}
		
		
	}	 

}
