package frontend.drawFigures;

import backend.model.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.*;
import javafx.scene.shape.ArcType;

import java.util.ArrayList;
import java.util.List;

public abstract class DrawFigure implements Figure {

    protected final GraphicsContext gc;

    protected boolean isShadow, isGradient, isBeveled = false;

    protected Color fill;

    public DrawFigure(GraphicsContext gc, Color fill, Color stroke){
        this.gc = gc;
        setFill(fill);
    }

    public void setFill(Color fill){
        this.fill = fill;
    }

    public void draw(boolean isSelected){
        setShadow(isShadow);
        setBeveled(isBeveled);
        gc.setLineDashes(null);
        gc.setFill(fill);
        gc.setLineWidth(1);
        setGradient(isGradient, fill);
        if (isSelected)
            gc.setStroke(Color.RED);
    };

    public abstract Figure getFigure();

    public abstract void move(double diffX, double diffY);

    public abstract boolean belongs(Point eventPoint);

    public abstract void updatePreview(Point eventPoint);

    public abstract boolean intersects(Rectangle other);


    public void setBeveled(boolean value){
        isBeveled = value;
    }

    protected void setOvalBeveled(Point centerPoint, double sMayorAxis, double sMinorAxis){
        if (isBeveled) {
            Ellipse beveled = new Ellipse(centerPoint, sMayorAxis, sMinorAxis);
            double arcX = beveled.getCenterPoint().getX() - beveled.getsMayorAxis();
            double arcY = beveled.getCenterPoint().getY() - beveled.getsMinorAxis();
            gc.setLineWidth(10);
            gc.setStroke(Color.LIGHTGRAY);
            gc.strokeArc(arcX, arcY, beveled.getsMayorAxis() * 2, beveled.getsMinorAxis() * 2, 45, 180, ArcType.OPEN);
            gc.setStroke(Color.BLACK);
            gc.strokeArc(arcX, arcY, beveled.getsMayorAxis() * 2, beveled.getsMinorAxis() * 2, 225, 180, ArcType.OPEN);
        }
    }

    public void setShadow(boolean value){
        isShadow = value;
    }

    protected void setOvalShadow(Point centerPoint, double sMayorAxis, double sMinorAxis){
        if (isShadow) {
            Ellipse shadow = new Ellipse(centerPoint, sMayorAxis, sMinorAxis);

            gc.setFill(Color.GRAY);
            gc.fillOval(
                    shadow.getCenterPoint().getX() - (shadow.getsMayorAxis() / 2) + 10,
                    shadow.getCenterPoint().getY() - (shadow.getsMinorAxis() / 2) + 10,
                    shadow.getsMayorAxis(),
                    shadow.getsMinorAxis()
            );
        }
    }

    public ArrayList<Boolean> getStatus(){
        return new ArrayList<>(List.of(isShadow, isGradient, isBeveled));
    }

    public void setStatus(boolean isShadow, boolean isGradient, boolean isBeveled){
        this.isShadow =  isShadow;
        this.isGradient = isGradient;
        this.isBeveled = isBeveled;
    }

    protected void rectangleShadow(Rectangle rectangle){
        Rectangle shadow = new Rectangle(rectangle.getTopLeft(), rectangle.getBottomRight());
        gc.setFill(Color.GRAY);
        gc.fillRect(shadow.getTopLeft().getX() + 10.0,
                shadow.getTopLeft().getY() + 10.0,
                Math.abs(shadow.getTopLeft().getX() - shadow.getBottomRight().getX()),
                Math.abs(shadow.getTopLeft().getY() - shadow.getBottomRight().getY()));
    }

    protected void rectangleBeveled(Rectangle rectangle){
        if (isBeveled) {
            Rectangle beveled = new Rectangle(rectangle.getTopLeft(), rectangle.getBottomRight());
            double x = beveled.getTopLeft().getX();
            double y = beveled.getTopLeft().getY();
            double width = Math.abs(x - beveled.getBottomRight().getX());
            double height = Math.abs(y - beveled.getBottomRight().getY());
            gc.setLineWidth(10);
            gc.setStroke(Color.LIGHTGRAY);
            gc.strokeLine(x, y, x + width, y);
            gc.strokeLine(x, y, x, y + height);
            //El problema es que cada vez que llamo al redrawCanvas para un beveled, se va a quedar como ultimo color de la linea el color negro, y yo quiero que si se selecciona se ponga el color
            gc.setStroke(Color.BLACK);
            gc.strokeLine(x + width, y, x + width, y + height);
            gc.strokeLine(x, y + height, x + width, y + height);

        }
    }

    public void setGradient(boolean value, Color fill) {
        isGradient = value;
        if (isGradient){
            RadialGradient radialGradient = new RadialGradient(0, 0, 0.5, 0.5, 0.5, true,
                    CycleMethod.NO_CYCLE,
                    new Stop(0, fill),
                    new Stop(1, fill.invert()));
        gc.setFill(radialGradient);
        }
    }

    public void setRectangleGradient(boolean value, Color fill){
        isGradient = value;
        if (isGradient) {
            LinearGradient linearGradient = new LinearGradient(0, 0, 1, 0, true,
                    CycleMethod.NO_CYCLE,
                    new Stop(0, fill),
                    new Stop(1, fill.invert()));
            gc.setFill(linearGradient);
        }
    }

    public abstract void rotate();
    public void resizeOvals(Ellipse ellipse, double percentage){
        ellipse.setAxis(ellipse.getsMayorAxis() * percentage, ellipse.getsMinorAxis() * percentage);
    }

    protected void resizeRectangle(Rectangle rectangle, double percentage){
        Point centerPoint = new Point((rectangle.getTopLeft().getX() + rectangle.getBottomRight().getX())/2 , (rectangle.getBottomRight().getY() + rectangle.getTopLeft().getY())/2);
        rectangle.setTopLeft(new Point(centerPoint.getX() - (((rectangle.getBottomRight().getX() - rectangle.getTopLeft().getX()) * percentage)/2), centerPoint.getY() - (((rectangle.getBottomRight().getY() - rectangle.getTopLeft().getY()) * percentage)/2)));
        rectangle.setBottomRight(new Point(centerPoint.getX() + (((rectangle.getBottomRight().getX() - rectangle.getTopLeft().getX()) * percentage)/2), centerPoint.getY() + (((rectangle.getBottomRight().getY() - rectangle.getTopLeft().getY()) * percentage)/2)));
    }
    
    protected  void mirrorRectangles(Rectangle rectangle, boolean flag){ //mirrorH if flag == true
        if(flag){
            double lengthRectangle = rectangle.getBottomRight().getX() - rectangle.getTopLeft().getX();
            rectangle.setTopLeft(new Point(rectangle.getBottomRight().getX() , rectangle.getTopLeft().getY()));
            rectangle.setBottomRight(new Point(rectangle.getBottomRight().getX() + lengthRectangle , rectangle.getBottomRight().getY()));
        } else {
            double heightRectangle = rectangle.getBottomRight().getY() - rectangle.getTopLeft().getY();
            rectangle.setTopLeft(new Point(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY() + heightRectangle));
            rectangle.setBottomRight(new Point(rectangle.getBottomRight().getX(), rectangle.getBottomRight().getY() + heightRectangle));
        }
    }
    public abstract void enlarge();
    public abstract void reduce();
    public abstract void mirrorH();
    public abstract void mirrorV();
}
