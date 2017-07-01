import java.util.ArrayList;

public class ConvexHull {
	
	public static void main(String[] args) {
		ArrayList<Point> collection = new ArrayList<Point>();
		collection.add(new Point(1,2));
		collection.add(new Point(5,1));
		collection.add(new Point(7,4));
		collection.add(new Point(5,9));
		collection.add(new Point(9,6));
		collection.add(new Point(2,4));
		collection.add(new Point(4,1));
		collection.add(new Point(8,1));
		collection.add(new Point(7,7));
		
		problemSolve(collection);
	}
	
	public static void problemSolve(ArrayList<Point> collection) {
		int temp = collection.get(0).x;
		int order = 0;
		for(int i=1;i<collection.size();i++) {
			if(temp > collection.get(i).x) {
				temp = collection.get(i).x;
				order = i;
			}
		}
		int changeX = collection.get(order).x - 0;
		int changeY = collection.get(order).y - 0;
		for(int i=0;i<collection.size();i++) {
			if(i != order) {
				collection.get(i).x = collection.get(i).x - changeX;
				collection.get(i).y = collection.get(i).y - changeY;
			}	
		}
		if(order != 0) {
			Point tempL = collection.get(0);
			collection.set(0, collection.get(order));
			collection.set(order, tempL);
		}
		Point inter;
		for(int i=1;i<collection.size();i++) {
			for(int j=i+1;j<collection.size();j++) {
				if(collection.get(j).x >= collection.get(i).x) {
					inter = collection.get(i);
					collection.set(i, collection.get(j));
					collection.set(j, inter);
				}
			}
		}
		for(int i=1;i<collection.size();i++) {
			for(int j=i+1;j<collection.size();j++) {
				if(collection.get(i).x > 0 && collection.get(j).x > 0) {		
					if(sin(collection.get(i)) > sin(collection.get(j))) {
						inter = collection.get(i);
						collection.set(i, collection.get(j));
						collection.set(j, inter);
					}
				}
				if(collection.get(i).x < 0 && collection.get(j).x < 0) {
					if(sin(collection.get(i)) < sin(collection.get(j))) {
						inter = collection.get(i);
						collection.set(i, collection.get(j));
						collection.set(j, inter);
					}
				}		
			}			
		}
		ArrayList<Point> result = new ArrayList<Point>();
		result.add(collection.get(0));
		result.add(collection.get(1));
		for(int i=1;i+2<collection.size();i++) {
			if(collection.get(i).x != collection.get(i+1).x) {
				if(collection.get(i+2).y > judge(collection.get(i),collection.get(i+1),collection.get(i+2))) {
					result.add(collection.get(i+2));
				}
			}else {
				if(collection.get(i+2).x > collection.get(i+1).x) {
					result.add(collection.get(i+2));
				}
			} 		
		}
		for(int i=0;i<result.size();i++) {
			System.out.println(result.get(i).x+" "+result.get(i).y);
		}
		
	}
	public static int judge(Point a, Point b,Point c) {
		int A = b.y - a.y;
		int B = a.x - b.x;
		int C = b.x*a.y - a.x*b.y;
		return -(A*c.x + C)/B;
	}
	
	public static double sin(Point point) {
		return point.y/Math.sqrt(point.x*point.x + point.y*point.y);
	}
	 
	
}

class Point {
	
	public int x;
	public int y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
