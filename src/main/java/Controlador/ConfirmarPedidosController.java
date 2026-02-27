/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.DAO.PedidoDAO;
import Vista.Area_Confirmar_Pedidos;
import Vista.Ventana_Principal_Vendedor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ConfirmarPedidosController implements ActionListener {

    private Area_Confirmar_Pedidos vista;
    private PedidoDAO pedidoDAO;
    private int idUsuario;
    private int idPedidoSeleccionado = -1;

    public ConfirmarPedidosController(Area_Confirmar_Pedidos vista, int idUsuario) {
        this.vista = vista;
        this.idUsuario = idUsuario;
        this.pedidoDAO = new PedidoDAO();

        limpiarTablas();

        this.vista.btnBuscarDni.addActionListener(this);
        this.vista.btnConfirmar.addActionListener(this);
        this.vista.btnActualizarCantidad.addActionListener(this);
        this.vista.btnMenuPrincipal.addActionListener(this);
        this.vista.tblPedidos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = vista.tblPedidos.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    idPedidoSeleccionado = Integer.parseInt(vista.tblPedidos.getValueAt(filaSeleccionada, 0).toString());
                    cargarDatosDePedido(idPedidoSeleccionado);
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnBuscarDni) {
            buscarPedidosPorDni();
        } else if (e.getSource() == vista.btnConfirmar) {
            confirmarPedido();
        } else if (e.getSource() == vista.btnActualizarCantidad) {
            actualizarCantidadFaltante();
        } else if (e.getSource() == vista.btnMenuPrincipal) {
            volverMenu();
        }
    }

    private void limpiarTablas() {
        vista.tblPedidos.setModel(new DefaultTableModel(new Object[]{"ID Pedido Pendiente"}, 0));
        vista.tblDetalles.setModel(new DefaultTableModel(new Object[]{"Producto", "Cantidad Solicitada", "Stock Actual", "Disponible"}, 0));
        vista.txtNombreCliente.setText("");
        vista.txtFechaPedido.setText("");
    }

    private void buscarPedidosPorDni() {
        String dni = vista.txtDniBuscar.getText().trim();
        if (dni.length() != 8) {
            JOptionPane.showMessageDialog(vista, "Ingrese un DNI válido de 8 dígitos.");
            return;
        }

        DefaultTableModel modelo = pedidoDAO.obtenerIDsPedidosPendiente(dni);
        vista.tblPedidos.setModel(modelo);
        idPedidoSeleccionado = -1;

        if (modelo.getRowCount() == 0) {
            JOptionPane.showMessageDialog(vista, "Este cliente no tiene ningún pedido pendiente.");
        }
    }

    private void cargarDatosDePedido(int idPedido) {
        // Carga nombre y fecha
        String[] datosCabecera = pedidoDAO.obtenerDatosCabecera(idPedido);
        if (datosCabecera[0] != null) {
            vista.txtNombreCliente.setText(datosCabecera[0]);
            vista.txtFechaPedido.setText(datosCabecera[1]);
        }

        DefaultTableModel modeloDetalles = pedidoDAO.obtenerDetallesPedido(idPedido);
        vista.tblDetalles.setModel(modeloDetalles);
    }

    private void confirmarPedido() {
        if (idPedidoSeleccionado == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione un pedido primero.");
            return;
        }

        boolean todoDisponible = true;
        for (int i = 0; i < vista.tblDetalles.getRowCount(); i++) {
            String disponible = vista.tblDetalles.getValueAt(i, 3).toString(); 
            if (disponible.equalsIgnoreCase("No")) {
                todoDisponible = false;
                break;
            }
        }

        if (!todoDisponible) {
            JOptionPane.showMessageDialog(vista, 
                "No se puede confirmar la venta.\nEl pedido que supera el stoCK."
                        + "\n 'Actualizar Cantidad'." );
            return; 
        }

        int confirmacion = JOptionPane.showConfirmDialog(vista, "¿Desea confirmar el pedido y registrar la venta en la BD?", 
                "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean exito = pedidoDAO.confirmarPedido(idPedidoSeleccionado, idUsuario);
            if (exito) {
                JOptionPane.showMessageDialog(vista, "Pedido Confirmado y Venta generada exitosamente");
                buscarPedidosPorDni(); 
                vista.tblDetalles.setModel(new DefaultTableModel(new Object[]{"Producto", "Cantidad Solicitada", "Stock Actual", "Disponible"}, 0));
                vista.txtNombreCliente.setText("");
                vista.txtFechaPedido.setText("");
            } else {
                JOptionPane.showMessageDialog(vista, "Error al confirmar el pedido.");
            }
        }
    }

    private void actualizarCantidadFaltante() {
        if (idPedidoSeleccionado == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione un pedido primero.");
            return;
        }

        int filaSelec = vista.tblDetalles.getSelectedRow();
        if (filaSelec == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione un producto en la tabla de detalles para actualizar su cantidad.");
            return;
        }

        String nombreProducto = vista.tblDetalles.getValueAt(filaSelec, 0).toString();
        String stockActualStr = vista.tblDetalles.getValueAt(filaSelec, 2).toString();

        String nuevaCantStr = JOptionPane.showInputDialog(vista, 
            "Producto: " + nombreProducto + "\nStock disponible: " + stockActualStr + "\nIngrese la nueva cantidad:", "Actualizar Cantidad", JOptionPane.QUESTION_MESSAGE);
        
        if (nuevaCantStr != null && !nuevaCantStr.isEmpty()) {
            try {
                int nuevaCant = Integer.parseInt(nuevaCantStr);
                
                String mensajeBD = pedidoDAO.actualizarCantidadDetalle(idPedidoSeleccionado, nombreProducto, nuevaCant);
                
                if (mensajeBD.equals("Cantidad actualizada correctamente")) {
                    JOptionPane.showMessageDialog(vista, mensajeBD);
                    cargarDatosDePedido(idPedidoSeleccionado);
                } else {
                    JOptionPane.showMessageDialog(vista, mensajeBD);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "Por favor ingrese solo números.");
            }
        }
    }

    private void volverMenu() {
        vista.dispose();
        new Ventana_Principal_Vendedor(idUsuario).setVisible(true); 
    }
}