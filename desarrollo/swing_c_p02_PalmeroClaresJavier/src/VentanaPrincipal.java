import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Clase que representa la ventana principal de la aplicación de gestión hotelera.
 */
public class VentanaPrincipal extends JFrame {

    /** Etiqueta para mostrar la imagen del hotel. */
    private JLabel imagenHotel;

    /** Dimensiones iniciales de la ventana. */
    private Dimension tamanoInicial;

    /**
     * Constructor que inicializa la ventana principal.
     */
    public VentanaPrincipal() {
        setTitle("Gestión Hotel Continental");
        setIconImage(escalarImagen("/recursos/anagrama.png", 64, 64).getImage());
        configurarVentana();
        inicializarComponentes();
        configurarMenu();
        setVisible(true);
    }

    /**
     * Configura las propiedades de la ventana principal.
     */
    private void configurarVentana() {
        Dimension tamanoPantalla = Toolkit.getDefaultToolkit().getScreenSize();
        tamanoInicial = new Dimension(tamanoPantalla.width / 2, tamanoPantalla.height / 2);
        setSize(tamanoInicial);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(204, 229, 255));
    }

    /**
     * Inicializa los componentes visuales de la ventana.
     */
    private void inicializarComponentes() {
        JLabel tituloHotel = new JLabel("Hotel Continental", SwingConstants.CENTER);
        tituloHotel.setFont(new Font("Arial", Font.BOLD, 24));
        tituloHotel.setForeground(Color.DARK_GRAY);
        tituloHotel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(tituloHotel, BorderLayout.NORTH);

        imagenHotel = new JLabel("", SwingConstants.CENTER);
        actualizarImagenHotel((int) (tamanoInicial.width * 3.5), tamanoInicial.height * 2 / 3);
        add(imagenHotel, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setOpaque(false);

        // Botones para la gestión de reservas
        panelBotones.add(crearBoton("Alta Reservas", "/recursos/nuevo.png", e -> new VentanaDialogo(this).setVisible(true)));
        panelBotones.add(crearBoton("Baja Reservas", "/recursos/eliminar.png", e -> JOptionPane.showMessageDialog(this, "Función no implementada")));

        add(panelBotones, BorderLayout.SOUTH);
    }

    /**
     * Crea un botón con icono y acción asociada.
     *
     * @param texto       El texto del botón.
     * @param rutaImagen  La ruta del icono.
     * @param accion      El listener que define la acción del botón.
     * @return El botón creado.
     */
    private JButton crearBoton(String texto, String rutaImagen, ActionListener accion) {
        JButton boton = new JButton(texto, escalarImagen(rutaImagen, 50, 50));
        boton.setHorizontalTextPosition(SwingConstants.CENTER);
        boton.setVerticalTextPosition(SwingConstants.BOTTOM);
        boton.addActionListener(accion);
        return boton;
    }

    /**
     * Actualiza la imagen del hotel.
     *
     * @param ancho El ancho deseado de la imagen.
     * @param alto  La altura deseada de la imagen.
     */
    private void actualizarImagenHotel(int ancho, int alto) {
        imagenHotel.setIcon(escalarImagen("/recursos/hotel.png", ancho, alto - 20));
    }

    /**
     * Escala una imagen desde la ruta especificada.
     *
     * @param rutaImagen La ruta del archivo de imagen.
     * @param ancho      El ancho deseado de la imagen escalada.
     * @param alto       La altura deseada de la imagen escalada.
     * @return Un ImageIcon con la imagen escalada, o null si ocurre un error.
     */
    private ImageIcon escalarImagen(String rutaImagen, int ancho, int alto) {
        try {
            BufferedImage img = ImageIO.read(getClass().getResource(rutaImagen));
            double escala = Math.min((double) ancho / img.getWidth(), (double) alto / img.getHeight());
            Image imagenEscalada = img.getScaledInstance((int) (img.getWidth() * escala),
                    (int) (img.getHeight() * escala), Image.SCALE_SMOOTH);
            return new ImageIcon(imagenEscalada);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Configura la barra de menú de la ventana.
     */
    private void configurarMenu() {
        JMenuBar barraMenu = new JMenuBar();

        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem itemSalir = new JMenuItem("Salir");
        itemSalir.addActionListener(e -> System.exit(0));
        menuArchivo.add(itemSalir);

        JMenu menuRegistro = new JMenu("Registro");
        JMenuItem itemAltaReservas = new JMenuItem("Alta Reservas");
        itemAltaReservas.setAccelerator(KeyStroke.getKeyStroke("ctrl A"));
        itemAltaReservas.addActionListener(e -> new VentanaDialogo(this).setVisible(true));

        JMenuItem itemBajaReservas = new JMenuItem("Baja Reservas");
        itemBajaReservas.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
        itemBajaReservas.addActionListener(e -> JOptionPane.showMessageDialog(this, "Función no implementada"));

        menuRegistro.add(itemAltaReservas);
        menuRegistro.add(itemBajaReservas);

        JMenu menuAyuda = new JMenu("Ayuda");
        JMenuItem itemAcercaDe = new JMenuItem("Acerca de...");
        itemAcercaDe.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Gestión Hotel Versión 27.3.2.18\nEmpresa: Monstruos S.A\nGerente: Javier Palmero Clares\nFundado en 1883"));

        menuAyuda.add(itemAcercaDe);

        barraMenu.add(menuArchivo);
        barraMenu.add(menuRegistro);
        barraMenu.add(menuAyuda);

        setJMenuBar(barraMenu);
    }
}
