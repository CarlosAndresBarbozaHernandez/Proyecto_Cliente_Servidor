package org.example;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    public Carrito(){
        setContentPane(MainPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle("Carrito de Compras");


        //Datos temporales antes de la BD
        listaProductos.add(new ProductoElectronico(101, "Laptop HP", 550000, 10, Categoria.COMPUTADORAS));
        listaProductos.add(new ProductoElectronico(102, "iPhone 14", 800000, 5, Categoria.CELULARES));
        listaProductos.add(new ProductoElectronico(103, "Smart TV LG", 400000, 8, Categoria.TELEVISORES));

        DefaultTableModel modeloProductos = new DefaultTableModel();
        modeloProductos.addColumn("Código");
        modeloProductos.addColumn("Nombre");
        modeloProductos.addColumn("Inventario");
        modeloProductos.addColumn("Precio");
        tblProductos.setModel(modeloProductos);

        for (Producto p : listaProductos) {
            modeloProductos.addRow(new Object[]{p.getCodigo(), p.getNombre(), p.getInventario(), p.getPrecio()});
        }

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



    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }


}
