package org.uv.tcswpractica02;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCSWPractica02 {
     public static void main(String[] args) {
    ControllerMensaje controller=new ControllerMensaje();
    
    IMensaje msg=new SaludoI();
    controller.mostrar(msg);
    controller.mostrar(new SaludoI());
    controller.mostrar(new DespedidaI());
    
    controller.mostrar(new IMensaje() {
            @Override
            public void imprimir() {
                Logger.getLogger(IMensaje.class.getName())
                        .log(Level.INFO, "Otro...");
            }
        });
    
    PojoEmpleado emp = new PojoEmpleado();
        emp.setClave(1); // Ajusta la clave según tu tabla
        emp.setNombre("Marlene Juarez");
        emp.setDireccion("Calle Falsa 123");
        emp.setTelefono("1234567890");
        
        DAOEmpleado dao = new DAOEmpleado();
        
        
         try {
            boolean exito = dao.create(emp);
            if (exito) {
                Logger.getLogger(TCSWPractica02.class.getName()).log(Level.INFO, "Empleado insertado correctamente");

            } else {
                Logger.getLogger(TCSWPractica02.class.getName()).log(Level.INFO, "Error al insertar empleado");

            }
        } catch (Exception e) {
            Logger.getLogger(TCSWPractica02.class.getName()).log(Level.INFO, "Execption", e);

        }
    }
}
    

