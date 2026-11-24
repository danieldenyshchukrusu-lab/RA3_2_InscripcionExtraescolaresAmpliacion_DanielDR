package com.mycompany.ra3_inscripcionextraescolaresampliacion_danieldr;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

/**
 * 
 * El controlador de la práctica RA_3_2.
 * 
 * @author Daniel Denyshchuk Rusu
 * @version 1.0
 */

public class PrimaryController {

    //NO SE SI HAY QUE METER UN TEXT-INPUT-DIALOG.
    
    @FXML
    private TextField NombreAlumno;
    
    @FXML
    private ComboBox<String> ComboboxCurso = new ComboBox<>();
    
    @FXML
    private ListView<Inscripcion> ListaInscritos;
    
    @FXML
    private ObservableList<Inscripcion> Inscritos = FXCollections.observableArrayList();
    
    @FXML
    private ScrollPane PanelScroll = new ScrollPane();
    
    @FXML
    private RadioButton Robotica = new RadioButton("Robótica");
    
    @FXML
    private RadioButton Ajedrez = new RadioButton("Ajedrez");
    
    @FXML
    private RadioButton Pintura = new RadioButton("Pintura");
    
    @FXML
    private RadioButton Idiomas = new RadioButton("Idiomas");
    
    @FXML
    private RadioButton LunesMiercoles = new RadioButton("Lun-Mié 16:00");
    
    @FXML
    private RadioButton MartesJueves = new RadioButton("Mar-Jue 16:00");
    
    @FXML
    private RadioButton Viernes = new RadioButton("Viernes 16:00");
    
    @FXML
    private RadioButton Sabado = new RadioButton("Sabado 16:00");
    
    @FXML
    private RadioButton Iniciacion = new RadioButton("Iniciación");
    
    @FXML
    private RadioButton Intermedio = new RadioButton("Intermedio");
    
    @FXML
    private RadioButton Avanzado = new RadioButton("Avanzado");
    
    @FXML
    private RadioButton Mensual = new RadioButton("Mensual");
    
    @FXML
    private RadioButton Trimestral = new RadioButton("Trimestral");
    
    @FXML
    private RadioButton Anual = new RadioButton("Anual");
    
    @FXML
    private CheckBox Comedor = new CheckBox("Comedor");
    
    @FXML
    private CheckBox Transporte = new CheckBox("Transporte");
    
    @FXML
    private CheckBox SeguroEscolar = new CheckBox("Seguro Escolar");
    
    @FXML
    private CheckBox MaterialIncluido = new CheckBox("Material Incluido");
    
    @FXML
    private Slider Deslizador = new Slider(1,10,0);
    
    @FXML 
    private Button Añadir;
    
    @FXML 
    private Button Buscar;
    
    @FXML 
    private Button Eliminar;
    
    @FXML 
    private Button Renombrar;
    
    @FXML 
    private Button Cancelar;
    
    @FXML 
    private Button Salir;
    
    @FXML
    private Label HorasBind = new Label();
    
    @FXML
    private Label Curso = new Label();
    
    @FXML
    private Alert alertaInfo = new Alert(Alert.AlertType.INFORMATION);
    
    @FXML
    private Alert alertaWarning = new Alert(Alert.AlertType.WARNING);
    
    @FXML
    private Alert alertaConfirm = new Alert(Alert.AlertType.CONFIRMATION);
    
    @FXML
    private Alert alertaErr = new Alert(Alert.AlertType.ERROR);
    
    @FXML 
    private ToggleGroup Actividades;
    
    @FXML 
    private ToggleGroup Turnos;
    
    @FXML 
    private ToggleGroup Niveles;
    
    @FXML 
    private ToggleGroup Pagos;
    
    //FICHERO
    private static final String FICHERO = "inscritos.txt";
    
    int contador = 0;
    
    //Esto lo añadi ayudandome mas con la IA porque no sabia como hacer que a la hora de seleccionar algo de la lista, quedase desactivado el boton añadir y buscar.
    private BooleanProperty nombreIntroducido = new SimpleBooleanProperty(false);
    
    //Esto lo hago para que en el initialize a la hora de reactivar varias veces el programa, no añada los ITEMS de la ESO siempre.
    
    /**
     * Inicializa el formulario de inscripciones. JavaFX lo ejecuta una sola vez,
     * automáticamente, justo después de cargar el FXML.
     * <p>
     * Deja la interfaz en su estado inicial y configura:
     * <ul>
     *   <li>El estado desactivado del combo de curso y del panel de configuración.</li>
     *   <li>Los bindings de los botones: Añadir y Buscar se desactivan si no se ha
     *       pulsado Intro en el nombre o si hay algo seleccionado en la lista;
     *       Eliminar y Renombrar solo se activan con una selección.</li>
     *   <li>Los cursos disponibles del combo (una sola vez, controlado con
     *       {@code contador}).</li>
     *   <li>Un listener que muestra en el formulario la inscripción seleccionada.</li>
     *   <li>Un listener que limita el nombre a 15 caracteres.</li>
     *   <li>El binding entre el slider de horas y su etiqueta, y la configuración
     *       del propio slider (de 1 a 10, con marcas y saltos de uno en uno).</li>
     *   <li>El enlace del {@code ListView} con la lista {@code Inscritos}.</li>
     * </ul>
     * No recibe parámetros, no devuelve nada y no lanza excepciones comprobadas,
     */
    @FXML
    public void initialize() {
        
        ComboboxCurso.setDisable(true);
        PanelScroll.setDisable(true);
        
        //Inicializamos binding de añadir y buscar tambien, para que se desactiven al seleccionar algo de la lsita, pero que sirvan igual que como tenia antes, que era que cuando pulsaba intro en campoNombre, estos se activan.
        Añadir.disableProperty().bind(nombreIntroducido.not().or(ListaInscritos.getSelectionModel().selectedItemProperty().isNotNull()));
        Buscar.disableProperty().bind(nombreIntroducido.not().or(ListaInscritos.getSelectionModel().selectedItemProperty().isNotNull()));
        
        //Inicializamos binding de Eliminar y Renombrar, se activaran cuando tengamos seleccionado algo de la lista.
        Eliminar.disableProperty().bind(ListaInscritos.getSelectionModel().selectedItemProperty().isNull());
        Renombrar.disableProperty().bind(ListaInscritos.getSelectionModel().selectedItemProperty().isNull());
        Curso.setDisable(true);
        
        //Config inicial de combobox
        if (contador==0) {
            ComboboxCurso.getItems().addAll("1ºESO","2ºESO","3ºESO","4ºESO");
            contador++;
        }
        
        //Un listener para mostrar la configuración y nombre de lo que este seleccionado.
        ListaInscritos.getSelectionModel().selectedItemProperty().addListener(
                (observable, antiguo, nuevo) -> {
                    if (nuevo != null) {
                        ComboboxCurso.setDisable(false);
                        PanelScroll.setDisable(false);
                        Curso.setDisable(false);
                        mostrar(nuevo);
                    }
                }
        );
        
        //El listener para lo del max 15 caracteres. //NO LO HEMOS DADO PERO APRENDETELO.
        NombreAlumno.textProperty().addListener(
                (obs, antiguo, nuevo) -> {
                    if (nuevo.length() > 15) {
                        NombreAlumno.setText(antiguo);
                    }
        });
        
        //Binding de slider con label
        HorasBind.textProperty().bind(
            Deslizador.valueProperty().asString("%.0fh")
        );
        
        // configuración del slider (Lo hago aqui porque en el ScenBuilder no funciona el SnapTicks)
        Deslizador.setMin(1);
        Deslizador.setMax(10);
        Deslizador.setMajorTickUnit(1);
        Deslizador.setMinorTickCount(0);
        Deslizador.setSnapToTicks(true);
        Deslizador.setShowTickMarks(true);
        Deslizador.setShowTickLabels(true);
        
        ListaInscritos.setItems(Inscritos);
        
    }
    
    /**
     * 
     * Al introducir un nombre dentro del TextField NombreAlumno, este desactiva las Deshabilitaciones
     * y llama al metodo CONFIGURACION-ESTANDAR. Si no, vuelve al estado inicial del programa por metodo y suelta un AlertWarning.
     * 
     */
    @FXML 
    public void alPulsar() {
        if(!NombreAlumno.getText().isBlank()||!NombreAlumno.getText().isEmpty()) {  
            ComboboxCurso.setDisable(false);
            PanelScroll.setDisable(false);
            Curso.setDisable(false);
                
            //Ahora como hemos hecho el binding de los 4 botones mas arriba, si transformamos "nombreIntroducido" a true, se activaran los botones añadir y buscar.
            nombreIntroducido.set(true);
            
            ComboboxCurso.getSelectionModel().selectFirst();
            ComboboxCurso.requestFocus();
                
            ListaInscritos.getSelectionModel().clearSelection(); //Ponemos esto tambien para que me quite la seleccion de la lista y pueda utilizar el boton de añadir y buscar, porque al tener seleccionado algo
                                                                 //de la lista, me los desactiva.
            
            configuracionEstandar();
        } else {
            estadoInicialPrograma();
            alertaWarning.setHeaderText("NOMBRE VACIO");
            alertaWarning.setContentText("No debe de dejar el texto vacio");
            alertaWarning.showAndWait();
        }
    }
    
    /**
     * Esta es la configuracion estandar en la que debe de estar el panel de configuración.
     */
    @FXML
    public void configuracionEstandar() {
        
        Robotica.setSelected(true);
        LunesMiercoles.setSelected(true);
        Iniciacion.setSelected(true);
        Mensual.setSelected(true);
        
        SeguroEscolar.setSelected(true);
        Comedor.setSelected(false);
        Transporte.setSelected(false);
        MaterialIncluido.setSelected(true);
        
        Deslizador.setValue(2);
    }
    
    /**
     * Esta configuración quita todas las selecciones y pone de valor el Deslizador en 1.
     */
    @FXML
    public void configuracionEstandarFALSE() {
        Robotica.setSelected(false);
        LunesMiercoles.setSelected(false);
        Iniciacion.setSelected(false);
        Mensual.setSelected(false);
        
        SeguroEscolar.setSelected(false);
        Comedor.setSelected(false);
        Transporte.setSelected(false);
        MaterialIncluido.setSelected(false);
        
        Deslizador.setValue(1);
    }
    
    /**
     * Lo utilizamos en (AL-AÑADIR, AL_BUSCAR, AL-ELIMINAR...) entre otros.
     * Nos devuelve el estado inicial del programa con todo limpio + deshabilitados algunos paneles y botones.
     */
    @FXML
    public void estadoInicialPrograma() {
        NombreAlumno.clear();
        ComboboxCurso.getSelectionModel().selectFirst();
        ComboboxCurso.setDisable(true);
        PanelScroll.setDisable(true);
        Curso.setDisable(true);

        nombreIntroducido.set(false);
        ListaInscritos.getSelectionModel().clearSelection();

        configuracionEstandarFALSE();   // deselecciona grupos, checkboxes y slider a 1
        NombreAlumno.requestFocus();
    }
    
    /**
     * Para añadir una nueva Inscripción dentro de nuestro listado de Inscripciones.
     * Ya una vez añadido, volveremos al estado inicial del programa.
     */
    @FXML 
    public void AlAñadir() {
        
        String nombre = NombreAlumno.getText();
        String curso = ComboboxCurso.getValue();
        
        RadioButton actividadRadio = (RadioButton) Actividades.getSelectedToggle();
        String actividad = actividadRadio.getText();
        
        RadioButton turnoRadio = (RadioButton) Turnos.getSelectedToggle();
        String turno = turnoRadio.getText();
        
        RadioButton nivelRadio = (RadioButton) Niveles.getSelectedToggle();
        String nivel = nivelRadio.getText();
        
        RadioButton pagoRadio = (RadioButton) Pagos.getSelectedToggle();
        String pago = pagoRadio.getText();
        
        int horas = (int) Deslizador.getValue();
        
        List<String> opcionesMarcadas = new ArrayList();
        if (SeguroEscolar.isSelected()) {
            opcionesMarcadas.add(SeguroEscolar.getText());
        }
        if (Comedor.isSelected()) {
            opcionesMarcadas.add(Comedor.getText());
        }
        if (Transporte.isSelected()) {
            opcionesMarcadas.add(Transporte.getText());
        }
        if (MaterialIncluido.isSelected()) {
            opcionesMarcadas.add(MaterialIncluido.getText());
        }
        
        Inscripcion inscripcion = new Inscripcion(nombre,curso,actividad,turno,nivel,pago,horas,opcionesMarcadas);
        
        Inscritos.add(inscripcion);
        AlCancelar();
        
        estadoInicialPrograma();
        
        alertaInfo.setHeaderText("INSCRIPCION REALIZADA");
        alertaInfo.setContentText("La inscripción ha sido realizada con éxito.");
        alertaInfo.showAndWait();
    }
    
    /**
     * Nos mostrará los datos completos de un objeto Inscripcion.
     * @param i objeto inscripcion el cual se va a mostrar en el panel de configuracion, nombre y curso.
     */
    public void mostrar(Inscripcion i) {
        NombreAlumno.setText(i.getNombre());
        ComboboxCurso.setValue(i.getCurso());

        // RadioButton: marcar el que tenga el mismo texto
        for (Toggle t : Actividades.getToggles()) {
            if (((RadioButton) t).getText().equals(i.getActividad())) {
                Actividades.selectToggle(t);
            }
        }
        for (Toggle t : Turnos.getToggles()) {
            if (((RadioButton) t).getText().equals(i.getTurno())) {
                Turnos.selectToggle(t);
            }
        }
        for (Toggle t : Niveles.getToggles()) {
            if (((RadioButton) t).getText().equals(i.getNivel())) {
                Niveles.selectToggle(t);
            }
        }
        for (Toggle t : Pagos.getToggles()) {
            if (((RadioButton) t).getText().equals(i.getPago())) {
                Pagos.selectToggle(t);
            }
        }

        Deslizador.setValue(i.getHorasSemanales());

        // CheckBox: marcado si su texto está en la lista de opciones
        Comedor.setSelected(i.getOpcionesElegidas().contains(Comedor.getText()));
        Transporte.setSelected(i.getOpcionesElegidas().contains(Transporte.getText()));
        SeguroEscolar.setSelected(i.getOpcionesElegidas().contains(SeguroEscolar.getText()));
        MaterialIncluido.setSelected(i.getOpcionesElegidas().contains(MaterialIncluido.getText()));
}
    /**
     * Para poder buscar algo, necesitaremos que previamente este introducido un nombre dentro del TextField.
     * Nos mostrará los datos de la configuración de esa persona, y si hay varias, nos ira preguntando con un 
     * Alert Confirmation si queremos seguir viendo las otras personas que tienen el mismo nombre si es que existen.
     * 
     * Ya una consultado todo, volveremos al estado inicial del programa.
     */
    @FXML 
    public void AlBuscar() {
        
        //1. PRIMERO TENDREMOS QUE AGRARRAR EL NOMBRE DEL CAMPO, Y TODAS LAS COINCIDENCIAS METERLAS EN UNA UNICA LISTA
        
        String nombreBuscado = NombreAlumno.getText();
        List<Inscripcion> encontrados = new ArrayList<>();
        
        for (Inscripcion i : ListaInscritos.getItems()) {
            if (i.getNombre().equalsIgnoreCase(nombreBuscado)) {
                encontrados.add(i);
            }
        } 
        
        //2. SI NO SE ENCUENTRA NINGUNO, MENSAJE DE NO EXISTE
        
        if (encontrados.isEmpty()) {
                alertaWarning.setHeaderText("NO EXISTE");
                alertaWarning.setContentText("No existe un alumno con ese nombre");
                alertaWarning.showAndWait();
        }else {
        //3. SI ES QUE ALGO EXISTE QUE MUESTRE SOLO 1 OBJETO SI ES UNICO, Y SI NO, TODOS LOS DEMAS OBJETOS PERO CON CONFIRMACION DE SI QUIERE SEGUIR VIENDO A LOS DEMAS.
            if (encontrados.size()==1) {
                mostrar(encontrados.get(0));
            } else {
                int contador = 0;
                boolean seguir = true;
                
                //(Todo esto lo he hecho con un while porque con un break creo que podria quedar mal)
                
                while (seguir && contador < encontrados.size()) {
                    
                    mostrar(encontrados.get(contador));
                    
                    if (contador != encontrados.size() - 1) {
                        alertaConfirm.setHeaderText("¿Seguir buscando?");
                        Optional<ButtonType> respuesta = alertaConfirm.showAndWait();
                        
                        seguir = respuesta.isPresent() && respuesta.get() == ButtonType.OK;
                    } else {
                        alertaInfo.setHeaderText("Búsqueda");
                        alertaInfo.setContentText("No hay más resultados");
                        alertaInfo.showAndWait();
                    }
                    contador++;
                }
                estadoInicialPrograma();
            }
        }
    }
    
    /**
     * Lo utilizamos para borrar una inscripción que tenemos seleccionada de la ListView.
     * Nos preguntara con un Alert Confirmation si queremos eliminarlo o no.
     * Luego volverá al estado inicial del programa.
     */
    @FXML 
    public void AlEliminar() {
        Inscripcion seleccionado = ListaInscritos.getSelectionModel().getSelectedItem();
        
        Iterator<Inscripcion> it = ListaInscritos.getItems().iterator();
        while (it.hasNext()) {
            Inscripcion i = it.next();
            if (i.getNombre().equalsIgnoreCase(seleccionado.getNombre())) {
                
                alertaConfirm.setHeaderText("¿Está seguro de eliminarlo?");
                Optional<ButtonType> respuesta = alertaConfirm.showAndWait();
                
                if (respuesta.isPresent() && respuesta.get()==ButtonType.OK) {
                    it.remove();
                    ListaInscritos.refresh();
                }
            }
        }
        estadoInicialPrograma();
    }
    
    /**
     * Una vez tenemos seleccionado una inscripción de nuestra ListView y le damos al botón
     * nos abrirá un TextInputDialog para renombrar al alumno, y mientras este nombre cumpla con las normas
     * se cambiará el nombre. 
     * Si no cumple con las normas saltará un Alert Warning
     */
    @FXML 
    public void AlRenombrar() {
        Inscripcion seleccionado = ListaInscritos.getSelectionModel().getSelectedItem();
        
        TextInputDialog dialogo = new TextInputDialog(seleccionado.getNombre());
        dialogo.setTitle("Renombrar alumno");
        dialogo.setHeaderText("Nuevo nombre (máximo 15 caracteres)");
        Optional<String> resultado = dialogo.showAndWait();

        if (resultado.isPresent()) {
            String nuevoNombre = resultado.get();

            if (!nuevoNombre.isBlank() && nuevoNombre.length() <= 15) {
                seleccionado.setNombre(nuevoNombre);
                ListaInscritos.refresh();
                
            } else {
                alertaWarning.setHeaderText("INVALIDO");
                alertaWarning.setContentText("No se cambia nada");
                alertaWarning.showAndWait();
            }
        }
    }
    
    //AQUI DENTRO TAMBIEN TENDREMOS QUE HACER ALGO QUE HAGA SI EL TEXTFIELD ESTA VACIO SI O SI NO PODAMOS AÑADIR ALGO.
    
    /**
     * Este metodo borra absolutamente todas las selecciones y limpia prácticamente todos los bloques.
     */
    @FXML 
    public void AlCancelar() {
        NombreAlumno.setText(null);
        
        SeguroEscolar.setSelected(false);
        Comedor.setSelected(false);
        Transporte.setSelected(false);
        MaterialIncluido.setSelected(false);
        
        Actividades.selectToggle(null);
        Turnos.selectToggle(null);
        Niveles.selectToggle(null);
        Pagos.selectToggle(null);
        
        ComboboxCurso.getSelectionModel().selectFirst(); 
        
        Deslizador.setValue(1);
        estadoInicialPrograma();
    }
    
    /**
     * Para poder salir de nuestro programa con el boton Salir.
     */
    @FXML 
    public void AlSalir() {
        
        boolean seguir = false;
        
        if (Inscritos.size()!=0) {
            alertaConfirm.setHeaderText("Hay inscripciones sin guardar. ¿Salir igualmente?");
            Optional<ButtonType> respuesta = alertaConfirm.showAndWait();     
            seguir = respuesta.isPresent() && respuesta.get() == ButtonType.OK;
            
            if (seguir==true) {
                Platform.exit();
            }
        } else {
            Platform.exit();
        }
    }   
    
    /**
     * Guardaremos dentro de un fichero Inscritos.txt las inscripciones que hayan dentro del ListView.
     * (Hemos utilizado CREATE y APPEND para que se cree el fichero si este no existe, y que se añadan mas datos al final sin sobreescribir lo que hay en el fichero)
     * Todo con BufferedWriter y BufferedReader, si todo esta correcto salta un Alert Info de que el guardado ha sido completado.
     */
    @FXML
    public void GuardarInscripciones() {
        if (Inscritos.isEmpty()) {
            alertaWarning.setHeaderText("LISTA VACIA");
            alertaWarning.setContentText("No se puede guardar nada por estar la lista vacia");
            alertaWarning.showAndWait();
            return;
        }

        int contadorGuardados = 0;

        try (BufferedWriter out = Files.newBufferedWriter(Path.of(FICHERO),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND)) { //Ponemos tanto el CREATE, como el APPEND para que se cree el fichero si no existe, y escribe al final sin borrar lo anterior.

            for (Inscripcion i : Inscritos) {
                /* out.write(i.getNombre() + ";"
                        + i.getActividad() + ";"
                        + i.getCurso() + ";"
                        + i.getTurno() + ";"
                        + i.getNivel() + ";"
                        + i.getPago() + ";"
                        + i.getHorasSemanales() + ";"
                        + i.getOpcionesElegidas()); */
                out.write(i.toStringUno());
                out.newLine();
                contadorGuardados++;
            }

            Inscritos.clear();   // la lista (y el ListView) queda vacía

            alertaInfo.setHeaderText("GUARDADO COMPLETADO");
            alertaInfo.setContentText("Se han guardado: " + contadorGuardados + " inscritos");
            alertaInfo.showAndWait();

        } catch (IOException e) {
            alertaErr.setHeaderText("ERROR");
            alertaErr.setContentText("Hubo un error al guardar");
            alertaErr.showAndWait();
        }
    }
    
    /**
     * FASE 1. Leeremos el fichero linea a linea para poder construir todos los objetos y estos guardarlos en un ArrayList.
     * FASE 2. Mostraremos cada inscripción una a una, y las pasaremos al ListView mientras las mostramos.
     * FASE 3. Reescribiremos el fichero solo con las que NO se han pasado aun.
     * 
     * Ejemplo: fichero con A, B, C, D. Se pasan A y B y se pulsa Cancelar (n = 2).
     * El fichero se reescribe y queda solo con C y D, y A y B están en la lista del programa.
     * Al pulsar Guardar inscripciones (con APPEND), A y B se escriben al final,
     * y el fichero queda: C, D, A, B. No se pierde ni se duplica ninguna.
     */
    @FXML
    public void MostrarInscripciones() {
        File f = new File(FICHERO);
            
        if (!f.exists()) {
            alertaInfo.setHeaderText("NO HAY INSCRIPCIONES GUARDADAS");
            alertaInfo.setContentText("NO EXISTE el fichero de guardado");
            alertaInfo.showAndWait();
            return; //Se avisa y termina
        }
        
        List<Inscripcion> inscripcionesGuardadas = new ArrayList<>();
        
        //FASE 1. Leer el fichero linea a linea para poder construir los objetos.
        try (BufferedReader in = Files.newBufferedReader(Path.of(FICHERO))) {
           
            String linea;

            while ((linea = in.readLine())!=null) {
                String[] p = linea.split(";");
                String nombre = p[0];
                String curso = p[1];
                String actividad = p[2];
                String turno = p[3];
                String nivel = p[4];
                String pago = p[5];
                int horasSemanales = Integer.parseInt(p[6]);
                
                List <String> opciones = new ArrayList<>();
                String[] partes = p[7].split(",");
                
                if (!p[7].isBlank()) {
                    for (String opcion : partes) {
                        opciones.add(opcion);
                    }
                }
                
                Inscripcion i = new Inscripcion(nombre, curso, actividad, turno, nivel, pago, horasSemanales, opciones);
                inscripcionesGuardadas.add(i);
            } 
        } catch (IOException ex) {
            System.getLogger(PrimaryController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        // Si el fichero estaba vacío, se avisa y se termina
        if (inscripcionesGuardadas.isEmpty()) {
            alertaInfo.setHeaderText("No hay inscripciones guardadas");
            alertaInfo.setContentText("El fichero no contiene inscripciones");
            alertaInfo.showAndWait();
            return;
        }
        
        //FASE 2. mostrar una a una y pasarlas a la lista del programa.
        
        int n = 0;
        boolean seguir = true;
        
        while (seguir==true && n < inscripcionesGuardadas.size()) {
            Inscripcion i = inscripcionesGuardadas.get(n);
            mostrar(i);         //Se rellena el formulario
            Inscritos.add(i);   //Se añade la lista del programa
            n++;                //Ya hemos pasado una Inscripcion.
            
            boolean quedanMas = n < inscripcionesGuardadas.size(); //quedanMas sera verdadero mientras se cumpla lo de la derecha.
        
            if (quedanMas) { //si quedanMas es true:
                alertaConfirm.setHeaderText("¿Ver la siguiente?");
                Optional<ButtonType> respuesta = alertaConfirm.showAndWait();
                
                seguir = respuesta.isPresent() && respuesta.get() == ButtonType.OK; //seguir será true mientras haya respuesta presente y el ButtonType sea OK.

                if (!seguir) { //Si no cumple con lo de arriba, (que es basicamente que le damos al boton de cancelar)
                    NombreAlumno.requestFocus();
                }
            }   
            
        }
        
        //FASE 3. reescribir el fichero solo con las que NO se han pasado aun.
        
        try (BufferedWriter out = Files.newBufferedWriter(Path.of(FICHERO))) {
            
            //Una vez le haya dado a cancelar, se reescribe el fichero desde el contador n hasta el final, para guardar los otros 2 que no hemos mostrado en el fichero reescrito.
            //Y ahora quedan los otros 2 principales (A y B) que estan en la ListView, y como en GuardarInscritos tenemeos el .APPEND, estos se guardaran despues de (C y D)
            
            for (int k = n; k < inscripcionesGuardadas.size(); k++) {
                out.write(inscripcionesGuardadas.get(k).toStringUno());
                out.newLine();
            }
        } catch (IOException e) {
            alertaErr.setHeaderText("ERROR");
            alertaErr.setContentText("No se pudo actualizar el fichero");
            alertaErr.showAndWait();
        }
    }
}

