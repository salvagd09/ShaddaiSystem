package Controlador;

import Modelo.DAO.ClienteDAO;
import Vista.Area_Registar_Cliente_Frecuente;
import Vista.Ventana_Principal_Vendedor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Registro_CliFrecuente implements ActionListener {

    private Area_Registar_Cliente_Frecuente vista;
    private ClienteDAO clienteDAO;
    private int idUsuario;

    public Registro_CliFrecuente(Area_Registar_Cliente_Frecuente vista, int idUsuario) {
        this.vista = vista;
        this.idUsuario = idUsuario;
        this.clienteDAO = new ClienteDAO();

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID Venta");
        modelo.addColumn("Fecha de Compra");
        modelo.addColumn("Nombre Cliente");
        this.vista.tblHistorial.setModel(modelo);
        
        this.vista.btnRegistrar.addActionListener(this);
        this.vista.btnBuscar.addActionListener(this);
        this.vista.btnMenuPrincipal.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnRegistrar) {
            registrarFrecuente();
        } else if (e.getSource() == vista.btnBuscar) {
            buscarHistorialDeCompras();
        } else if (e.getSource() == vista.btnMenuPrincipal) {
            volverMenuPrincipal(); 
        }
    }
    
    private void volverMenuPrincipal() {
        vista.dispose(); 
        new Ventana_Principal_Vendedor(idUsuario).setVisible(true); 
    }
   
    private void buscarHistorialDeCompras() {
        String numDocumento = vista.txtDocumento.getText().trim();

        if (numDocumento.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "ingresar el número de documento para buscar el historial.");
            return;
        }

        String dni = null;
        String ruc = null;

        if (vista.rbDni.isSelected()) {
            dni = numDocumento;
            if (dni.length() != 8) {
                JOptionPane.showMessageDialog(vista, "El DNI debe tener 8 dígitos.");
                return;
            }
        } else if (vista.rbRuc.isSelected()) {
            ruc = numDocumento;
            if (ruc.length() != 11) {
                JOptionPane.showMessageDialog(vista, "El RUC debe tener 11 dígitos.");
                return;
            }
        } else {
             JOptionPane.showMessageDialog(vista, "Seleccione el tipo de documento).");
             return;
        }

        // Obtener historial de compras
        DefaultTableModel modeloHistorial = clienteDAO.obtenerHistorialCompras(dni, ruc);
        vista.tblHistorial.setModel(modeloHistorial);

        if (modeloHistorial.getRowCount() == 0) {
            JOptionPane.showMessageDialog(vista, "El cliente no registra compras en este mes o no existe.");
            vista.txtNombreClienteResult.setText("");
            vista.txtTotalComprasMes.setText("0");
        } else {
            String nombre = modeloHistorial.getValueAt(0, 2).toString();
            
            if(nombre == null || nombre.trim().isEmpty() || nombre.equals("Cliente Público")){
                vista.txtNombreClienteResult.setText("Cliente sin nombbre");
            } else {
                vista.txtNombreClienteResult.setText(nombre);
            }
            int totalCompras = clienteDAO.obtenerTotalComprasMes(dni, ruc);
            vista.txtTotalComprasMes.setText(String.valueOf(totalCompras));
        }
    }

    private void registrarFrecuente() {
        String documentoIngresado = vista.txtDocumento.getText().trim();
        String telefono = vista.txtTelefono.getText().trim();
        String correo = vista.txtCorreo.getText().trim();
        String direccion = vista.txtDireccion.getText().trim();

        if (documentoIngresado.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Debe ingresar el número de documento");
        }

        if (telefono.isEmpty() || correo.isEmpty() || direccion.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Por favor, complete todos los campos de contacto).");
            return;
        }

        String dni = null;
        String ruc = null;

        if (vista.rbDni.isSelected()) {
            dni = documentoIngresado;
        } else if (vista.rbRuc.isSelected()) {
            ruc = documentoIngresado;
        }

        boolean exito = clienteDAO.registrarClienteFrecuente(dni, ruc, telefono, correo, direccion);
        if (exito) {
            JOptionPane.showMessageDialog(vista, "Cliente actualizado a 'Frecuente'!"); 
            vista.txtDocumento.setText("");
            vista.txtTelefono.setText("");
            vista.txtCorreo.setText("");
            vista.txtDireccion.setText("");
            vista.txtNombreClienteResult.setText("");
            vista.txtTotalComprasMes.setText("");
            ((DefaultTableModel) vista.tblHistorial.getModel()).setRowCount(0);
            vista.rbDni.setSelected(true); 
        } else {
            JOptionPane.showMessageDialog(vista, "Asegúrese de que el cliente exista en la bd y tenga compras.");
        }
    }
}