package com.springboot.panecillos.app.servicios;

import akka.actor.*;
import java.util.ArrayList;

import com.springboot.panecillos.app.models.domain.TarjetaCredito;

public class VerificarTarjeta  extends UntypedAbstractActor  {

	 private static final long TIEMPO_VALIDAR_TARJETA = 2000;
	    private ArrayList<ActorRef> tarjetas;
	    
	    public enum Mensaje {
	        ATENDER_TARJETA,
	        SOLICITAR_DATOS_TARJETA,
	        TARJETA_VALIDA
	    }
	    
	    @Override
	    public void preStart() {
	    	tarjetas = new ArrayList<>();
	    }

	    @Override
	    public void onReceive(Object o) throws Exception {
	        System.out.println("Se ha recibido el mensaje: "+ o);
	 
	        if (o == Mensaje.ATENDER_TARJETA) {
	        	tarjetas.add(getSender());
	        } else if (o == Mensaje.SOLICITAR_DATOS_TARJETA) {            
	            System.out.println("La tarjeta esta lista");
	            System.out.println("Se ha recibido  la tarjeta");            
	            if (!tarjetas.isEmpty()) {
	            	tarjetas.get(0).tell(Mensaje.SOLICITAR_DATOS_TARJETA, getSelf());
	            	tarjetas.remove(0);
	            }
	        } else {
	            unhandled(o);
	        }
	    }
	    
	     private void pedirPreparacion(TarjetaCredito tarjetaCredito) throws InterruptedException {
	        Thread.sleep(TIEMPO_VALIDAR_TARJETA);
	        if(tarjetaCredito.getNumeroTarjeta().length()!=16
					|| tarjetaCredito.getCvc().length()!=3 
					|| tarjetaCredito.getNumeroTarjeta().isEmpty()
					|| tarjetaCredito.getCodigoPostal().isEmpty()) {
	        	System.out.println("Tarjeta invalidad");
	        	tarjetas.get(0).tell(Mensaje.SOLICITAR_DATOS_TARJETA, getSelf());
	        }else {
	        	
	        	tarjetas.get(0).tell(Mensaje.TARJETA_VALIDA, getSelf());
	        }
	    }

}
