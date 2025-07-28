package org.example;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Carrito extends JFrame {


    private JPanel MainPanel;
    private JTable tblProductos;
    private JLabel lblCategoria;
    private JComboBox cmbCategoria;
    private JTable tblCarrito;
    private JButton btnBuscar;
    private JTextField txtNombre;
    private JTextField txtID;
    private JButton btnAgregar;
    private JButton btnEliminar;
    private JButton btnFactura;
    private JButton btnLimpiar;
    private JTextArea txtProducto;
    private JLabel lblCarrito;
    private JLabel lblProductos;
    private JLabel lblInfo;

    private DefaultTableModel modeloTabla;
    private ArrayList<Producto> listaProductos = new ArrayList<>();
    private ArrayList<Producto> carrito = new ArrayList<>();
    //Lista ya filtrada para poder agregar de manera correcta objetos a tblCarrito
    private ArrayList<Producto> listaFiltrada = new ArrayList<>();
    private ConexionDB conexionDB = new ConexionDB();

    public Carrito(){
        setContentPane(MainPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle("Carrito de Compras");



        DefaultTableModel modeloProductos = new DefaultTableModel();
        modeloProductos.addColumn("Código");
        modeloProductos.addColumn("Nombre");
        modeloProductos.addColumn("Inventario");
        modeloProductos.addColumn("Precio");
        tblProductos.setModel(modeloProductos);

        DefaultTableModel modeloCarrito = new DefaultTableModel();
        modeloCarrito.addColumn("Código");
        modeloCarrito.addColumn("Nombre");
        modeloCarrito.addColumn("Inventario");
        modeloCarrito.addColumn("Precio");
        tblCarrito.setModel(modeloCarrito);

        //Para tener una opcion default vacia
        cmbCategoria.addItem(null);
        for (Categoria c : Categoria.values()){
            cmbCategoria.addItem(c);
        }

        cargarProductosDesdeBD();
        for (Producto p : listaProductos) {
            modeloProductos.addRow(new Object[]{p.getCodigo(), p.getNombre(), p.getInventario(), p.getPrecio()});
        }



        //Para poder anadir productos dandole click al producto desde la tabla
        tblProductos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = tblProductos.getSelectedRow();
                if (fila != -1 && fila < listaFiltrada.size()) {
                    Producto producto = listaFiltrada.get(fila);
                    txtProducto.setText("Código: " + producto.getCodigo() +
                            "\nNombre: " + producto.getNombre() +
                            "\nCantidad: " + producto.getInventario() +
                            "\nPrecio: ₡" + producto.getPrecio());
                }
            }
        });


        //Anade la info que se selecciona en tblProducto a tblCarrito
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int fila = tblProductos.getSelectedRow();
                if (fila != -1 && fila < listaFiltrada.size()) {
                    Producto seleccionado = listaFiltrada.get(fila);
                    carrito.add(seleccionado);

                    DefaultTableModel modeloCarrito = (DefaultTableModel) tblCarrito.getModel();
                    modeloCarrito.addRow(new Object[]{
                            seleccionado.getCodigo(),
                            seleccionado.getNombre(),
                            seleccionado.getInventario(),
                            seleccionado.getPrecio()
                    });
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un producto primero.");
                }
            }
        });


        //Elimina un elemento seleccionado desde tblCarrito
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = tblCarrito.getSelectedRow();
                if (fila != -1) {
                    carrito.remove(fila);
                    DefaultTableModel modeloCarrito = (DefaultTableModel) tblCarrito.getModel();
                    modeloCarrito.removeRow(fila);
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona un producto del carrito para eliminar.");
                }
            }
        });


        //Elimina la info completa de tblCarrito
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carrito.clear();
                DefaultTableModel modeloCarrito = (DefaultTableModel) tblCarrito.getModel();
                modeloCarrito.setRowCount(0);
            }
        });

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Categoria categoriaSeleccionada = (Categoria) cmbCategoria.getSelectedItem();


                //Limpia la tabla para mostrar solo lo que se busca
                DefaultTableModel modeloProductos = (DefaultTableModel) tblProductos.getModel();
                modeloProductos.setRowCount(0);


                //esto de abajo permite poder anadir de manera correcta cada objeto despues de realizar una busqueda, acomodando los indices de la tabla para que no se agreguen otras cosas
                listaFiltrada.clear();

                for (Producto producto : listaProductos) {
                    if (producto.getCategoria() == categoriaSeleccionada) {
                        listaFiltrada.add(producto);
                        modeloProductos.addRow(new Object[]{
                                producto.getCodigo(),
                                producto.getNombre(),
                                producto.getInventario(),
                                producto.getPrecio()
                        });
                    }
                }
            }
        });
        btnFactura.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (carrito.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No hay productos en el carrito.");
                    return;
                }

                ArrayList<String> factura = new ArrayList<>();
                double totalCompra = 0.0;

                try {
                    conexionDB.setConexion();

                    for (Producto p : carrito) {
                        // Consultar inventario actual
                        String consultaCantidad = "SELECT Cantidad FROM inventario WHERE ID_Inventario = ?";
                        conexionDB.setConsulta(consultaCantidad);
                        conexionDB.getConsulta().setInt(1, p.getCodigo());
                        ResultSet rs = conexionDB.getResultado();

                        if (rs.next()) {
                            int cantidadActual = rs.getInt("Cantidad");
                            int nuevaCantidad = cantidadActual - 1;

                            if (nuevaCantidad < 0) {
                                JOptionPane.showMessageDialog(null, "Inventario insuficiente para: " + p.getNombre());
                                conexionDB.cerrarConexion();
                                return;
                            }

                            // Actualizar inventario
                            String updateInventario = "UPDATE inventario SET Cantidad = ? WHERE ID_Inventario = ?";
                            conexionDB.setConsulta(updateInventario);
                            conexionDB.getConsulta().setInt(1, nuevaCantidad);
                            conexionDB.getConsulta().setInt(2, p.getCodigo());

                            int filasActualizadas = conexionDB.getConsulta().executeUpdate();
                            if (filasActualizadas > 0) {
                                factura.add(p.getNombre() + " - $" + p.getPrecio());
                                totalCompra += p.getPrecio();
                            } else {
                                JOptionPane.showMessageDialog(null, "Error al actualizar inventario para: " + p.getNombre());
                                conexionDB.cerrarConexion();
                                return;
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Producto no encontrado en inventario: " + p.getNombre());
                            conexionDB.cerrarConexion();
                            return;
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error durante la actualización de inventario: " + ex.getMessage());
                    return;
                } finally {
                    conexionDB.cerrarConexion();
                }

                // Generar archivo
                try (DataOutputStream out = new DataOutputStream(new FileOutputStream("Factura.txt", true))) {
                    out.writeBytes("*** Reporte de Compra ***\n");
                    out.writeBytes("Total: $" + totalCompra + "\n");
                    out.writeBytes("Productos Comprados:\n");
                    for (String linea : factura) {
                        out.writeBytes("- " + linea + "\n");
                    }
                    out.writeBytes("\n");

                    JOptionPane.showMessageDialog(null, "Factura generada correctamente en 'Factura.txt'");


                    carrito.clear();
                    ((DefaultTableModel) tblCarrito.getModel()).setRowCount(0);


                    cargarProductosDesdeBD();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al escribir la factura: " + ex.getMessage());
                }
            }
        });


    }

    private void cargarProductosDesdeBD() {
        listaProductos.clear();

        conexionDB.setConexion();
        String copiaDatos = "SELECT i.ID_Inventario, i.Nombre_Producto, i.Precio, i.Cantidad, c.Nombre_Categoria " +
                "FROM inventario i JOIN categoria c ON i.ID_Categoria = c.ID_Categoria";
        conexionDB.setConsulta(copiaDatos);

        try (ResultSet rs = conexionDB.getResultado()) {
            while (rs != null && rs.next()) {
                int codigo = rs.getInt("ID_Inventario");
                String nombre = rs.getString("Nombre_Producto");
                double precio = rs.getDouble("Precio");
                int inventario = rs.getInt("Cantidad");
                String categoriaStr = rs.getString("Nombre_Categoria");

                Categoria categoria = Categoria.valueOf(categoriaStr.toUpperCase());
                Producto p = new ProductoElectronico(codigo, nombre, precio, inventario, categoria);
                listaProductos.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar productos desde la base de datos.");
        } finally {
            conexionDB.cerrarConexion();
        }
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }


}
