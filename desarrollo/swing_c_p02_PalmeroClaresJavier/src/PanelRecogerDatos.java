import java.awt.BorderLayout;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.Font;

/**
 * La clase PanelRecogerDatos es un panel que permite mostrar y gestionar los datos del cliente y de la habitación.
 * Utiliza pestañas para mostrar los datos del cliente y de la habitación por separado.
 */
public class PanelRecogerDatos extends JPanel {
	
	/** Etiqueta para mostrar los datos del cliente. */
	private JLabel labelDatosCliente;
	
	/** Etiqueta para mostrar los datos de la habitación. */
	private JLabel labelDatosHabitacion;

	/**
	 * Constructor de la clase PanelRecogerDatos.
	 * Inicializa el panel, crea el contenedor de pestañas y los paneles para mostrar los datos del cliente y la habitación.
	 */
	public PanelRecogerDatos() {
		// Creamos un borde con un título para el panel
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(java.awt.Color.GRAY), "Detalles",
				TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
				new Font("SansSerif", Font.BOLD, 14)));

		// Crear el contenedor de pestañas donde se mostrarán los datos
		JTabbedPane tabbedPane = new JTabbedPane();

		// Panel para los datos del cliente
		JPanel clientePanel = new JPanel();
		// Inicializamos la etiqueta para los datos del cliente
		labelDatosCliente = new JLabel("Datos del Cliente");
		// Establecemos la alineación vertical del texto para que se alinee en la parte superior
		labelDatosCliente.setVerticalAlignment(SwingConstants.TOP);
		// Establecemos un layout de BorderLayout para el panel
		clientePanel.setLayout(new BorderLayout());
		// Añadimos la etiqueta con los datos del cliente al panel en la parte superior (NORTH)
		clientePanel.add(labelDatosCliente, BorderLayout.NORTH);

		// Panel para los datos de la habitación
		JPanel habitacionPanel = new JPanel();
		// Inicializamos la etiqueta para los datos de la habitación
		labelDatosHabitacion = new JLabel("Datos de la Habitación");
		// Establecemos la alineación vertical del texto para que se alinee en la parte superior
		labelDatosHabitacion.setVerticalAlignment(SwingConstants.TOP);
		// Establecemos un layout de BorderLayout para el panel
		habitacionPanel.setLayout(new BorderLayout());
		// Añadimos la etiqueta con los datos de la habitación al panel en la parte superior (NORTH)
		habitacionPanel.add(labelDatosHabitacion, BorderLayout.NORTH);

		// Agregamos las pestañas al contenedor, asociando el panel con los datos del cliente y el panel con los datos de la habitación
		tabbedPane.addTab("Cliente", clientePanel);
		tabbedPane.addTab("Habitación", habitacionPanel);

		// Establecemos un layout de BorderLayout para el panel principal
		setLayout(new BorderLayout());
		// Añadimos el contenedor de pestañas al panel principal en la zona central (CENTER)
		add(tabbedPane, BorderLayout.CENTER);
	}

	/**
	 * Muestra los datos del cliente en el panel correspondiente.
	 * Se utiliza HTML para dar formato a los datos, permitiendo saltos de línea.
	 *
	 * @param datos Información del cliente en formato String.
	 */
	public void mostrarDatosCliente(String datos) {
		// Convertimos los saltos de línea en el texto a etiquetas HTML <br> para que se muestren correctamente
		labelDatosCliente.setText("<html>" + datos.replace("\n", "<br>") + "</html>");
	}

	/**
	 * Muestra los datos de la habitación en el panel correspondiente.
	 * Se utiliza HTML para dar formato a los datos, permitiendo saltos de línea.
	 *
	 * @param datos Información de la habitación en formato String.
	 */
	public void mostrarDatosHabitacion(String datos) {
		// Convertimos los saltos de línea en el texto a etiquetas HTML <br> para que se muestren correctamente
		labelDatosHabitacion.setText("<html>" + datos.replace("\n", "<br>") + "</html>");
	}

	/**
	 * Resetea el contenido de ambos paneles, restaurando los textos predeterminados.
	 * Este método se puede utilizar para limpiar los datos mostrados en los paneles.
	 */
	public void reset() {
		// Restauramos los textos predeterminados de los paneles
		labelDatosCliente.setText("Datos del Cliente");
		labelDatosHabitacion.setText("Datos de la Habitación");
	}
}
