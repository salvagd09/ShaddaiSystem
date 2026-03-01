/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;


import Modelo.DAO.ClienteDAO;
import Modelo.DAO.PedidoDAO;
import Modelo.DAO.ProductoDAO;
import Modelo.DAO.VentaDAO;
import Modelo.DTO.DatosParaClienteFrecuenteDTO;
import Modelo.DTO.DetallesPedidoConfirmadoDTO;
import Modelo.DTO.MostrarDatosClienteDTO;
import Modelo.DTO.ResultadoOperacionDTO;
import Modelo.Entidad.Cliente;
import Modelo.Entidad.DetalleVenta;
import Modelo.Entidad.Producto;
import Modelo.Enum.TipoCliente;
import Vista.Login;
import Vista.ModuloVentas;
import Vista.Ventana_Principal_Vendedor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
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
    private ClienteDAO clienteDAO;
    private ProductoDAO productoDAO;
    private List<DetalleVenta> carrito;
    private double totalVenta = 0;
    private int idUsuario;
    private Integer idSeleccionado;
    public VentaRegistroController(ModuloVentas vista, int idUsuario) {
        this.vista = vista;
        this.idUsuario = idUsuario;
        this.ventaDAO = new VentaDAO();
        this.productoDAO = new ProductoDAO();
        this.pedidoDAO = new PedidoDAO();
        this.clienteDAO=new ClienteDAO();
        this.carrito = new ArrayList<>();
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Código");
        modelo.addColumn("Producto");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Precio");
        modelo.addColumn("Subtotal");
        this.vista.tblVentas.setModel(modelo);
        this.vista.btnPagoPedidoConfirmado.addActionListener(this);
        this.vista.btnAgregar.addActionListener(this);
        this.vista.btnFinalizarVenta.addActionListener(this);
        this.vista.btnRegistrarWhatsapp.addActionListener(this);
        this.vista.btnMenuPrincipal.addActionListener(this);
        this.vista.txtDni.addActionListener(this);
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
        }  else if (e.getSource() == vista.btnPagoPedidoConfirmado) {
            PagoPedidoConfirmado();
        } else if(e.getSource()==vista.txtDni){
            ObtenerClienteAnterior();
        }
    }
    private void volverMenuPrincipal() {
        int resultado=JOptionPane.showConfirmDialog(null,"Estás seguro de querer regresar a la pantalla principal?","Confirmación de regreso a la VP",JOptionPane.YES_NO_OPTION);
        if(resultado==0){
          vista.dispose();
         new Ventana_Principal_Vendedor(idUsuario).setVisible(true); 
        }
    }
    private void ObtenerClienteAnterior(){
             if(vista.rbPersona.isSelected()){
                MostrarDatosClienteDTO mostrar=clienteDAO.MostrarDatosClientes(TipoCliente.PERSONA, vista.txtDni.getText());
                vista.txtNombreCliente.setText(mostrar.getNombreCompleto());
                JOptionPane.showMessageDialog(vista, mostrar.getMensaje());
                DatosParaClienteFrecuenteDTO ventasRealizadas=ventaDAO.ContabilizarVentasMes(vista.txtDni.getText(),null);
                JOptionPane.showMessageDialog(vista, mostrar.getMensaje()+".El cliente ha realizado: "+ventasRealizadas.getTotalCompraMes()+" compras");
             } else if(vista.rbEmpresa.isSelected()){
                MostrarDatosClienteDTO mostrar=clienteDAO.MostrarDatosClientes(TipoCliente.EMPRESA, vista.txtDni.getText());
                vista.txtNombreCliente.setText(mostrar.getNombreCompleto());
                vista.RUCText.setText(mostrar.getRUC());
                DatosParaClienteFrecuenteDTO ventasRealizadas=ventaDAO.ContabilizarVentasMes(vista.txtDni.getText(), vista.RUCText.getText());
                JOptionPane.showMessageDialog(vista, mostrar.getMensaje()+".El cliente ha realizado: "+ventasRealizadas.getTotalCompraMes()+" compras");
             }
       
    }
    private void PagoPedidoConfirmado() {                                                        
            String dni = vista.txtDni.getText().trim();
            if (dni.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Ingrese el DNI del cliente.");
                return;
            }
            List<Integer> pedidosConfirmados = pedidoDAO.obtenerIDsPedidosConfirmados(dni);
             if (pedidosConfirmados.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Este cliente no tiene pedidos confirmados pendientes de pago.");
            return;
            }
            Integer[] opciones = pedidosConfirmados.toArray(new Integer[0]);
             idSeleccionado = (Integer) JOptionPane.showInputDialog(
                vista,
                "Seleccione el ID del pedido a pagar:",
                "Pedidos Confirmados",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
            );
                if (idSeleccionado != null) {
                    Cliente clientePConfirmado=pedidoDAO.ObtenerPedidoConfirmado(idSeleccionado);
                    if(vista.rbEmpresa.isSelected()){
                        vista.txtNombreCliente.setText(clientePConfirmado.getNombreCompleto());
                        vista.txtDni.setText(clientePConfirmado.getDNI());
                        vista.RUCText.setText(clientePConfirmado.getRUC());
                    }
                    else if(vista.rbPersona.isSelected()){
                        vista.txtNombreCliente.setText(clientePConfirmado.getNombreCompleto());
                        vista.txtDni.setText(clientePConfirmado.getDNI());        
                    }
                    List<DetallesPedidoConfirmadoDTO> detallesConfirmados=pedidoDAO.ObtenerDetallesPConfirmado(idSeleccionado);
                      DefaultTableModel modelo = (DefaultTableModel) vista.tblVentas.getModel();
                      for (DetallesPedidoConfirmadoDTO detallePC : detallesConfirmados) {
                        modelo.addRow(new Object[]{
                            detallePC.getCodigoProducto(),
                            detallePC.getNombre(),
                            detallePC.getCantidad(),
                            detallePC.getPrecio(),
                            detallePC.getSubTotal()
                        });
                        totalVenta += detallePC.getSubTotal();
                        vista.txtTotal.setText(String.format("%.2f", totalVenta));

                        // ← Agregar esto:
                        DetalleVenta detalle = new DetalleVenta();
                        detalle.setCodigoProductoTemp(detallePC.getCodigoProducto());
                        detalle.setCantidad(detallePC.getCantidad());
                        detalle.setPrecioUnitario(detallePC.getPrecio());
                        carrito.add(detalle);
                      }
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
        boolean exito=true;
        String lugarVenta=vista.rbTienda.isSelected()? "Tienda":"Whatsapp";
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
        if("Tienda".equals(lugarVenta)){
            exito = ventaDAO.registrarVentaCompleta(lugarVenta, idUsuario,null,tipoCliente, dniCliente, rucCliente, nombreCliente, tipoComprobante, metodoPago, carrito);
        } else if("Whatsapp".equals(lugarVenta)){
            boolean finalizado=pedidoDAO.FinalizarPedido(idSeleccionado);
            exito = ventaDAO.registrarVentaCompleta(lugarVenta, idUsuario,idSeleccionado,tipoCliente, dniCliente, rucCliente, nombreCliente, tipoComprobante, metodoPago, carrito);
            if(finalizado){
                JOptionPane.showMessageDialog(vista, "Pedido finalizado");
            }
        }
        if (exito) {
            generarPDF();
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

    private void generarPDF() {
        String comprobante=vista.rbBoleta.isSelected()?"Boleta":"Factura";
        String tipoCliente=vista.rbEmpresa.isSelected()?"Empresa":"Persona";
        String metodoPago=vista.rbYape.isSelected()?"Yape/Plin":"Efectivo";
      try{
      javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setSelectedFile(new java.io.File("ComprobanteVenta.pdf"));
        int opcion = fileChooser.showSaveDialog(vista);
        if (opcion != javax.swing.JFileChooser.APPROVE_OPTION) return;
        String ruta = fileChooser.getSelectedFile().getAbsolutePath();
        if (!ruta.endsWith(".pdf")) ruta += ".pdf";
        com.itextpdf.text.Document document = new com.itextpdf.text.Document();
        com.itextpdf.text.pdf.PdfWriter.getInstance(document, new java.io.FileOutputStream(ruta));
        document.open();
        com.itextpdf.text.Font fuenteTitulo = new com.itextpdf.text.Font(
        com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 24, com.itextpdf.text.Font.BOLD);
        com.itextpdf.text.Paragraph titulo = new com.itextpdf.text.Paragraph(
        "Comprobante de Ventas", fuenteTitulo);
        titulo.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
        document.add(titulo);
        String fechaFormateada = new java.text.SimpleDateFormat("dd 'de' MMMM 'de' yyyy, HH:mm:ss", 
         new java.util.Locale("es", "PE")).format(new java.util.Date());
        document.add(new com.itextpdf.text.Paragraph("Gracias por su preferencia!!!"));
        document.add(com.itextpdf.text.Chunk.NEWLINE);
        document.add(new com.itextpdf.text.Paragraph("Fecha: " +fechaFormateada));
        document.add(com.itextpdf.text.Chunk.NEWLINE);
        document.add(new com.itextpdf.text.Paragraph("Tipo de comprobante: " +comprobante));
        document.add(com.itextpdf.text.Chunk.NEWLINE);
        document.add(new com.itextpdf.text.Paragraph("Nombre del cliente:"+vista.txtNombreCliente.getText()));
        document.add(com.itextpdf.text.Chunk.NEWLINE);
        if("Persona".equals(tipoCliente)){
            document.add(new com.itextpdf.text.Paragraph("DNI del cliente:"+vista.txtDni.getText()));
            document.add(com.itextpdf.text.Chunk.NEWLINE);
        }
        else if("Empresa".equals(tipoCliente)){
            document.add(new com.itextpdf.text.Paragraph("DNI del cliente:"+vista.txtDni.getText()));
            document.add(com.itextpdf.text.Chunk.NEWLINE);
            document.add(new com.itextpdf.text.Paragraph("RUC del cliente:"+vista.RUCText.getText()));
            document.add(com.itextpdf.text.Chunk.NEWLINE);
        }
        com.itextpdf.text.Paragraph subtitulo=new com.itextpdf.text.Paragraph("Productos comprados:");
        subtitulo.setSpacingBefore(5f);
        subtitulo.setSpacingAfter(5f);
        document.add(subtitulo);
        com.itextpdf.text.pdf.PdfPTable tabla = new com.itextpdf.text.pdf.PdfPTable(5);
        tabla.addCell("Codigo");
        tabla.addCell("Producto");
        tabla.addCell("Cantidad");
        tabla.addCell("Precio unitario");
        tabla.addCell("Subtotal");
        DefaultTableModel model = (DefaultTableModel) vista.tblVentas.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            tabla.addCell(model.getValueAt(i, 0).toString());
            tabla.addCell(model.getValueAt(i, 1).toString());
            tabla.addCell(model.getValueAt(i, 2).toString());
            tabla.addCell(model.getValueAt(i, 3).toString());
            tabla.addCell(model.getValueAt(i, 4).toString());
        }
        document.add(tabla);
        document.add(com.itextpdf.text.Chunk.NEWLINE);
        com.itextpdf.text.Font fuenteTotal = new com.itextpdf.text.Font(
        com.itextpdf.text.Font.FontFamily.HELVETICA, 18, com.itextpdf.text.Font.BOLD, new com.itextpdf.text.BaseColor(255, 214, 210));
        document.add(new com.itextpdf.text.Paragraph("Total de la venta:"+vista.txtTotal.getText(),fuenteTotal));
        document.add(com.itextpdf.text.Chunk.NEWLINE);
        document.add(new com.itextpdf.text.Paragraph("Método de pago:"+metodoPago));
        document.close();
        JOptionPane.showMessageDialog(vista, "PDF generado exitosamente en:\n" + ruta);
      }
       catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(vista, "Error al generar el PDF: " + e.getMessage());
        }
    }
}

