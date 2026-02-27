/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;


import Modelo.DAO.PedidoDAO;
import Modelo.DAO.ProductoDAO;
import Modelo.DAO.VentaDAO;
import Modelo.DTO.ResultadoOperacionDTO;
import Modelo.Entidad.DetalleVenta;
import Modelo.Entidad.Producto;
import Vista.Login;
import Vista.ModuloVentas;
import Vista.Ventana_Principal_Vendedor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Usuario
 */
    public class VentaRegistroController implements ActionListener {
    private ModuloVentas vista;
    private VentaDAO ventaDAO;
    private PedidoDAO pedidoDAO; 
    private ProductoDAO productoDAO;
    private List<DetalleVenta> carrito;
    private double totalVenta = 0;
    private int idUsuario;
    public VentaRegistroController(ModuloVentas vista, int idUsuario) {
        this.vista = vista;
        this.idUsuario = idUsuario;
        this.ventaDAO = new VentaDAO();
        this.productoDAO = new ProductoDAO();
        this.pedidoDAO = new PedidoDAO();
        this.carrito = new ArrayList<>();
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Código");
        modelo.addColumn("Producto");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Precio");
        modelo.addColumn("Subtotal");
        this.vista.tblVentas.setModel(modelo);

        this.vista.btnAgregar.addActionListener(this);
        this.vista.btnFinalizarVenta.addActionListener(this);
        this.vista.btnRegistrarWhatsapp.addActionListener(this);
        this.vista.btnMenuPrincipal.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnAgregar) {
            agregarAlCarrito();
        } else if (e.getSource() == vista.btnFinalizarVenta) {
            finalizarVenta();
        } else if (e.getSource() == vista.btnRegistrarWhatsapp) {
            registrarPedidoWhatsapp();
        } else if (e.getSource() == vista.btnMenuPrincipal) {
            volverMenuPrincipal();
        }
    }
    
    private void volverMenuPrincipal() {
        int resultado=JOptionPane.showConfirmDialog(null,"Estás seguro de querer regresar a la pantalla principal?","Confirmación de regreso a la VP",JOptionPane.YES_NO_OPTION);
        if(resultado==0){
          vista.dispose();
         new Ventana_Principal_Vendedor(idUsuario).setVisible(true); 
    }
    }
    private void agregarAlCarrito() {
        String nombreProducto = vista.txtNombreProducto.getText().trim();
        int cantidad = (Integer) vista.spinCantidad.getValue();

        if (nombreProducto.isEmpty() || cantidad <= 0) {
            JOptionPane.showMessageDialog(vista, "Ingrese producto y cantidad mayor a 0.");
            return;
        }

        try {
            Producto producto = productoDAO.buscarPorNombre(nombreProducto);
            if (producto == null) {
                JOptionPane.showMessageDialog(vista, "Producto no encontrado.");
                return;
            }

            ResultadoOperacionDTO resultado = ventaDAO.validarStockTienda(producto.getCodigoProducto(), cantidad);

            if ("OK".equals(resultado.getEstado())) {
                DetalleVenta detalle = new DetalleVenta();
                detalle.setCodigoProductoTemp(producto.getCodigoProducto()); 
                detalle.setCantidad(cantidad);
                detalle.setPrecioUnitario(resultado.getPrecio());
                carrito.add(detalle);

                totalVenta += resultado.getSubtotal();

                DefaultTableModel modelo = (DefaultTableModel) vista.tblVentas.getModel();
                modelo.addRow(new Object[]{producto.getCodigoProducto(), resultado.getNombreProducto(), cantidad, resultado.getPrecio(), resultado.getSubtotal()});
                vista.txtTotal.setText(String.format("%.2f", totalVenta));
                vista.txtNombreProducto.setText("");
                vista.spinCantidad.setValue(1);

            } else {
                JOptionPane.showMessageDialog(vista, resultado.getMensaje(), "Sin Stock", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error: " + ex.getMessage());
        }
    }

    private void finalizarVenta() {
        if (carrito.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "El carrito está vacío.");
            return;
        }

        String tipoCliente = vista.rbPersona.isSelected() ? "Persona" : "Empresa";
        String dniCliente = vista.txtDni.getText().trim(); 
        String rucCliente = vista.RUCText.getText().trim();
        String nombreCliente = vista.txtNombreCliente.getText().trim(); 
        String tipoComprobante = vista.rbFactura.isSelected() ? "Factura" : "Boleta";
        String metodoPago = vista.rbYape.isSelected() ? "Yape/Plin" : "Efectivo";

        if (tipoCliente.equals("Persona") && dniCliente.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Debe ingresar el DNI para clientes de tipo Persona.");
            return;
        }
        if (tipoCliente.equals("Empresa") && rucCliente.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Debe ingresar el RUC para clientes de tipo Empresa.");
            return;
        }
        
        boolean exito = ventaDAO.registrarVentaCompleta("Tienda", idUsuario, tipoCliente, dniCliente, rucCliente, nombreCliente, tipoComprobante, metodoPago, carrito);

        if (exito) {
            JOptionPane.showMessageDialog(vista, "Venta registrada con éxito");
            ((DefaultTableModel) vista.tblVentas.getModel()).setRowCount(0);
            carrito.clear();
            totalVenta = 0.0;
            vista.txtTotal.setText("0.00");
            vista.txtDni.setText("");
            vista.RUCText.setText("");
            vista.txtNombreCliente.setText(""); 
        } else {
            JOptionPane.showMessageDialog(vista, "Error al guardar en BD.");
        }
    }

    private void registrarPedidoWhatsapp() {
        if (carrito.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "El carrito está vacío, agregue productos .");
            return;
        }

        String nombreCliente = vista.txtNombreCliente.getText().trim();
        if (nombreCliente.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Ingresar el Nombre y Apellido del cliente para un pedido de Whatsapp.");
            return;
        }

        String tipoCliente = vista.rbPersona.isSelected() ? "Persona" : "Empresa";
        String dni = vista.txtDni.getText().trim();
        String ruc = vista.RUCText.getText().trim();

        if (tipoCliente.equals("Persona") && dni.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Debe ingresar el DNI para clientes de tipo Persona.");
            return;
        }
        if (tipoCliente.equals("Empresa") && ruc.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Debe ingresar el RUC para clientes de tipo Empresa.");
            return;
        }
        
        boolean exito = pedidoDAO.registrarPedidoPendienteWhatsapp(tipoCliente, dni, ruc, nombreCliente, carrito);

        if (exito) {
            JOptionPane.showMessageDialog(vista, "Pedido por WhatsApp registrado como PENDIENTE\n"
                    + "\nEl stock no se descontará hasta ser confirmado.");
            
            
            ((DefaultTableModel) vista.tblVentas.getModel()).setRowCount(0);
            carrito.clear();
            totalVenta = 0.0;
            vista.txtTotal.setText("0.00");
            vista.txtNombreCliente.setText("");
            vista.txtDni.setText("");
            vista.RUCText.setText("");
            
        } else {
            JOptionPane.showMessageDialog(vista, "error al registrar el pedido pendiente en la bd.");
        }
    }
}

