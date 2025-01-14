import java.awt.*;

import javax.swing.*;

/**
 * La clase VentanaDialogo representa una ventana de diálogo modal 
 * que permite gestionar reservas de un hotel. Incorpora diferentes
 * paneles para capturar datos del cliente, habitación, imágenes y
 * detalles adicionales.
 */
public class VentanaDialogo extends JDialog {
    
    /** Panel que contiene los datos del cliente. */
    private PanelDatosCliente panelCliente;
    
    /** Panel que contiene los datos de la habitación. */
    private PanelDatosHabitacion panelHabitacion;
    
    /** Panel para mostrar imágenes relacionadas con la reserva. */
    private PanelImagenes panelImagenes;
    
    /** Panel que recopila detalles adicionales de la reserva. */
    private PanelRecogerDatos panelDetalles;

    /**
     * Constructor de la clase VentanaDialogo.
     * Inicializa y configura los elementos gráficos de la ventana.
     *
     * @param owner la ventana principal que actúa como propietaria del diálogo.
     */
    public VentanaDialogo(JFrame owner) {
        super(owner, "Alta Reservas", true);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/recursos/anagrama.png")));
        
        // Configuración de la ventana
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize);
        setResizable(false);
        setLocation(0, 0);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel superior (Título)
        add(new PanelTitulo(), BorderLayout.NORTH);

        // Inicialización de paneles
        panelCliente = new PanelDatosCliente();
        panelImagenes = new PanelImagenes();
        panelHabitacion = new PanelDatosHabitacion(panelCliente, panelImagenes);
        panelDetalles = new PanelRecogerDatos();

        // Panel central con diseño en cuadrícula
        JPanel panelCentro = new JPanel(new GridLayout(2, 2, 5, 5));
        panelCentro.add(panelHabitacion);
        panelCentro.add(panelCliente);
        panelCentro.add(panelImagenes);
        panelCentro.add(panelDetalles);

        add(panelCentro, BorderLayout.CENTER);

        // Panel inferior (Botones)
        add(crearPanelBotones(), BorderLayout.SOUTH);
    }

    /**
     * Crea el panel que contiene los botones de acción de la ventana.
     *
     * @return el panel con los botones.
     */
    private JPanel crearPanelBotones() {
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        panelBotones.setBackground(new Color(200, 220, 240));

        // Creación de botones con iconos
        JButton btnImprimir = crearBotonConIcono("Imprimir a Documento", "imprimir.png");
        JButton btnNuevo = crearBotonConIcono("Nuevo", "nuevo.png");
        JButton btnGuardar = crearBotonConIcono("Guardar", "guardar.png");

        // Añadir acciones a los botones
        btnImprimir.addActionListener(e -> imprimirDocumento());
        btnNuevo.addActionListener(e -> resetearFormulario());
        btnGuardar.addActionListener(e -> guardarRegistro());

        // Añadir botones al panel
        panelBotones.add(btnImprimir);
        panelBotones.add(btnNuevo);
        panelBotones.add(btnGuardar);

        return panelBotones;
    }

    /**
     * Crea un botón con un texto y un icono personalizado.
     *
     * @param texto el texto que aparecerá en el botón.
     * @param nombreImagen el nombre del archivo de la imagen del icono.
     * @return un botón personalizado.
     */
    private JButton crearBotonConIcono(String texto, String nombreImagen) {
        JButton boton = new JButton(texto);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/recursos/" + nombreImagen));
        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(
            screenSize.width / 70, screenSize.height / 40, Image.SCALE_SMOOTH);
        boton.setIcon(new ImageIcon(imagenEscalada));
        boton.setHorizontalTextPosition(SwingConstants.RIGHT);
        boton.setPreferredSize(new Dimension(200, 50));
        boton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        return boton;
    }

    /**
     * Muestra los datos del cliente y la habitación en el panel de detalles.
     * Realiza una validación previa de los datos del cliente.
     */
    private void imprimirDocumento() {
        if (panelCliente.validarDatosCliente()) {
            String clienteInfo = panelCliente.obtenerDatos();
            String habitacionInfo = panelHabitacion.obtenerDatos();
            panelDetalles.mostrarDatosCliente(clienteInfo);
            panelDetalles.mostrarDatosHabitacion(habitacionInfo);
        }
    }

    /**
     * Restaura los valores por defecto de todos los campos y paneles del formulario.
     */
    private void resetearFormulario() {
        panelCliente.resetearCampos();
        panelHabitacion.reset();
        panelDetalles.reset();
    }

    /**
     * Guarda los datos de la reserva después de validar los datos del cliente.
     * Muestra un mensaje de éxito al usuario si todo es correcto.
     */
    private void guardarRegistro() {
        if (panelCliente.validarDatosCliente()) {
            JOptionPane.showMessageDialog(this, "Registro Guardado", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Muestra un mensaje de advertencia en un cuadro de diálogo.
     *
     * @param mensaje el mensaje que se desea mostrar.
     */
    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Aviso", JOptionPane.WARNING_MESSAGE);
    }
}
