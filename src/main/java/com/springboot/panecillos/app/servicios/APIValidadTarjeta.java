package com.springboot.panecillos.app.servicios;

import akka.actor.*;

public class APIValidadTarjeta extends UntypedAbstractActor  {

	 private static final long TIEMPO_EN_VALIDAR = 4000;
	    
     public enum Mensaje {
        VALIDANDO_TARJETA
    }
    
    private void prepararComida() throws InterruptedException {
        Thread.sleep(TIEMPO_EN_VALIDAR);
    }
	
	@Override
	public void onReceive(Object message) throws Throwable {
		System.out.println(" ha recibido la petición de validar tarjeta: " + message);
        if (message == Mensaje.VALIDANDO_TARJETA) {            
            System.out.println("está validando la tarjeta");
            prepararComida();
            System.out.println("ha validado la tarjeta.");
            getSender().tell(Mensaje.VALIDANDO_TARJETA, getSelf());
        } else {
            unhandled(message);
        }
		
	}
	
}
