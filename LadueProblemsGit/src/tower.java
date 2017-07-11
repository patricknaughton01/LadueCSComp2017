import java.util.*;
import java.io.*;
public class tower {
	public static ArrayList<Point2D> points = new ArrayList<Point2D>();
	public static Point2D centroid = new Point2D(0, 0);
	public static double centroidRadius = 0;
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException{
		BufferedReader r = new BufferedReader(new FileReader("tower.in"));
		int numSets = Integer.valueOf(r.readLine().trim());
		for(int i = 0; i<numSets; i++){
			ArrayList<Point2D> ps = new ArrayList<Point2D>();
			int numPoints = Integer.valueOf(r.readLine().trim());
			for(int j = 0; j<numPoints; j++){
				String[] tmp = r.readLine().trim().split(" ");
				double[] t = {Double.valueOf(tmp[0]), Double.valueOf(tmp[1])};
				Point2D p = new Point2D(t[0], t[1]);
				ps.add(p);
			}
			points = (ArrayList<Point2D>) ps.clone();
			centroid = getCentroid();
			centroidRadius = centroid.distanceTo(getFarthestFrom(centroid));
			Circle c = getSmallestCircle();
			System.out.println(roundToThreePlaces(c.getX())
					+ " " + roundToThreePlaces(c.getY()) + " " + 
					roundToThreePlaces(c.getR()));
		}
		r.close();

	}
	
	public static String roundToThreePlaces(double d){
		double t = d*1000;
		long r = Math.round(t);
		double x = r/1000.0;
		String tmp = Double.toString(x);
		while(decLength(tmp)<3){
			tmp+="0";
		}
		return(tmp);
	}
	
	public static int decLength(String str){
		int ind = str.indexOf(".");
		String s = str.substring(ind+1);
		return(s.length());
	}
	
	public static Point2D getCentroid(){
		double x = 0;
		double y = 0;
		for(int i = 0; i<points.size(); i++){
			x+=points.get(i).x();
			y+=points.get(i).y();
		}
		x/=points.size();
		y/=points.size();
		Point2D r = new Point2D(x, y);
		return(r);
	}
    public static Circle getSmallestCircle(){
    	if(centroidRadius==0){
    		return(new Circle(centroid.x(), centroid.y(), centroidRadius));
    	}
    	Circle smallest = new Circle(centroid.x(), centroid.y(), centroidRadius);
    	Point2D outer = getFarthestFrom(centroid);
    	double changeRate = 0.0001;
    	Point2D mid;
    	Point2D center = new Point2D(0, 0);
    	while((mid = allIn(smallest))==null){
    		double dX = (getFarthestFrom(centroid).x()-smallest.getX())*changeRate;
    		double dY = (getFarthestFrom(centroid).y()-smallest.getY())*changeRate;
    		center = new Point2D(smallest.getX()+dX, smallest.getY()+dY);
    		smallest = new Circle(center.x(), center.y(), outer.distanceTo(center) + changeRate);
    	}
    	Point2D midpoint = new Point2D((mid.x()+outer.x())/2, (mid.y()+outer.y())/2);
    	smallest = new Circle(center.x(), center.y(), mid.distanceTo(center));
    	changeRate = 0.0001;
    	double threshold = 0.0001;
    	while(allIn(smallest)==null){
    		double dX = (midpoint.x()-smallest.getX())*changeRate;
    		double dY = (midpoint.y()-smallest.getY())*changeRate;
    		//System.out.println(Double.toString(midpoint.x()-smallest.getX()) + ", "
    				//+ Double.toString(midpoint.y() - smallest.getY()));
    		if(Math.abs(midpoint.x()-smallest.getX())<threshold &&
    				Math.abs(midpoint.y()-smallest.getY())<threshold){
    			break;
    		}
    		center = new Point2D(smallest.getX()+dX, smallest.getY()+dY);
    		if(outer.distanceTo(center)>mid.distanceTo(center)){
    			smallest = new Circle(center.x(), center.y(), outer.distanceTo(center)); //+ changeRate);
    		}else{
    			smallest = new Circle(center.x(), center.y(), mid.distanceTo(center));
    		}
    	}
    	Point2D g = new Point2D(smallest.getX(), smallest.getY());
    	smallest = new Circle(smallest.getX(), smallest.getY(), g.distanceTo(getFarthestFrom(g)));
    	return(smallest);
    }
    public static Point2D allIn(Circle c){
    	//System.out.println(c);
    	for(Point2D x:points){
    		if(!c.inCircle(x)){
    			return(x);
    		}
    	}
    	return(null);
    }
    
    public ArrayList<Point2D> pointsOnCircle(Circle c){
    	ArrayList<Point2D> d = new ArrayList<Point2D>();
    	for(Point2D f:points){
    		if(c.onCircle(f)){
    			d.add(f);
    		}
    	}
    	return(d);
    }
    public static Point2D getFarthestFrom(Point2D point)
    {
    	double max = 0;
    	Point2D f = points.get(0);
    	for(Point2D x:points){
    		if(x.distanceTo(point)>max){
    			max = x.distanceTo(point);
    			f = x;
    		}
    	}
    	return(f);
    }

}

class Circle {
	private double x;
	private double y;
	private double r;
	
	public Circle(double x, double y, double r){
		this.x = x;
		this.y = y;
		this.r = r;
	}
	
	public Circle(){
		x = 0;
		y = 0;
		r = 0;
	}
	
	public boolean inCircle(Point2D p){
		Point2D c = new Point2D(x, y);
		double d = c.distanceTo(p);
		return(d<=r);
	}
	
	public boolean onCircle(Point2D p){
		Point2D c = new Point2D(x, y);
		double d = c.distanceTo(p);
		return(d==r);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getR() {
		return r;
	}

	public void setR(double r) {
		this.r = r;
	}
	
	public String toString(){
		return("[" + x + ", " + y + ", " + r + "]");
	}
}

final class Point2D implements Comparable<Point2D> 
{

    /**
     * Compares two points by x-coordinate.
     */
    public static final Comparator<Point2D> X_ORDER = new XOrder();

    /**
     * Compares two points by y-coordinate.
     */
    public static final Comparator<Point2D> Y_ORDER = new YOrder();

    /**
     * Compares two points by polar radius.
     */
    public static final Comparator<Point2D> R_ORDER = new ROrder();

    private final double x;    // x coordinate
    private final double y;    // y coordinate

    /**
     * Initializes a new point (x, y).
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @throws IllegalArgumentException if either <tt>x</tt> or <tt>y</tt>
     *    is <tt>Double.NaN</tt>, <tt>Double.POSITIVE_INFINITY</tt> or
     *    <tt>Double.NEGATIVE_INFINITY</tt>
     */
    public Point2D(double x, double y) {
        if (Double.isInfinite(x) || Double.isInfinite(y))
            throw new IllegalArgumentException("Coordinates must be finite");
        if (Double.isNaN(x) || Double.isNaN(y))
            throw new IllegalArgumentException("Coordinates cannot be NaN");
        if (x == 0.0) this.x = 0.0;  // convert -0.0 to +0.0
        else          this.x = x;

        if (y == 0.0) this.y = 0.0;  // convert -0.0 to +0.0
        else          this.y = y;
    }

    /**
     * Returns the x-coordinate.
     * @return the x-coordinate
     */
    public double x() {
        return x;
    }

    /**
     * Returns the y-coordinate.
     * @return the y-coordinate
     */
    public double y() {
        return y;
    }

    /**
     * Returns the polar radius of this point.
     * @return the polar radius of this point in polar coordiantes: sqrt(x*x + y*y)
     */
    public double r() {
        return Math.sqrt(x*x + y*y);
    }

    /**
     * Returns the angle of this point in polar coordinates.
     * @return the angle (in radians) of this point in polar coordiantes (between -pi/2 and pi/2)
     */
    public double theta() {
        return Math.atan2(y, x);
    }

    /**
     * Returns the angle between this point and that point.
     * @return the angle in radians (between -pi and pi) between this point and that point (0 if equal)
     */
    private double angleTo(Point2D that) {
        double dx = that.x - this.x;
        double dy = that.y - this.y;
        return Math.atan2(dy, dx);
    }

    /**
     * Returns true if a->b->c is a counterclockwise turn.
     * @param a first point
     * @param b second point
     * @param c third point
     * @return { -1, 0, +1 } if a->b->c is a { clockwise, collinear; counterclockwise } turn.
     */
    public static int ccw(Point2D a, Point2D b, Point2D c) {
        double area2 = (b.x-a.x)*(c.y-a.y) - (b.y-a.y)*(c.x-a.x);
        if      (area2 < 0) return -1;
        else if (area2 > 0) return +1;
        else                return  0;
    }

    /**
     * Returns twice the signed area of the triangle a-b-c.
     * @param a first point
     * @param b second point
     * @param c third point
     * @return twice the signed area of the triangle a-b-c
     */
    public static double area2(Point2D a, Point2D b, Point2D c) {
        return (b.x-a.x)*(c.y-a.y) - (b.y-a.y)*(c.x-a.x);
    }

    /**
     * Returns the Euclidean distance between this point and that point.
     * @param that the other point
     * @return the Euclidean distance between this point and that point
     */
    public double distanceTo(Point2D that) {
        double dx = this.x - that.x;
        double dy = this.y - that.y;
        return Math.sqrt(dx*dx + dy*dy);
    }

    /**
     * Returns the square of the Euclidean distance between this point and that point.
     * @param that the other point
     * @return the square of the Euclidean distance between this point and that point
     */
    public double distanceSquaredTo(Point2D that) {
        double dx = this.x - that.x;
        double dy = this.y - that.y;
        return dx*dx + dy*dy;
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point (x1, y1)
     * if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this string is equal to the argument
     *         string (precisely when <tt>equals()</tt> returns <tt>true</tt>);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point2D that) {
        if (this.y < that.y) return -1;
        if (this.y > that.y) return +1;
        if (this.x < that.x) return -1;
        if (this.x > that.x) return +1;
        return 0;
    }

    /**
     * Compares two points by polar angle (between 0 and 2pi) with respect to this point.
     *
     * @return the comparator
     */
    public Comparator<Point2D> polarOrder() {
        return new PolarOrder();
    }

    /**
     * Compares two points by atan2() angle (between -pi and pi) with respect to this point.
     *
     * @return the comparator
     */
    public Comparator<Point2D> atan2Order() {
        return new Atan2Order();
    }

    /**
     * Compares two points by distance to this point.
     *
     * @return the comparator
     */
    public Comparator<Point2D> distanceToOrder() {
        return new DistanceToOrder();
    }

    // compare points according to their x-coordinate
    private static class XOrder implements Comparator<Point2D> {
        public int compare(Point2D p, Point2D q) {
            if (p.x < q.x) return -1;
            if (p.x > q.x) return +1;
            return 0;
        }
    }

    // compare points according to their y-coordinate
    private static class YOrder implements Comparator<Point2D> {
        public int compare(Point2D p, Point2D q) {
            if (p.y < q.y) return -1;
            if (p.y > q.y) return +1;
            return 0;
        }
    }

    // compare points according to their polar radius
    private static class ROrder implements Comparator<Point2D> {
        public int compare(Point2D p, Point2D q) {
            double delta = (p.x*p.x + p.y*p.y) - (q.x*q.x + q.y*q.y);
            if (delta < 0) return -1;
            if (delta > 0) return +1;
            return 0;
        }
    }
 
    // compare other points relative to atan2 angle (bewteen -pi/2 and pi/2) they make with this Point
    private class Atan2Order implements Comparator<Point2D> {
        public int compare(Point2D q1, Point2D q2) {
            double angle1 = angleTo(q1);
            double angle2 = angleTo(q2);
            if      (angle1 < angle2) return -1;
            else if (angle1 > angle2) return +1;
            else                      return  0;
        }
    }

    // compare other points relative to polar angle (between 0 and 2pi) they make with this Point
    private class PolarOrder implements Comparator<Point2D> {
        public int compare(Point2D q1, Point2D q2) {
            double dx1 = q1.x - x;
            double dy1 = q1.y - y;
            double dx2 = q2.x - x;
            double dy2 = q2.y - y;

            if      (dy1 >= 0 && dy2 < 0) return -1;    // q1 above; q2 below
            else if (dy2 >= 0 && dy1 < 0) return +1;    // q1 below; q2 above
            else if (dy1 == 0 && dy2 == 0) {            // 3-collinear and horizontal
                if      (dx1 >= 0 && dx2 < 0) return -1;
                else if (dx2 >= 0 && dx1 < 0) return +1;
                else                          return  0;
            }
            else return -ccw(Point2D.this, q1, q2);     // both above or below

            // Note: ccw() recomputes dx1, dy1, dx2, and dy2
        }
    }

    // compare points according to their distance to this point
    private class DistanceToOrder implements Comparator<Point2D> {
        public int compare(Point2D p, Point2D q) {
            double dist1 = distanceSquaredTo(p);
            double dist2 = distanceSquaredTo(q);
            if      (dist1 < dist2) return -1;
            else if (dist1 > dist2) return +1;
            else                    return  0;
        }
    }


    /**       
     * Compares this point to the specified point.
     *       
     * @param  other the other point
     * @return <tt>true</tt> if this point equals <tt>other</tt>;
     *         <tt>false</tt> otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        Point2D that = (Point2D) other;
        return this.x == that.x && this.y == that.y;
    }

    /**
     * Return a string representation of this point.
     * @return a string representation of this point in the format (x, y)
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * Returns an integer hash code for this point.
     * @return an integer hash code for this point
     */
    @Override
    public int hashCode() {
        int hashX = ((Double) x).hashCode();
        int hashY = ((Double) y).hashCode();
        return 31*hashX + hashY;
    }
}
