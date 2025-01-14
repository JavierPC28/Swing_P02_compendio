import java.awt.Color;
import java.awt.Font;

import javax.swing.*;

/**
 * Clase PanelTitulo.
 * Representa el panel superior que contiene el título del hotel.
 * Este panel tiene un diseño sencillo con un título estilizado.
 */
public class PanelTitulo extends JPanel {

    /**
     * Constructor de la clase PanelTitulo.
     * Configura el fondo, el borde y añade una etiqueta centrada con el nombre del hotel.
     */
    public PanelTitulo() {
        // Establece el color de fondo del panel (azul claro)
        setBackground(new Color(173, 216, 230)); 
        
        // Configura un borde con título para el panel
        setBorder(BorderFactory.createTitledBorder("Hotel Continental"));

        // Crea una etiqueta con el texto "Hotel Continental" centrada
        JLabel label = new JLabel("Hotel Continental", SwingConstants.CENTER);
        
        // Configura la fuente del texto de la etiqueta: Arial, negrita, tamaño 24
        label.setFont(new Font("Arial", Font.BOLD, 24));
        
        // Establece el color del texto en gris oscuro
        label.setForeground(Color.DARK_GRAY);

        // Añade la etiqueta al panel
        add(label);
    }
}
