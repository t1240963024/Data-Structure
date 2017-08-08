import java.util.ArrayList;

public class ConvexHull {
	
	public static void main(String[] args) {
		ArrayList<Point> collection = new ArrayList<Point>();
		collection.add(new Point(1,2));
		collection.add(new Point(5,9));
		collection.add(new Point(7,4));
		collection.add(new Point(5,9));
		collection.add(new Point(9,6));
		collection.add(new Point(2,4));
		collection.add(new Point(4,2));
		collection.add(new Point(8,1));
		collection.add(new Point(7,7));
		collection.add(new Point(13,7));
		collection.add(new Point(9,12));
		collection.add(new Point(14,8));
		
		problemSolve(collection);
	}
	
	public static void problemSolve(ArrayList<Point> collection) {
		
		ArrayList<Point> temp = collection;
		
		double originX;
		double originY;
		
		Point tempPoint;
		//找出原点
		for(int i=0;i<temp.size()-1;i++) {
			for(int j=i+1;j<temp.size();j++) {
				if(temp.get(j).y < temp.get(i).y) {
					tempPoint = temp.get(i);
					temp.set(i, temp.get(j));
					temp.set(j, tempPoint);
				}
			}
		}
		
		//测试用
		System.out.println("原点为" + temp.get(0).x +"," + temp.get(0).y);
		
		originX = temp.get(0).x;
		originY = temp.get(0).y;
		
		//平移使原点为(0,0)
		for(int i=1;i<temp.size();i++) {
			temp.get(i).x -= temp.get(0).x;
			temp.get(i).y -= temp.get(0).y;
		}
		
		//测试用
		System.out.println("调整坐标后");
		for(int i=0;i<temp.size();i++) {
			System.out.println(temp.get(i).x + "," + temp.get(i).y);
		}
		
		temp.get(0).x = 0;
		temp.get(0).y = 0;
		
		ArrayList<Point> positive = new ArrayList<Point>();
		ArrayList<Point> negative = new ArrayList<Point>();
		ArrayList<Point> zero = new ArrayList<Point>();
		
		for(int i=1;i<temp.size();i++) {
			if(temp.get(i).x > 0) {
				positive.add(temp.get(i));
			} else if(temp.get(i).x < 0) {
				negative.add(temp.get(i));
			}else {
				zero.add(temp.get(i));
			}
		}
		//测试用
		System.out.println("调整前第一象限的坐标");
		for(int i=0;i<positive.size();i++) {
			System.out.println(positive.get(i).x + "," + positive.get(i).y);
		}
		//将第一象限的点按照角度的大小排序
		Point positiveTemp;
		for(int i=0;i<positive.size();i++) {
			for(int j=i+1;j<positive.size();j++) {
				if(positive.get(j).y/positive.get(j).x < positive.get(i).y/positive.get(i).x) {
					positiveTemp = positive.get(i);
					positive.set(i, positive.get(j));
					positive.set(j, positiveTemp);
				}
			}
		}
		//测试用
		System.out.println("调整后第一象限的坐标");
		for(int i=0;i<positive.size();i++) {
			System.out.println(positive.get(i).x + "," + positive.get(i).y);
		}
		//将第二象限的点按照角度大小排序
		Point negativeTemp;
		for(int i=0;i<negative.size();i++) {
			for(int j=i+1;j<negative.size();j++) {
				if(negative.get(j).y/negative.get(j).x < negative.get(i).y/negative.get(i).x) {
					negativeTemp = negative.get(i);
					negative.set(i, negative.get(j));
					negative.set(j, negativeTemp);
				}
			}
		}
		
		//测试用
		System.out.println("调整后第二象限的坐标");
		for(int i=0;i<negative.size();i++) {
			System.out.println(negative.get(i).x + "," + negative.get(i).y);
		}
		
		temp.clear();
		temp.add(new Point(0,0));
		for(int i=0;i<positive.size();i++) {
			temp.add(positive.get(i));
		}
		for(int i=0;i<zero.size();i++) {
			temp.add(zero.get(i));
		}
		for(int i=0;i<negative.size();i++) {
			temp.add(negative.get(i));
		}
		
		//测试用
		System.out.println("计算凸包之前的arraylist");
		for(int i=0;i<temp.size();i++) {
			System.out.println(temp.get(i).x +","+ temp.get(i).y);
		}
		
		ArrayList<Point> result = new ArrayList<Point>();
		result.add(temp.get(0));
		result.add(temp.get(1));
		for(int i=1;i+2<temp.size();i++) {
			if(temp.get(i).x != temp.get(i+1).x) {
				if(temp.get(i+2).y > judge(temp.get(i),temp.get(i+1),temp.get(i+2))) {
					result.add(temp.get(i+2));
				}
			}else {
				if(temp.get(i+2).x > temp.get(i+1).x) {
					result.add(temp.get(i+2));
				}
			} 		
		}
		//测试用
		System.out.println("最终结果");
		for(int i=0;i<result.size();i++) {
			System.out.println((result.get(i).x+originX) +" "+ (result.get(i).y + originY));
		}
	
	}
	
	public static double judge(Point a, Point b,Point c) {
		double A = b.y - a.y;
		double B = a.x - b.x;
		double C = b.x*a.y - a.x*b.y;
		return -(A*c.x + C)/B;
	}
}

class Point {
	public double x;
	public double y;
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
}
