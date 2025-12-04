package org.uv.tcswpractica02;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  Clase DAO (Data Access Object) para la entidad Empleado
 * 
 * Esta clase implementa todas las operaciones CRUD (Create, Read, Update, Delete)
 * para gestionar empleados en la base de datos PostgreSQL. Utiliza el patrón
 * Singleton para obtener la conexión a la base de datos.
 * 
 * Implementa la interfaz IDAOGeneral con tipos específicos:
 * - PojoEmpleado: La entidad que representa un empleado
 * - Long: El tipo de dato del ID/clave del empleado
 */
public class DAOEmpleado implements IDAOGeneral<PojoEmpleado, Long> {

    
    private static final Logger logger = Logger.getLogger(DAOEmpleado.class.getName());

    @Override
public boolean create(PojoEmpleado pojo) {
    ConexionDB con = ConexionDB.getInstance();

    TransacctionDB<PojoEmpleado, Long> t = new TransacctionDB<PojoEmpleado, Long>(pojo) {
        @Override
        public boolean execute(Connection con) {
            String sql = "INSERT INTO empleados (clave, nombre, direccion, telefono) VALUES (?, ?, ?, ?)";
            
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setLong(1, pojoDB.getClave());       
                pst.setString(2, pojoDB.getNombre());    
                pst.setString(3, pojoDB.getDireccion()); 
                pst.setString(4, pojoDB.getTelefono()); 

                pst.execute();
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
    };

    return con.execute(t);
}

 

    @Override
    public PojoEmpleado findById(Long id) {
        try {
            // Construir la consulta SQL de búsqueda
            String sql = " SELECT * FROM empleados WHERE "
                    + " clave='" + id + "'";
            
            ConexionDB con = ConexionDB.getInstance();
            ResultSet res = con.select(sql);

            //Procesar el resultado de la consulta
            if (res.next()) {
                // ️ Crear y poblar el objeto PojoEmpleado
                PojoEmpleado pojo = new PojoEmpleado();
                pojo.setClave(res.getLong("Clave"));
                pojo.setNombre(res.getString("Nombre"));
                pojo.setDireccion(res.getString("Direccion"));
                pojo.setTelefono(res.getString("Telefono"));
                
                logger.log(Level.INFO, String.format("Empleado encontrado:%s ID:%d  " ,pojo.getNombre() ,id ));
                return pojo;
            } else {
                logger.log(Level.INFO, "No se encontró empleado con ID: {0}", id);
                return null;
            }
            
        } catch (SQLException ex) {
         
            logger.log(Level.SEVERE, String.format("Error SQL en findById para ID %d: %s" , id , ex.getMessage()), ex);
            return null;
        } catch (Exception ex) {
            //  Capturar cualquier otra excepción
            logger.log(Level.SEVERE,String.format ("Excepción en findById para ID %d : %S" ,id , ex.getMessage()), ex);
            return null;
        }
    }

   
    @Override
    public List<PojoEmpleado> findAll() {
        try {
            
            List<PojoEmpleado> lista = new ArrayList<>();
            
            
            String sql = " SELECT * FROM empleados";
            
            ConexionDB con = ConexionDB.getInstance();
            ResultSet res = con.select(sql);

            while (res.next()) {
                
                PojoEmpleado pojo = new PojoEmpleado();
                pojo.setClave(res.getLong("Clave"));
                pojo.setNombre(res.getString("Nombre"));
                pojo.setDireccion(res.getString("Direccion"));
                pojo.setTelefono(res.getString("Telefono"));
                
                
                lista.add(pojo);
            }

            logger.log(Level.INFO, String.format(" Se encontraron empleados en total %d" ,lista.size()));
            return lista;
            
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, String.format("Error SQL en findAll: %s" , ex.getMessage()), ex);
            return null;
        } catch (Exception ex) {
            //  Capturar cualquier otra excepción
            logger.log(Level.SEVERE, String.format(" Excepción en findAll:%s " , ex.getMessage()), ex);
            return null;
        }
    }

    @Override
    public PojoEmpleado delete(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public PojoEmpleado update(PojoEmpleado pojo, Long id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

   
