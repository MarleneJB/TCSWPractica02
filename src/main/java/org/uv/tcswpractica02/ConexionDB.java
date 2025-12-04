/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.tcswpractica02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ConexionDB {

    private static ConexionDB cx = null;

    public static ConexionDB getInstance() {
        try {
            if (cx == null) {
                
                cx = new ConexionDB();
            }
            return cx;
        } catch (Exception ex) {
            Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, " Error al crear instancia: " , ex);
            return null;
        }
    }

    private String url = "jdbc:postgresql://localhost:5432/Empleados";
    private String usr = "postgres"; 
    private String pwd = "kimi";
    private Connection con = null;

   
    private ConexionDB() {
        try {
            
            con = DriverManager.getConnection(url, usr, pwd);
            Logger.getLogger(ConexionDB.class.getName()).log(Level.INFO, " Se conectó correctamente a la base de datos");
        } catch (SQLException ex) {
            Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, " Error crítico de conexión a BD: " , ex);
        } catch (Exception ex) {
            Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, " Error inesperado en constructor: " , ex);
        }
    }

    public boolean update(String sql) {
        Statement st = null;
        try {
           
            st = con.createStatement();
            int rowsAffected = st.executeUpdate(sql);
            
           Logger.getLogger(ConexionDB.class.getName()).log(Level.INFO, "️ Update ejecutado correctamente. Filas afectadas: {0}", 
           rowsAffected
           );

            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, String.format("Error SQL en update. SQL: %s", sql), ex);

            return false;
        } catch (Exception ex) {
            Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, 
                " Error inesperado en update: " , ex);
            return false;
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, 
                    " Error al cerrar statement en update: " , ex);
            }
        }
    }

   
    public ResultSet deleteAndReturn(String selectSql, String deleteSql) {
        ResultSet resultado = null;
        try {
            Logger.getLogger(ConexionDB.class.getName()).log(Level.INFO, 
                "Iniciando proceso de eliminación con retorno de datos");
            
            resultado = select(selectSql);
            
            if (resultado != null) {
                Logger.getLogger(ConexionDB.class.getName()).log(Level.INFO, 
                    "Registro encontrado, procediendo a eliminar");

                boolean deleteResult = update(deleteSql);
                if (deleteResult) {
                    Logger.getLogger(ConexionDB.class.getName()).log(Level.INFO, 
                        "Eliminación completada exitosamente");
                } else {
                    Logger.getLogger(ConexionDB.class.getName()).log(Level.WARNING, 
                        "Problema durante la eliminación");
                }
            } else {
                Logger.getLogger(ConexionDB.class.getName()).log(Level.WARNING, 
                    "️No se encontró registro para eliminar");
            }
            
            return resultado;
            
        } catch (Exception ex) {
            Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, 
                "Error en deleteAndReturn: " , ex);
            return null;
        }
    }

    public ResultSet select(String sql) {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            Logger.getLogger(ConexionDB.class.getName()).log(Level.INFO, 
                " Select ejecutado correctamente");
            return rs;
            
        } catch (SQLException ex) {
            Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, 
                String.format(" Error SQL en select: %s | SQL: %s", ex.getMessage(), sql), ex);
            return null;
        }
    }

   
    public boolean exectute(String sql) {
        Statement st = null;
        try {
            // 🔨 Crear y ejecutar la consulta SQL genérica
            st = con.createStatement();
            boolean result = st.execute(sql);
            
            Logger.getLogger(ConexionDB.class.getName()).log(Level.INFO, ("Execute ejecutado correctamente. Resultado: ") ,result);
            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, String.format("Error SQL en execute: %s | SQL: %s" ,ex.getMessage(), sql), ex);
            return false;
        } catch (Exception ex) {
            Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, 
                " Error inesperado en execute: ", ex);
            return false;
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, 
                    "Error al cerrar statement en execute: " , ex);
            }
        }
    }
    public boolean execute(TransacctionDB t){
        return t.execute(con);
    }
}