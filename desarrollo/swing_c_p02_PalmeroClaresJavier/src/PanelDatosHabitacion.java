import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * La clase PanelDatosHabitacion representa un panel para mostrar y gestionar los datos relacionados con una habitación.
 * Permite seleccionar el tipo de habitación, el número de habitaciones, indicar si hay niños, definir los extras para niños
 * y calcular el importe total según la selección.
 */
public class PanelDatosHabitacion extends JPanel {
	
	/** El combo box para seleccionar el tipo de habitación. */
	private JComboBox<String> tipoHabitacion;
	
	/** El spinner para seleccionar el número de habitaciones. */
	private JSpinner numHabitaciones;
	
	/** El check box para indicar si hay niños. */
	private JCheckBox checkNiños;
	
	/** El panel para mostrar los extras para niños, que inicialmente está oculto. */
	private JPanel panelExtrasNiños;
	
	/** El spinner para seleccionar la edad de los niños. */
	private JSpinner edadNiñosSpinner;
	
	/** El campo de texto para mostrar los extras de la habitación para niños (como una cuna o cama supletoria). */
	private JTextField extrasTextField;
	
	/** La etiqueta para mostrar el importe calculado de la habitación. */
	private JLabel importeLabel;
	
	/** El panel de datos del cliente, utilizado para obtener información como los días de estancia. */
	private PanelDatosCliente panelCliente;

	/**
	 * Constructor de PanelDatosHabitacion.
	 * Inicializa los componentes de la interfaz de usuario y establece la disposición y eventos.
	 *
	 * @param panelCliente El panel de datos del cliente.
	 * @param panelImagenes El panel de imágenes, aunque no se utiliza en este panel.
	 */
	public PanelDatosHabitacion(PanelDatosCliente panelCliente, PanelImagenes panelImagenes) {
		// Se asigna el panel de cliente para utilizar datos del cliente en los cálculos
		this.panelCliente = panelCliente;

		// Establecemos el fondo y el borde con título del panel
		setBackground(new Color(230, 230, 250));  // Color lavanda claro
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED),
				"Datos de la Habitación"));  // Título del panel
		setLayout(new GridBagLayout());  // Utilizamos GridBagLayout para una distribución flexible
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);  // Añadimos márgenes entre los componentes
		gbc.fill = GridBagConstraints.HORIZONTAL;  // Los componentes ocuparán todo el ancho disponible
		gbc.weightx = 1.0;  // Los componentes ocuparán un porcentaje de la fila según su peso
		gbc.weighty = 1.0;  // Los componentes ocuparán un porcentaje de la columna según su peso

		// Configuración de la etiqueta para el tipo de habitación
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		add(createLabel("Tipo de Habitación:"), gbc);

		// Configuración del combo box para seleccionar el tipo de habitación
		gbc.gridx = 1;
		gbc.gridy = 0;
		tipoHabitacion = new JComboBox<>(new String[] { "Simple", "Doble", "Suite" });  // Opciones de tipo de habitación
		tipoHabitacion.setFont(tipoHabitacion.getFont().deriveFont(Font.PLAIN, 16));  // Estilo de fuente
		add(tipoHabitacion, gbc);

		// Configuración de la etiqueta para el número de habitaciones
		gbc.gridx = 0;
		gbc.gridy = 1;
		add(createLabel("Nº de Habitaciones:"), gbc);

		// Configuración del spinner para seleccionar el número de habitaciones
		gbc.gridx = 1;
		gbc.gridy = 1;
		numHabitaciones = new JSpinner(new SpinnerNumberModel(1, 0, 50, 1));  // Límite de habitaciones entre 0 y 50
		numHabitaciones.setFont(numHabitaciones.getFont().deriveFont(Font.PLAIN, 16));  // Estilo de fuente
		add(numHabitaciones, gbc);

		// Configuración de la etiqueta para saber si hay niños
		gbc.gridx = 0;
		gbc.gridy = 2;
		add(createLabel("¿Niños?"), gbc);

		// Configuración del check box para indicar si hay niños
		gbc.gridx = 1;
		gbc.gridy = 2;
		checkNiños = new JCheckBox();  // Check box para indicar si hay niños
		checkNiños.setFont(checkNiños.getFont().deriveFont(Font.PLAIN, 16));  // Estilo de fuente
		add(checkNiños, gbc);

		// Panel para los extras de niños, inicialmente oculto
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		panelExtrasNiños = new JPanel(new GridBagLayout());
		panelExtrasNiños.setBorder(new TitledBorder("Extras para Niños"));  // Borde con título
		panelExtrasNiños.setBackground(new Color(255, 228, 225));  // Fondo rosa claro
		panelExtrasNiños.setVisible(false);  // El panel de extras para niños comienza oculto
		add(panelExtrasNiños, gbc);

		GridBagConstraints gbcExtras = new GridBagConstraints();
		gbcExtras.insets = new Insets(5, 5, 5, 5);  // Márgenes para los componentes dentro del panel de extras
		gbcExtras.fill = GridBagConstraints.HORIZONTAL;
		gbcExtras.weightx = 1.0;  // Los componentes dentro de este panel ocupan el ancho disponible

		// Configuración del spinner para la edad de los niños
		gbcExtras.gridx = 0;
		gbcExtras.gridy = 0;
		panelExtrasNiños.add(createLabel("Edad de niños:"), gbcExtras);

		gbcExtras.gridx = 1;
		gbcExtras.gridy = 0;
		edadNiñosSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 14, 1));  // Edad entre 0 y 14 años
		edadNiñosSpinner.setFont(edadNiñosSpinner.getFont().deriveFont(Font.PLAIN, 16));  // Estilo de fuente
		panelExtrasNiños.add(edadNiñosSpinner, gbcExtras);

		// Campo de texto para mostrar los extras relacionados con la edad del niño
		gbcExtras.gridx = 0;
		gbcExtras.gridy = 1;
		panelExtrasNiños.add(createLabel("Extras:"), gbcExtras);

		gbcExtras.gridx = 1;
		gbcExtras.gridy = 1;
		extrasTextField = new JTextField("Cuna");  // Campo de texto inicializado con "Cuna"
		extrasTextField.setFont(extrasTextField.getFont().deriveFont(Font.PLAIN, 16));  // Estilo de fuente
		extrasTextField.setEditable(false);  // No es editable por el usuario
		panelExtrasNiños.add(extrasTextField, gbcExtras);

		// Configuración de la etiqueta para mostrar el importe de la habitación
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 1;
		add(createLabel("Importe habitación:"), gbc);

		// Etiqueta para mostrar el importe total
		gbc.gridx = 1;
		gbc.gridy = 4;
		importeLabel = new JLabel("0 €");  // El importe inicial es 0 €
		importeLabel.setFont(importeLabel.getFont().deriveFont(Font.PLAIN, 16));  // Estilo de fuente
		add(importeLabel, gbc);

		// Eventos que actualizan la visibilidad del panel de extras para niños y el cálculo del importe
		checkNiños.addActionListener(e -> panelExtrasNiños.setVisible(checkNiños.isSelected()));  // Si se marca, se muestra el panel de extras
		edadNiñosSpinner.addChangeListener(e -> actualizarExtras());  // Si cambia la edad, se actualizan los extras
		tipoHabitacion.addActionListener(e -> calcularImporte());  // Si cambia el tipo de habitación, se recalcula el importe
		numHabitaciones.addChangeListener(e -> calcularImporte());  // Si cambia el número de habitaciones, se recalcula el importe
		checkNiños.addActionListener(e -> calcularImporte());  // Si se marca o desmarca el check, se recalcula el importe

		calcularImporte();  // Calculamos el importe al inicio
	}

	/**
	 * Método auxiliar para crear etiquetas con un estilo de fuente común.
	 *
	 * @param text El texto que aparecerá en la etiqueta.
	 * @return La etiqueta con el texto proporcionado.
	 */
	private JLabel createLabel(String text) {
		JLabel label = new JLabel(text);
		label.setFont(label.getFont().deriveFont(Font.PLAIN, 16));  // Establecemos el estilo de la fuente
		return label;
	}

	/**
	 * Método que actualiza los extras en función de la edad del niño.
	 * Dependiendo de la edad, se cambiarán los extras a "Cuna", "Cama supletoria pequeña" o "Cama supletoria normal".
	 */
	private void actualizarExtras() {
		int edad = (int) edadNiñosSpinner.getValue();  // Obtenemos la edad del niño
		if (edad >= 0 && edad <= 3) {
			extrasTextField.setText("Cuna");  // Si tiene entre 0 y 3 años, el extra es una cuna
		} else if (edad >= 4 && edad <= 10) {
			extrasTextField.setText("Cama supletoria pequeña");  // Si tiene entre 4 y 10 años, cama supletoria pequeña
		} else if (edad >= 11 && edad <= 14) {
			extrasTextField.setText("Cama supletoria normal");  // Si tiene entre 11 y 14 años, cama supletoria normal
		}
		calcularImporte();  // Recalculamos el importe después de actualizar los extras
	}

	/**
	 * Método que calcula el importe total de la habitación en función de los datos seleccionados.
	 */
	private void calcularImporte() {
		int diasEstancia;
		try {
			// Intentamos obtener los días de estancia del panel de datos del cliente
			diasEstancia = Integer.parseInt(panelCliente.getDiasEstancia());  
		} catch (NumberFormatException e) {
			diasEstancia = 0;  // Si no se puede obtener, asumimos 0 días de estancia
		}

		// Determinamos el precio base en función del tipo de habitación
		String tipo = (String) tipoHabitacion.getSelectedItem();
		int precioBase;
		switch (tipo) {
		case "Simple":
			precioBase = 50;  // Precio base para habitación simple
			break;
		case "Doble":
			precioBase = 75;  // Precio base para habitación doble
			break;
		case "Suite":
			precioBase = 125;  // Precio base para suite
			break;
		default:
			precioBase = 0;  // Si no se selecciona un tipo, el precio es 0
		}

		// Obtenemos el número de habitaciones
		int numHab = (int) numHabitaciones.getValue();
		int extraCoste = 0;  // Inicializamos el coste extra

		// Si hay niños, sumamos un coste adicional
		if (checkNiños.isSelected()) {
			int edad = (int) edadNiñosSpinner.getValue();
			if (edad >= 0 && edad <= 14) {
				extraCoste = 20;  // Coste extra si hay niños
			}
		}

		// Calculamos el total: (precio base + coste extra) * número de habitaciones * días de estancia
		int total = (precioBase + extraCoste) * numHab * diasEstancia;
		importeLabel.setText(total + " €");  // Actualizamos la etiqueta con el total calculado
	}
	
	/**
	 * Método que obtiene los datos seleccionados por el usuario en un formato de texto.
	 *
	 * @return Una cadena con los datos de la habitación seleccionada.
	 */
	public String obtenerDatos() {
	    String tipo = (String) tipoHabitacion.getSelectedItem();
	    int numHab = (int) numHabitaciones.getValue();
	    String extras = checkNiños.isSelected() ? extrasTextField.getText() : "Ninguno";
	    return String.format("Tipo: %s\nHabitaciones: %d\nExtras: %s\nImporte: %s", tipo, numHab, extras, importeLabel.getText());
	}

	/**
	 * Método para resetear el panel a sus valores iniciales.
	 */
	public void reset() {
		tipoHabitacion.setSelectedIndex(0);  // Restablecer la selección de tipo a "Simple"
		numHabitaciones.setValue(1);  // Restablecer el número de habitaciones a 1
		checkNiños.setSelected(false);  // Desmarcar el check de niños
		panelExtrasNiños.setVisible(false);  // Ocultar el panel de extras para niños
		edadNiñosSpinner.setValue(0);  // Restablecer la edad del niño a 0
		extrasTextField.setText("");  // Limpiar el campo de texto de extras
		importeLabel.setText("0 €");  // Restablecer el importe a 0 €
	}
}
