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
import java.util.function.Function;

public class PaintPane extends BorderPane {

	// BackEnd
	CanvasState<DrawFigure> canvasState;

	// Canvas y relacionados
	Canvas canvas = new Canvas(800, 600);
	GraphicsContext gc = canvas.getGraphicsContext2D();
	Color lineColor = Color.BLACK;
	Color defaultFillColor = Color.YELLOW;

	// Selector de color de relleno
	ColorPicker fillColorPicker = new ColorPicker(defaultFillColor);

	// Dibujar una figura
	Point startPoint;

	ToggleButton multiSelection;
	ToggleButton rectangleButton;
	ToggleButton circleButton;
	ToggleButton squareButton;
	ToggleButton ellipseButton;
	ToggleButton deleteButton;
	ToggleButton gatherButton;
	ToggleButton unGatherButton;
	ToggleButton rotateButton;
	ToggleButton mirrorHButton;
	ToggleButton mirrorVButton;
	ToggleButton enlargeButton;
	ToggleButton reduceButton;
	CheckBox shadowBox;
	CheckBox gradientBox;
	CheckBox beveledBox;


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

		ButtonManager buttonsManager = new ButtonManager(canvasState, this::redrawCanvas, statusPane, selectedFigures, fillColorPicker);

		multiSelection = buttonsManager.getMultiSelection();
		rectangleButton = buttonsManager.getRectangleButton();
		circleButton = buttonsManager.getCircleButton();
		squareButton = buttonsManager.getSquareButton();
		ellipseButton = buttonsManager.getEllipseButton();
		deleteButton = buttonsManager.getDeleteButton();
		gatherButton = buttonsManager.getGatherButton();
		unGatherButton = buttonsManager.getUnGatherButton();
		rotateButton = buttonsManager.getRotateButton();
		mirrorHButton = buttonsManager.getMirrorHButton();
		mirrorVButton = buttonsManager.getMirrorVButton();
		enlargeButton = buttonsManager.getEnlargeButton();
		reduceButton = buttonsManager.getReduceButton();

		shadowBox = buttonsManager.getShadowBox();
		gradientBox = buttonsManager.getGradientBox();
		beveledBox = buttonsManager.getBeveledBox();


		ToggleButton[] toolsArr = new ToggleButton[]{multiSelection, rectangleButton, circleButton, squareButton, ellipseButton, deleteButton, gatherButton, unGatherButton, rotateButton, mirrorHButton, mirrorVButton, enlargeButton, reduceButton};
		ToggleGroup tools = new ToggleGroup();
		for (ToggleButton tool : toolsArr) {
			tool.setMinWidth(90);
			tool.setToggleGroup(tools);
			tool.setCursor(Cursor.HAND);
		}

		buttonsSetData();

		// Crear HBox para CheckBoxes
		HBox checkBoxHBox = new HBox(10, new Label("Efectos: "), shadowBox, gradientBox, beveledBox);
		CheckBox[] boxArr = {shadowBox, gradientBox, beveledBox};
		checkBoxHBox.setPadding(new Insets(5));
		checkBoxHBox.setStyle("-fx-background-color: #999");
		checkBoxHBox.setAlignment(Pos.CENTER);

		// Crear VBox para botones
		VBox buttonsBox = new VBox(10);
		buttonsBox.getChildren().addAll(toolsArr);
		buttonsBox.getChildren().add(this.fillColorPicker);
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

	void redrawCanvas(DrawFigure figure){
		redrawCanvas(Set.of(figure));
	}

	private void buttonsSetData(){
		rectangleButton.setUserData(new DrawRectangleButton());
		circleButton.setUserData(new DrawCircleButton());
		squareButton.setUserData(new DrawSquareButton());
		ellipseButton.setUserData(new DrawEllipseButton());
		multiSelection.setUserData(new DrawMultiSelectionButton());
	}

}
