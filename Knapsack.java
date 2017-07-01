import java.util.ArrayList;

public class Knapsack {
	/*0-1背包问题(每种物品只能选择0或1个)
	 * f[i,j]:i件物品中选择若干见放入容量为J的包中所能获得的最大价值
	 * W(i):第i件物品的重量
	 * V(i):第i件物品的价值
	 * 状态转换方程:f[i,j] = max{f[i-1,j-W(i)]+V(i),f[i-1,j]}
	 * i件物品中选择若干放入容量为j的包中所能获得的最大价值 = 
	 * 最大值{前i-1件物品中选择若干放入容量为j-W(i)的包中所能获得的最大价值+第i件物品的价值,前i-1件物品中选择若干放入容量为j的包中所能获得的最大价值}
	 * */
	public static void main(String[] args) {
		
		ArrayList<Fruit> fruit = new ArrayList<Fruit>();
		
		fruit.add(new Fruit(10,2,"苹果"));
		fruit.add(new Fruit(19,3,"香蕉"));
		fruit.add(new Fruit(27,5,"梨"));
		fruit.add(new Fruit(21,4,"橙子"));
		fruit.add(new Fruit(37,6,"西瓜"));
		//j
		int bagWeight = 10;
		//求最优解 非递归
		deal(fruit,bagWeight);
		
	}
	
	public static void deal(ArrayList<Fruit> fruit, int bagWeight) {
		/*
		 * 将最优解设为一个二维数组
		 * 初始化最优价
		 * */
		int[][] f = new int[fruit.size()+1][bagWeight+1];
		
		for(int i=0;i<fruit.size()+1;i++) {
			f[i][0] = 0;
		}
		for(int i=0;i<bagWeight+1;i++) {
			f[0][i] = 0;
		}
		
		for(int i=1;i<fruit.size()+1;i++) {
			for(int j=1;j<bagWeight+1;j++) {
				/* 
				 * f[1,1] 第一个水果放入重量为1的包中 因为水果的重量大于1 所以f[1,1]=f[0,1]=0
				 * f[1,2] 第一个水果放入重量为2的包中 因为水果的重量等于2且f[0,1]<f[0,0]+10 所以f[1,2]=f[0,0]+10=10
				 * 
				 * 如果第i-1个物品的重量小于等于最大重量
				 *   如果i-1物品放入j的最大价值小于i-1物品放入j-(第i-1物品重量)的最大价值+第i-1物品的价值
				 *     i物品放入j的最大价值=i-1物品放入j-(第i-1物品重量)的最大价值+第i-1物品的价值
				 *     否则i物品放入j的最大价值=i-1物品放入j的最大价值
				 * 否则i物品放入j的最大价值=i-1物品放入j的最大价值
				 * */
				if(fruit.get(i-1).getWeight() <= j) {
					if(f[i-1][j] < f[i-1][j-fruit.get(i-1).getWeight()]+fruit.get(i-1).getValue()) {
						f[i][j] = f[i-1][j-fruit.get(i-1).getWeight()] + fruit.get(i-1).getValue();
					} else {
						f[i][j] = f[i-1][j];
					}
				} else {
					f[i][j] = f[i-1][j];
				}
			}
		}

		System.out.println(f[fruit.size()][bagWeight]);
	}
	
}

class Fruit {
	private int value;
	private int weight;
	private String name;
	
	public Fruit(int value,int weight,String name) {
		this.value = value;
		this.weight = weight;
		this.name = name;
	}
	
	public int getValue() {
		return value;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public String getName() {
		return name;
	}
}