package frontend.buttons;

import backend.CanvasState;
import frontend.Redraw;
import frontend.StatusPane;
import frontend.buttons.drawButtons.*;
import frontend.drawFigures.DrawFigure;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Set;

public class ButtonManager {

    ToggleButton multiSelection = new ToggleButton("Seleccionar");
    ToggleButton rectangleButton = new ToggleButton("Rectangulo");
    ToggleButton circleButton = new ToggleButton("Circulo");
    ToggleButton squareButton = new ToggleButton("Cuadrado");
    ToggleButton ellipseButton = new ToggleButton("Elipse");

    ToggleButton deleteButton = new ToggleButton("Borrar");
    ToggleButton gatherButton = new ToggleButton("Agrupar");
    ToggleButton unGatherButton = new ToggleButton("Desagrupar");
    ToggleButton rotateButton = new ToggleButton("Girar D");
    ToggleButton mirrorHButton = new ToggleButton("Voltear H");
    ToggleButton mirrorVButton = new ToggleButton("Voltear V");
    ToggleButton enlargeButton = new ToggleButton("Escalar +");
    ToggleButton reduceButton = new ToggleButton("Escalar -");

    CheckBox shadowBox = new CheckBox("Sombra");
    CheckBox gradientBox = new CheckBox("Gradiente");
    CheckBox beveledBox = new CheckBox("Biselado");

    private final Redraw redrawHandler;

    private ToggleButton[] toolsArr;
    private VBox buttonsBox;
    private HBox checkBoxHBox;

    private final CanvasState<DrawFigure> canvasState;
    private final StatusPane statusPane;

    private final Set<DrawFigure> selectedFigures;

    ColorPicker fillColorPicker;

    CheckBox[] boxArr;

    public ButtonManager(CanvasState<DrawFigure> canvasState, Redraw redrawHandler, StatusPane statusPane, Set<DrawFigure> selectedFigures, ColorPicker fillColorPicker) {
        this.canvasState = canvasState;
        this.redrawHandler = redrawHandler;
        this.statusPane = statusPane;
        this.selectedFigures = selectedFigures;
        this.fillColorPicker = fillColorPicker;
        this.toolsArr = new ToggleButton[]{multiSelection, rectangleButton, circleButton, squareButton, ellipseButton, deleteButton, gatherButton, unGatherButton, rotateButton, mirrorHButton, mirrorVButton, enlargeButton, reduceButton};
        this.boxArr = new CheckBox[]{shadowBox, gradientBox, beveledBox};

        // Crear HBox para CheckBoxes
        this.checkBoxHBox = new HBox(10, new Label("Efectos: "), shadowBox, gradientBox, beveledBox);
        checkBoxHBox.setPadding(new Insets(5));
        checkBoxHBox.setStyle("-fx-background-color: #999");
        checkBoxHBox.setAlignment(Pos.CENTER);

        // Crear VBox para botones
        this.buttonsBox = new VBox(10);
        buttonsBox.getChildren().addAll(toolsArr);
        buttonsBox.getChildren().add(this.fillColorPicker);
        buttonsBox.setPadding(new Insets(5));
        buttonsBox.setStyle("-fx-background-color: #999");
        buttonsBox.setPrefWidth(100);



        buttonsSetData();

        configureButtons();
    }

    public HBox getCheckBoxHBox(){
        return checkBoxHBox;
    }
    public VBox getButtonsBox(){
        return buttonsBox;
    }

    public ToggleButton[] getToolsArr(){
        return toolsArr;
    }

    public CheckBox[] getBoxArr(){
        return boxArr;
    }

    private void configureButtons(){
        gatherButton.setOnAction(event -> {
            StringBuilder toShow = new StringBuilder("Se agruparon: ");
            groupApply(canvasState::gather, toShow);
        });
        unGatherButton.setOnAction(event -> {
            StringBuilder toShow = new StringBuilder("Se desagruparon: ");
            groupApply(canvasState::unGather, toShow);
            selectedFigures.clear();
        });
        rotateButton.setOnAction(event -> {
            StringBuilder toShow = new StringBuilder("Se rotó: ");
            applyAction(DrawFigure::rotate, toShow);
        });
        enlargeButton.setOnAction(event -> {
            StringBuilder toShow = new StringBuilder("Se agrandó un 25%: ");
            applyAction(DrawFigure::enlarge, toShow);
        });
        reduceButton.setOnAction(event -> {
            StringBuilder toShow = new StringBuilder("Se achicó un 25%: ");
            applyAction(DrawFigure::reduce, toShow);
        });
        mirrorHButton.setOnAction(event -> {
            StringBuilder toShow = new StringBuilder("Se volteó horizontalmente: ");
            applyAction(DrawFigure::mirrorH, toShow);
        });
        mirrorVButton.setOnAction(event -> {
            StringBuilder toShow = new StringBuilder("Se volteó verticalmente: ");
            applyAction(DrawFigure::mirrorV, toShow);
        });
        shadowBox.setOnAction(event -> {
            StringBuilder toShow = new StringBuilder("Se aplico sombra a: ");
            applyAction(figure -> figure.setShadow(shadowBox.isSelected()), toShow);
        });
        gradientBox.setOnAction(event -> {
            StringBuilder toShow = new StringBuilder("Se aplico gradiente a: ");
            applyAction(figure -> figure.setGradient(gradientBox.isSelected(), fillColorPicker.getValue()), toShow);

        });
        beveledBox.setOnAction(event -> {
            StringBuilder toShow = new StringBuilder("Se aplico biselado a: ");
            applyAction(figure -> figure.setBeveled(beveledBox.isSelected()), toShow);
        });
        deleteButton.setOnAction(event -> {
            StringBuilder toShow = new StringBuilder("Se elimino: ");
            applyAction(canvasState::deleteFigure, toShow);
            pressSelected();
        });
    }


    //Buttons
    public ToggleButton getRectangleButton(){
        return rectangleButton;
    }
    public ToggleButton getCircleButton(){
        return circleButton;
    }
    public ToggleButton getSquareButton(){
        return squareButton;
    }
    public ToggleButton getEllipseButton(){
        return ellipseButton;
    }

    /*public ToggleButton getDeleteButton(){
        return deleteButton;
    }
    public ToggleButton getGatherButton(){
        return gatherButton;
    }
    public ToggleButton getUnGatherButton(){
        return unGatherButton;
    }
    public ToggleButton getRotateButton(){
        return rotateButton;
    }
    public ToggleButton getEnlargeButton(){
        return enlargeButton;
    }
    public ToggleButton getReduceButton(){
        return reduceButton;
    }
    public ToggleButton getMirrorHButton(){
        return mirrorHButton;
    }
    public ToggleButton getMirrorVButton(){
        return mirrorVButton;
    }*/

    public ToggleButton getMultiSelection(){
        return multiSelection;
    }

    //CheckBoxes
    public CheckBox getShadowBox(){
        return shadowBox;
    }
    public CheckBox getGradientBox(){
        return gradientBox;
    }
    public CheckBox getBeveledBox(){
        return beveledBox;
    }


    private void groupApply(GroupAction action, StringBuilder toShow){
        if (!selectedFigures.isEmpty()) {
            Set<DrawFigure> figures = canvasState.getFigures(selectedFigures);
            action.apply(figures);
            redrawHandler.redraw(figures);
            toShow.append(figures);
        }
        statusPane.updateStatus(toShow.toString());
        pressSelected();
    }

    private void applyAction(FigureAction action, StringBuilder toShow) {
        if (!selectedFigures.isEmpty()) {
            for (DrawFigure figure : canvasState.getFigures(selectedFigures)) {
                toShow.append(figure.toString()).append(", ");
                action.apply(figure);
                redrawCanvas(figure);
            }
        }
        statusPane.updateStatus(toShow.toString());
    }

    private void pressSelected(){
        multiSelection.requestFocus();
        multiSelection.fire();
    }

    void redrawCanvas(DrawFigure figure){
        redrawHandler.redraw(Set.of(figure));
    }

    private void buttonsSetData(){
        rectangleButton.setUserData(new DrawRectangleButton());
        circleButton.setUserData(new DrawCircleButton());
        squareButton.setUserData(new DrawSquareButton());
        ellipseButton.setUserData(new DrawEllipseButton());
        multiSelection.setUserData(new DrawMultiSelectionButton());
    }


}
