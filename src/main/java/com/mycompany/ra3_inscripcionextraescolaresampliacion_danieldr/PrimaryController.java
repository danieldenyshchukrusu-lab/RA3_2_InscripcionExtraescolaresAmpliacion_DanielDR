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
    
    @FXML
    private void initialize() {
        
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
                    if (nuevo.length() > 15) NombreAlumno.setText(antiguo);
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
    
    @FXML 
    private void alPulsar() {
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
    
    @FXML
    private void configuracionEstandar() {
        
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
    
    @FXML
    private void configuracionEstandarFALSE() {
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
    
    @FXML
    private void estadoInicialPrograma() {
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
    
    //VUELVE A REVISAR ABSOLUTAMENTE TODO EL CODIGO DE ARRIBA Y LUEGO HAZ TODO 
    
    @FXML 
    private void AlAñadir() {
        
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
        
        //Llamamos otra vez al initialize para que luego no podamos añadir un alumno null.
        estadoInicialPrograma();
        
        alertaInfo.setHeaderText("INSCRIPCION REALIZADA");
        alertaInfo.setContentText("La inscripción ha sido realizada con éxito.");
        alertaInfo.showAndWait();
    }
    
    private void mostrar(Inscripcion i) {
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
    
    @FXML 
    private void AlBuscar() {
        
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
    
    @FXML 
    private void AlEliminar() {
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
    
    @FXML 
    private void AlRenombrar() {
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
    @FXML 
    private void AlCancelar() {
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
    
    @FXML 
    private void AlSalir() {
        
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
    
    @FXML
    private void GuardarInscripciones() {
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
    
    @FXML
    private void MostrarInscripciones() {
        File f = new File(FICHERO);
        
        if (!f.exists()) {
            alertaInfo.setHeaderText("NO HAY INSCRIPCIONES GUARDADAS");
            alertaInfo.setContentText("NO EXISTE el fichero de guardado");
            alertaInfo.showAndWait();
            return;
        }
        
        try (BufferedReader in = Files.newBufferedReader(Path.of(FICHERO))) {
           
            String linea;
            boolean seguir = true;
            
            
            while ((linea = in.readLine())!=null && seguir) {
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
                
                for (String opcion : partes) {
                    opciones.add(opcion);
                }
                
                Inscripcion i = new Inscripcion(nombre, curso, actividad, turno, nivel, pago, horasSemanales, opciones);
                
                Inscritos.add(i);
                mostrar(i);
                
                //No he podido hacer lo de que se borre o no porque seria cambiar todo el metodo.
                
                alertaConfirm.setHeaderText("¿Ver la siguiente?");
                Optional<ButtonType> respuesta = alertaConfirm.showAndWait();     
                seguir = respuesta.isPresent() && respuesta.get() == ButtonType.OK;
                
                if (seguir==false) {
                    NombreAlumno.requestFocus();
                }
            } 
        } catch (IOException ex) {
            System.getLogger(PrimaryController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
    }
    
}

