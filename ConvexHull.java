import java.util.ArrayList;

public class ConvexHull {
	/*
	 * Graham�㷨
	 * 1.���ҳ���������������С�ĵ�(a,b) ����Ϊԭ��(0,0) Ȼ���������е㶼����ƽ�� e.g. (x-a,y-b) 
	 * 2.���������갴��б�ʴ�С���� �����е�һλc1�͵ڶ�λc2��Ϊ͹���ϵĵ� 
	 * 3.for i=0 i+2<����size ��c(i) c(i+1) c(i+2)����result�� Ȼ���ж�c(i)c(i+1)���γɵ�ֱ�ߵ�б����c(i+1)c(i+2)���γɵ�б�ʵĴ�С 
	 * 4.�������б�ʴ���ǰ��б�� �ص�������
	 * 5.�������б��С��ǰ��б�� �Ƴ�result�����һλ��c(i+2) �ص�������
	 * */
	public static void main(String[] args) {

		ArrayList<Point> collection = new ArrayList<Point>();
		
		collection.add(new Point(1,2));
		collection.add(new Point(5,9));
		collection.add(new Point(7,4));
		collection.add(new Point(9,12));
		collection.add(new Point(14,8));
		collection.add(new Point(17,10));
		
		problemSolve(collection);
	}

	public static void problemSolve(ArrayList<Point> collection) {

		ArrayList<Point> temp = collection;

		double originX;
		double originY;

		Point tempPoint;
		//�ҳ�ԭ��
		for(int i=0;i<temp.size()-1;i++) {
			for(int j=i+1;j<temp.size();j++) {
				if(temp.get(j).y < temp.get(i).y) {
					tempPoint = temp.get(i);
					temp.set(i, temp.get(j));
					temp.set(j, tempPoint);
				}
			}
		}
		//������
		System.out.println("ԭ��Ϊ" + temp.get(0).x +"," + temp.get(0).y);
		
		originX = temp.get(0).x;
		originY = temp.get(0).y;	

		//ƽ��ʹԭ��Ϊ(0,0)
		for(int i=1;i<temp.size();i++) {
			temp.get(i).x -= temp.get(0).x;
			temp.get(i).y -= temp.get(0).y;
		}

		//������
		System.out.println("���������");
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
		
		//������
		System.out.println("����ǰ��һ���޵�����");
		for(int i=0;i<positive.size();i++) {
			System.out.println(positive.get(i).x + "," + positive.get(i).y);
		}
		
		//����һ���޵ĵ㰴�սǶȵĴ�С����
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

		//������
		System.out.println("�������һ���޵�����");
		for(int i=0;i<positive.size();i++) {
			System.out.println(positive.get(i).x + "," + positive.get(i).y);
		}
		
		//���ڶ����޵ĵ㰴�սǶȴ�С����
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
		
		//������
		System.out.println("������ڶ����޵�����");
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

		//������
		System.out.println("����͹��֮ǰ��arraylist");
		for(int i=0;i<temp.size();i++) {
			System.out.println((temp.get(i).x + originX) +","+ (temp.get(i).y + originY));
		}

		ArrayList<Point> result = new ArrayList<Point>();
		result.add(temp.get(0));
		result.add(temp.get(1));
		for(int i=0;i+2<temp.size();i++) {
			result.add(temp.get(i+2));
			if(judge(temp.get(i),temp.get(i+1)) > judge(temp.get(i+1),temp.get(i+2))) {
				result.remove(result.size() - 1);
			}
		}
		
		//������
		System.out.println("���ս��");
		for(int i=0;i<result.size();i++) {
			System.out.println((result.get(i).x+originX) +" "+ (result.get(i).y + originY));
		}
	}
	//�����γɵ�ֱ�ߵ�б��
	public static double judge(Point a, Point b) {
		return (b.y - a.y) / (b.x - a.x);
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