import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;

/**
 * Clase que representa un panel para la captura de datos de un cliente.
 * Este panel incluye campos de texto para el nombre, apellidos, DNI, teléfono,
 * fechas de entrada y salida, así como validaciones específicas para los datos ingresados.
 */
public class PanelDatosCliente extends JPanel {

    /** Formato de fecha utilizado en los campos de fecha. */
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    /** Fecha actual utilizada como base para los campos de fecha. */
    private final Date fechaActual = new Date();

    // Campos de entrada de texto para los datos del cliente
    private JTextField tfNombre, tfApellidos, tfDNI, tfTelefono, tfDiasEstancia;

    // Etiquetas de error asociadas a validaciones específicas
    private JLabel lbErrorDNI, lbErrorTelefono;

    // Campos para la selección de fechas
    private JSpinner spFechaEntrada, spFechaSalida;

    /**
     * Constructor de la clase PanelDatosCliente.
     * Inicializa todos los componentes gráficos, incluyendo los campos de texto,
     * spinners de fecha, y las validaciones correspondientes.
     */
    public PanelDatosCliente() {
        // Configuración general del panel
        setBackground(new Color(245, 245, 245));
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Datos del Cliente",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.BOLD, 14)
        ));
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espaciado entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL; // Ajuste horizontal

        // Inicialización de los campos de entrada
        tfNombre = new JTextField(25);
        tfApellidos = new JTextField(25);
        tfDNI = crearCampoDNI(); // Campo de texto con validación específica para DNI
        lbErrorDNI = new JLabel(" "); // Espacio inicial para el mensaje de error
        tfTelefono = crearCampoTelefono(); // Campo de texto con validación específica para teléfono
        lbErrorTelefono = new JLabel(" "); // Espacio inicial para el mensaje de error
        tfDiasEstancia = new JTextField(10);
        tfDiasEstancia.setEditable(false); // El campo de días de estancia no es editable

        // Adición de los campos al panel
        agregarCampo("Nombre:", tfNombre, gbc, 0);
        agregarCampo("Apellidos:", tfApellidos, gbc, 1);
        agregarCampoConValidacion("DNI:", tfDNI, lbErrorDNI, gbc, 2);
        agregarCampoConValidacion("Teléfono:", tfTelefono, lbErrorTelefono, gbc, 3);

        // Configuración de los spinners para las fechas
        spFechaEntrada = configurarSpinnerFecha(fechaActual);
        spFechaSalida = configurarSpinnerFecha(sumarDias(fechaActual, 1));

        // Adición de eventos para actualización dinámica de fechas
        spFechaEntrada.addChangeListener(this::actualizarFechas);
        spFechaSalida.addChangeListener(this::actualizarFechas);

        // Adición de campos relacionados con fechas
        agregarCampo("Fecha Entrada:", spFechaEntrada, gbc, 4);
        agregarCampo("Fecha Salida:", spFechaSalida, gbc, 5);

        // Adición del campo para los días de estancia
        agregarCampo("Nº de días estancia:", tfDiasEstancia, gbc, 6);
        calcularDiasEstancia(); // Cálculo inicial de los días de estancia
    }

    /**
     * Método para añadir un campo simple (etiqueta + componente) al panel.
     * 
     * @param etiqueta Nombre del campo que se mostrará como etiqueta.
     * @param campo Componente de entrada asociado al campo (ej. JTextField, JSpinner).
     * @param gbc Configuraciones del GridBagLayout para el diseño.
     * @param fila Fila donde se ubicará el campo.
     */
    private void agregarCampo(String etiqueta, Component campo, GridBagConstraints gbc, int fila) {
        // Configuración de la etiqueta
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.weightx = 0.3;
        JLabel label = new JLabel(etiqueta);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        add(label, gbc);

        // Configuración del componente de entrada
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        gbc.gridwidth = 2;
        campo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        add(campo, gbc);
        gbc.gridwidth = 1; // Restablecimiento de la anchura
    }

    /**
     * Método para añadir un campo con validación (etiqueta, campo y mensaje de error).
     * 
     * @param etiqueta Nombre del campo que se mostrará como etiqueta.
     * @param campoTexto Campo de texto donde se ingresarán los datos.
     * @param etiquetaError Etiqueta que mostrará el mensaje de error si ocurre una validación fallida.
     * @param gbc Configuraciones del GridBagLayout para el diseño.
     * @param fila Fila donde se ubicará el campo.
     */
    private void agregarCampoConValidacion(String etiqueta, JTextField campoTexto, JLabel etiquetaError, GridBagConstraints gbc, int fila) {
        agregarCampo(etiqueta, campoTexto, gbc, fila); // Agregar campo básico

        // Configuración del mensaje de error
        gbc.gridx = 3;
        gbc.weightx = 0.2;
        etiquetaError.setForeground(Color.RED); // Color rojo para errores
        add(etiquetaError, gbc);
    }

    /**
     * Crear campo DNI.
     *
     * @return el campo de texto para el DNI
     */
    private JTextField crearCampoDNI() {
        JTextField campoDNI = new JTextField(9);
        campoDNI.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String textoActual = campoDNI.getText();
                char caracter = e.getKeyChar();

                // Asegura que el campo tenga 8 dígitos seguidos de una letra
                if (textoActual.length() < 8 && !Character.isDigit(caracter)
                        || textoActual.length() == 8 && !Character.isLetter(caracter) || textoActual.length() >= 9) {
                    e.consume();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // Convierte la letra a mayúsculas cuando se alcanza el límite
                if (campoDNI.getText().length() == 9) {
                    campoDNI.setText(
                            campoDNI.getText().substring(0, 8) + campoDNI.getText().substring(8).toUpperCase());
                }
                validarDNI();
            }
        });
        campoDNI.setFont(new Font("SansSerif", Font.PLAIN, 14));
        return campoDNI;
    }

    /**
     * Crear campo teléfono.
     *
     * @return el campo de texto para el teléfono
     */
    private JTextField crearCampoTelefono() {
        JTextField campoTelefono = new JTextField(9);
        campoTelefono.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();
                // Solo permite dígitos y limita el número de caracteres a 9
                if (!Character.isDigit(caracter) || campoTelefono.getText().length() >= 9) {
                    e.consume();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                validarTelefono();
            }
        });
        campoTelefono.setFont(new Font("SansSerif", Font.PLAIN, 14));
        return campoTelefono;
    }

    /**
     * Configurar spinner para la fecha.
     *
     * @param fechaInicial la fecha inicial para el spinner
     * @return el spinner configurado
     */
    private JSpinner configurarSpinnerFecha(Date fechaInicial) {
        JSpinner spinner = new JSpinner(new SpinnerDateModel(fechaInicial, null, null, Calendar.DAY_OF_MONTH));
        spinner.setEditor(new JSpinner.DateEditor(spinner, "dd/MM/yyyy"));
        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setEditable(false);
        spinner.setFont(new Font("SansSerif", Font.PLAIN, 14));
        return spinner;
    }

    /**
     * Actualizar fechas de entrada y salida.
     *
     * @param e el evento del cambio en el spinner
     */
    private void actualizarFechas(ChangeEvent e) {
        Date fechaEntrada = (Date) spFechaEntrada.getValue();
        Date fechaSalida = (Date) spFechaSalida.getValue();

        if (e.getSource() == spFechaEntrada) {
            if (fechaEntrada.before(fechaActual)) {
                spFechaEntrada.setValue(fechaActual);
            }
            if (!fechaEntrada.before(fechaSalida)) {
                ajustarFechaSalida(fechaEntrada);
            }
        } else if (e.getSource() == spFechaSalida) {
            if (!fechaSalida.after(fechaEntrada)) {
                ajustarFechaSalida(fechaEntrada);
            }
        }
        calcularDiasEstancia();
    }

    /**
     * Ajustar la fecha de salida, sumando un día a la fecha de entrada.
     *
     * @param fechaEntrada la fecha de entrada
     */
    private void ajustarFechaSalida(Date fechaEntrada) {
        spFechaSalida.setValue(sumarDias(fechaEntrada, 1));
    }

    /**
     * Calcular los días de estancia entre las fechas de entrada y salida.
     */
    private void calcularDiasEstancia() {
        Date fechaEntrada = (Date) spFechaEntrada.getValue();
        Date fechaSalida = (Date) spFechaSalida.getValue();
        tfDiasEstancia
                .setText(String.valueOf((fechaSalida.getTime() - fechaEntrada.getTime()) / (1000 * 60 * 60 * 24)));
        tfDiasEstancia.setFont(new Font("SansSerif", Font.PLAIN, 14));
    }

    /**
     * Obtener los días de estancia.
     *
     * @return los días de estancia en formato texto
     */
    public String getDiasEstancia() {
        return tfDiasEstancia.getText();
    }

    /**
     * Obtener el nombre del cliente.
     *
     * @return el nombre del cliente
     */
    public String getNombre() {
        return tfNombre.getText().trim();
    }

    /**
     * Obtener los apellidos del cliente.
     *
     * @return los apellidos del cliente
     */
    public String getApellidos() {
        return tfApellidos.getText().trim();
    }

    /**
     * Obtener el DNI del cliente.
     *
     * @return el DNI del cliente
     */
    public String getDNI() {
        return tfDNI.getText().trim();
    }

    /**
     * Obtener el teléfono del cliente.
     *
     * @return el teléfono del cliente
     */
    public String getTelefono() {
        return tfTelefono.getText().trim();
    }

    /**
     * Validar el nombre del cliente.
     *
     * @return verdadero si el nombre es válido
     */
    public boolean validarNombre() {
        return !getNombre().isEmpty();
    }

    /**
     * Validar los apellidos del cliente.
     *
     * @return verdadero si los apellidos son válidos
     */
    public boolean validarApellidos() {
        return !getApellidos().isEmpty();
    }

    /**
     * Validar el DNI del cliente.
     *
     * @return verdadero si el DNI es válido
     */
    private boolean validarDNI() {
        String dni = tfDNI.getText();
        if (!dni.matches("\\d{8}[A-Za-z]") || !"TRWAGMYFPDXBNJZSQVHLCKE"
                .substring(Integer.parseInt(dni.substring(0, 8)) % 23, Integer.parseInt(dni.substring(0, 8)) % 23 + 1)
                .equalsIgnoreCase(dni.substring(8))) {
            lbErrorDNI.setText(dni.matches("\\d{8}[A-Za-z]") ? "Letra incorrecta" : "Formato incorrecto");
            return false;
        }
        lbErrorDNI.setText(" ");
        return true;
    }

    /**
     * Validar el teléfono del cliente.
     *
     * @return verdadero si el teléfono es válido
     */
    private boolean validarTelefono() {
        lbErrorTelefono.setText(tfTelefono.getText().matches("\\d{9}") ? " " : "Formato incorrecto");
        return tfTelefono.getText().matches("\\d{9}");
    }

    /**
     * Sumar días a una fecha.
     *
     * @param fecha la fecha base
     * @param dias los días a sumar
     * @return la nueva fecha
     */
    private Date sumarDias(Date fecha, int dias) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        cal.add(Calendar.DAY_OF_YEAR, dias);
        return cal.getTime();
    }

    /**
     * Validar todos los datos del cliente.
     *
     * @return verdadero si todos los datos son válidos
     */
    public boolean validarDatosCliente() {
        boolean nombreValido = validarNombre();
        boolean apellidosValido = validarApellidos();
        boolean dniValido = validarDNI();
        boolean telefonoValido = validarTelefono();

        if (!nombreValido) {
            JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío", "Error", JOptionPane.WARNING_MESSAGE);
        } else if (!apellidosValido) {
            JOptionPane.showMessageDialog(this, "El apellido no puede estar vacío", "Error", JOptionPane.WARNING_MESSAGE);
        } else if (!dniValido) {
            JOptionPane.showMessageDialog(this, "El DNI no es válido", "Error", JOptionPane.WARNING_MESSAGE);
        } else if (!telefonoValido) {
            JOptionPane.showMessageDialog(this, "El teléfono no es válido", "Error", JOptionPane.WARNING_MESSAGE);
        }

        return nombreValido && apellidosValido && dniValido && telefonoValido;
    }

    /**
     * Obtener los datos completos del cliente.
     *
     * @return los datos del cliente en formato texto
     */
    public String obtenerDatos() {
        return String.format("Nombre: %s\nApellidos: %s\nDNI: %s\nTeléfono: %s\nDías de estancia: %s",
                tfNombre.getText(), tfApellidos.getText(), tfDNI.getText(), tfTelefono.getText(), getDiasEstancia());
    }

    /**
     * Resetear los campos del formulario.
     */
    public void resetearCampos() {
        tfNombre.setText("");
        tfApellidos.setText("");
        tfDNI.setText("");
        tfTelefono.setText("");
        lbErrorDNI.setText(" ");
        lbErrorTelefono.setText(" ");
        spFechaEntrada.setValue(fechaActual);
        spFechaSalida.setValue(sumarDias(fechaActual, 1));
        tfDiasEstancia.setText("");
        tfNombre.requestFocusInWindow();
    }
}