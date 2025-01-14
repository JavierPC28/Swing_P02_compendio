import java.awt.*;
import javax.swing.*;

/**
 * La clase PanelImagenes representa un panel que muestra imágenes de diferentes
 * tipos de habitaciones. Cada imagen está acompañada por un título que indica
 * el tipo de habitación.
 */
public class PanelImagenes extends JPanel {

	/**
	 * Constructor de la clase PanelImagenes. Inicializa el panel, establece el
	 * fondo, el borde, y agrega tres sub-paneles con imágenes de diferentes tipos
	 * de habitaciones.
	 */
	public PanelImagenes() {
		// Establecemos el fondo del panel como un color beige
		setBackground(new Color(255, 248, 220));
		// Se le da un borde con título
		setBorder(BorderFactory.createTitledBorder("Vista de Habitaciones"));
		// Utilizamos un GridLayout con una fila y tres columnas para colocar las
		// imágenes
		setLayout(new GridLayout(1, 3, 10, 10)); // 1 fila, 3 columnas, 10px de separación entre componentes

		// Añadimos tres paneles, uno para cada tipo de habitación, pasando la ruta de
		// la imagen y el título
		add(crearPanelImagen("/recursos/simple.png", "Simple"));
		add(crearPanelImagen("/recursos/doble.png", "Doble"));
		add(crearPanelImagen("/recursos/suite.png", "Suite"));
	}

	/**
	 * Método auxiliar para crear un panel con una imagen y un título. Este panel
	 * tiene la imagen centrada en la parte superior y el título debajo de ella.
	 *
	 * @param rutaImagen La ruta del archivo de la imagen.
	 * @param titulo     El título que se mostrará debajo de la imagen.
	 * @return El panel con la imagen y el título configurado.
	 */
	private JPanel crearPanelImagen(String rutaImagen, String titulo) {
		// Creamos un nuevo panel con BorderLayout
		JPanel panel = new JPanel(new BorderLayout());
		// Establecemos el fondo del panel como blanco
		panel.setBackground(Color.WHITE);

		// Etiqueta para mostrar la imagen
		JLabel etiquetaImagen = new JLabel();
		// Alineamos la imagen al centro del panel
		etiquetaImagen.setHorizontalAlignment(SwingConstants.CENTER);

		// Etiqueta para mostrar el título de la habitación debajo de la imagen
		JLabel etiquetaTitulo = new JLabel(titulo, SwingConstants.CENTER);
		// Establecemos la fuente del título para que esté en negrita y con un tamaño
		// adecuado
		etiquetaTitulo.setFont(new Font("Arial", Font.BOLD, 14));

		// Añadimos la etiqueta de la imagen al centro del panel y el título al sur
		// (debajo de la imagen)
		panel.add(etiquetaImagen, BorderLayout.CENTER);
		panel.add(etiquetaTitulo, BorderLayout.SOUTH);

		// Añadimos un listener al panel para que ajuste la imagen cuando el tamaño del
		// panel cambie
		panel.addComponentListener(new java.awt.event.ComponentAdapter() {
			@Override
			public void componentResized(java.awt.event.ComponentEvent e) {
				// Llamamos al método para ajustar la imagen al nuevo tamaño del panel
				// (considerando un margen para el título)
				ajustarImagen(etiquetaImagen, rutaImagen, panel.getWidth(), panel.getHeight() - 30);
			}
		});

		// Devolvemos el panel con la imagen y el título
		return panel;
	}

	/**
	 * Método para ajustar la imagen a las dimensiones del panel. Mantiene la imagen
	 * escalada de forma suave, ajustando el tamaño según el panel disponible.
	 *
	 * @param etiquetaImagen La etiqueta donde se mostrará la imagen.
	 * @param rutaImagen     La ruta de la imagen.
	 * @param ancho          El ancho disponible en el panel.
	 * @param alto           El alto disponible en el panel (restando espacio para
	 *                       el título).
	 */
	private void ajustarImagen(JLabel etiquetaImagen, String rutaImagen, int ancho, int alto) {
		// Cargamos la imagen desde los recursos
		ImageIcon icono = cargarImagen(rutaImagen);
		if (icono != null && ancho > 0 && alto > 0) {
			// Escalamos la imagen al tamaño del panel, manteniendo la calidad
			// (SCALE_SMOOTH)
			Image imagen = icono.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
			// Establecemos la imagen escalada en la etiqueta
			etiquetaImagen.setIcon(new ImageIcon(imagen));
		} else {
			// Si la imagen no se puede cargar, mostramos un mensaje de error
			etiquetaImagen.setText("Imagen no encontrada");
			etiquetaImagen.setIcon(null); // Limpiamos la imagen
		}
	}

	/**
	 * Método para cargar una imagen desde los recursos del proyecto.
	 *
	 * @param ruta La ruta de la imagen que queremos cargar.
	 * @return El ImageIcon con la imagen cargada.
	 */
	private ImageIcon cargarImagen(String ruta) {
		try {
			// Intentamos cargar la imagen desde los recursos con la ruta especificada
			return new ImageIcon(getClass().getResource(ruta));
		} catch (Exception e) {
			// Si ocurre un error al cargar la imagen, lo registramos en la consola
			System.err.println("Error al cargar la imagen: " + ruta);
			// Retornamos null si no se pudo cargar la imagen
			return null;
		}
	}
}
