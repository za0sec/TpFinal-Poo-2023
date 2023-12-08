package frontend;

import backend.CanvasState;
import backend.model.*;
import frontend.buttons.drawButtons.*;
import frontend.drawFigures.*;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.beans.value.ChangeListener;
import javafx.scene.paint.Color;

import java.util.*;

public class PaintPane extends BorderPane {

	// BackEnd
	CanvasState<DrawFigure> canvasState;

	// Canvas y relacionados
	Canvas canvas = new Canvas(800, 600);
	GraphicsContext gc = canvas.getGraphicsContext2D();
	Color lineColor = Color.BLACK;
	Color defaultFillColor = Color.YELLOW;

	// Botones Barra Izquierda
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

	// Selector de color de relleno
	ColorPicker fillColorPicker = new ColorPicker(defaultFillColor);

	// Dibujar una figura
	Point startPoint;

	// Seleccionar una figura
	Set<DrawFigure> selectedFigures = new HashSet<>();

	ArrayList<Boolean> status = new ArrayList<>();

	//Figura temporal
	private DrawFigure previewFigure;

	private Color previousColor = defaultFillColor;

	//Status de si hay seleccion de una figura
	private boolean isSelected = false;

	// StatusBar
	StatusPane statusPane;

	// Colores de relleno de cada figura
	Map<DrawFigure, Color> figureColorMap = new HashMap<>();

	public PaintPane(CanvasState<DrawFigure> canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;
		ToggleButton[] toolsArr = {multiSelection, rectangleButton, circleButton, squareButton, ellipseButton, deleteButton, gatherButton, unGatherButton, rotateButton, mirrorHButton, mirrorVButton, enlargeButton, reduceButton};
		ToggleGroup tools = new ToggleGroup();
		for (ToggleButton tool : toolsArr) {
			tool.setMinWidth(90);
			tool.setToggleGroup(tools);
			tool.setCursor(Cursor.HAND);
		}
		rectangleButton.setUserData(new DrawRectangleButton());
		circleButton.setUserData(new DrawCircleButton());
		squareButton.setUserData(new DrawSquareButton());
		ellipseButton.setUserData(new DrawEllipseButton());
		multiSelection.setUserData(new DrawMultiSelectionButton());


		// Crear HBox para CheckBoxes
		HBox checkBoxHBox = new HBox(10, new Label("Efectos: "), shadowBox, gradientBox, beveledBox);
		CheckBox[] boxArr = {shadowBox, gradientBox, beveledBox};
		checkBoxHBox.setPadding(new Insets(5));
		checkBoxHBox.setStyle("-fx-background-color: #999");
		checkBoxHBox.setAlignment(Pos.CENTER);

		// Crear VBox para botones
		VBox buttonsBox = new VBox(10);
		buttonsBox.getChildren().addAll(toolsArr);
		buttonsBox.getChildren().add(fillColorPicker);
		buttonsBox.setPadding(new Insets(5));
		buttonsBox.setStyle("-fx-background-color: #999");
		buttonsBox.setPrefWidth(100);

		canvas.setOnMousePressed(event -> {
			startPoint = new Point(event.getX(), event.getY());
			previewFigure = null;
			Toggle selectedToggle = tools.getSelectedToggle();
			if (selectedToggle != null) {
				Buttons buttons = (Buttons) selectedToggle.getUserData();
				if (buttons != null) {
					previewFigure = buttons.execute(startPoint, startPoint, gc, fillColorPicker.getValue(), Color.BLACK);
					previewFigure.setStatus(shadowBox.isSelected(), gradientBox.isSelected(), beveledBox.isSelected());
					previewFigure.draw();
				}
			}
		});

		canvas.setOnMouseReleased(event -> {
			Point endPoint = new Point(event.getX(), event.getY());
			if(startPoint == null) {
				return ;
			}
			if(endPoint.getX() < startPoint.getX() || endPoint.getY() < startPoint.getY()) {
				return ;
			}
			DrawFigure newFigure = null;
			Toggle selectedToggle = tools.getSelectedToggle();
				Buttons buttons = (Buttons) selectedToggle.getUserData();
				if (!multiSelection.isSelected() && buttons.isDrawable() && !isSelected) {
					newFigure = buttons.execute(startPoint, endPoint, gc, fillColorPicker.getValue(), Color.BLACK);
					newFigure.setStatus(shadowBox.isSelected(), gradientBox.isSelected(), beveledBox.isSelected());
					newFigure.draw();
					figureColorMap.put(newFigure, fillColorPicker.getValue());
					canvasState.addFigure(newFigure);
					startPoint = null;
				}
		});

		canvas.setOnMouseMoved(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());
			boolean found = false;
			StringBuilder label = new StringBuilder();
			for (DrawFigure figure : canvasState.figures()) {
				if (figure.belongs(eventPoint)) {
					found = true;
					label.append(figure.toString());
				}
			}
			if (found) {
				statusPane.updateStatus(label.toString());
			} else {
				statusPane.updateStatus(eventPoint.toString());
			}

		});

		canvas.setOnMouseClicked(event -> {
			isSelected = false;
			statusPane.updateStatus("Ninguna figura encontrada");
			selectedFigures.clear();

			if(multiSelection.isSelected()) {
				StringBuilder label = new StringBuilder("Se seleccionó: ");
				for (DrawFigure figure : canvasState.figures()) {
					if(figure.intersects((Rectangle) previewFigure.getFigure())) {
						selectedFigures.add(figure);
						status =  figure.getStatus();
					}
				}
				for (int i=0; i< status.size(); i++)
					boxArr[i].setSelected(status.get(i));

				label.append(canvasState.getFigures(selectedFigures));
				if (!selectedFigures.isEmpty()) {
					statusPane.updateStatus(label.toString());
					isSelected = true;
			}
				previewFigure = null;
				redrawCanvas(selectedFigures);
			}
		});

		canvas.setOnMouseDragged(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());
			if(multiSelection.isSelected() && isSelected) {
				double diffX = (eventPoint.getX() - startPoint.getX()) / 100;
				double diffY = (eventPoint.getY() - startPoint.getY()) / 100;
				if (!selectedFigures.isEmpty()) {
					Set<DrawFigure> groups = canvasState.getFigures(selectedFigures);
					for(DrawFigure figure : groups) {
							figure.move(diffX, diffY);
							redrawCanvas(selectedFigures);
					}
				}
			}
			if (previewFigure != null && !isSelected) {
				previewFigure.updatePreview(eventPoint);
				redrawCanvas(previewFigure);
			}
		});

		gatherButton.setOnAction(event -> {
			if (!selectedFigures.isEmpty()) {
				StringBuilder toShow = new StringBuilder("Se agruparon: ");
				selectedFigures = canvasState.getFigures(selectedFigures);
				toShow.append(selectedFigures);
				canvasState.gather(selectedFigures);
				redrawCanvas(selectedFigures);
				statusPane.updateStatus(toShow.toString());
				pressSelected();
			}
		});
		unGatherButton.setOnAction(event -> {
			if (!selectedFigures.isEmpty()) {
				StringBuilder toShow = new StringBuilder("Se desagruparon: ");
				selectedFigures = canvasState.getFigures(selectedFigures);
				toShow.append(selectedFigures);
				canvasState.unGather(selectedFigures);
				redrawCanvas(selectedFigures);
				isSelected = false;
				statusPane.updateStatus(toShow.toString());
				selectedFigures.clear();
				pressSelected();
			}
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
		});


		fillColorPicker.valueProperty().addListener(new ChangeListener<Color>() {
			@Override
			public void changed(ObservableValue<? extends Color> observableValue, Color oldColor, Color newColor) {
				previousColor = oldColor;
				if (!selectedFigures.isEmpty()) {
					for (DrawFigure figure : selectedFigures) {
						figureColorMap.put(figure, newColor);
						figure.setFill(newColor);
					}
					redrawCanvas(selectedFigures);
				}
			}
		});


		setTop(checkBoxHBox);
		setLeft(buttonsBox);
		setRight(canvas);
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
		pressSelected();
	}

	private void pressSelected(){
		multiSelection.requestFocus();
		multiSelection.fire();
	}

	void redrawCanvas(DrawFigure figure){
		redrawCanvas(Set.of(figure));
	}

	void redrawCanvas(Set<DrawFigure> figures){
		Set<DrawFigure> preDraw = canvasState.figuresSet();
		if (isSelected){
			setCanvas(preDraw);
			return;
		}
		preDraw.removeAll(figures);
		setCanvas(preDraw);
	}

	private void setCanvas(Set<DrawFigure> figures) {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for(DrawFigure figure : figures) {
			Set<DrawFigure> groups = canvasState.getFigures(selectedFigures);
			gc.setFill(figureColorMap.get(figure));
			gc.setStroke(lineColor);
			if (groups.contains(figure) && isSelected) {
				gc.setStroke(Color.RED);
			}
			figure.draw();
		}
		if (previewFigure != null) {
			gc.setFill(fillColorPicker.getValue());
			previewFigure.draw();
		}
	}
}
