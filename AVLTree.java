
public class AVLTree {
	/*
	 * AVL树为高度平衡的二叉树,即任何节点的两个子树的高度最大差为1
	 * 满足二叉树的特性
	 * 失衡:①LL:插入或删除一个节点后,根节点的左子树的左子树还有非空子节点->根的左子树高度比右子树高度高2
	 * 	   ②LR:插入或删除一个节点后,根节点的左子树的右子树还有非空子节点->根的左子树高度比右子树高度高2
	 * 	   ③RR:插入或删除一个节点后,根节点的右子树的右子树还有非空子节点->根的右子树高度比左子树高度高2
	 * 	   ④RL:插入或删除一个节点后,根节点的右子树的左子树还有非空子节点->根的右子树高度比左子树高度高2
	 * 旋转:①LL:将失衡的节点T1的左子树T2变成根节点,T1变成T2的右子树,T2的右子树变成T1的左子树
	 * 	   ②LR:先对失衡的根节点T1的左子树T2进行一次RR旋转,再对T1进行一次LL旋转
	 * 	   ③RR:将失衡的节点T1的右子树T3变成根节点,T1变成T3的左子树,T3的左子树变成T1的右子树
	 *     ④RL:先对失衡的根节点T1的右子树T3进行一次LL旋转,再对T1进行一次RR旋转
	 * */
	
	public static void main(String[] args) {
		AVLTree tree = new AVLTree();
		tree.insert(4);
		tree.insert(3);
		tree.insert(2);
		tree.insert(1);
		
	//	tree.delete(1);

		System.out.println(tree.root.left.height);
		System.out.println(tree.root.right.height);
		System.out.println(tree.root.height);
	

	
	}
	
	class Node {
		//左孩子,右孩子,高度,值
		private Node left;
		private Node right;
		private int height;
		private int key;
		
		public Node(int key, Node left, Node right) {
			this.key = key;
			this.left = left;
			this.right = right;
			this.height = 0;
		}
		
		public int getKey() {
			return key;
		}
		
		public int getHeight() {
			return height;
		}
	}
	
	private Node root;
	
	public Node getRoot() {
		return root;
	}
	
	public AVLTree() {
		root = null;
	}
	
	public void insert(int key) {
		root = insert(root, key);
	}
	
	public Node insert(Node root, int key) {
		if(root == null) {
			root = new Node(key, null, null);
		//	root.height = root
		}else {
			//如果key值小于当前节点的key值,则新增的节点应该位于当前节点的左子树
			if(key < root.key) {
				root.left = insert(root.left, key);
				//如果新增节点后当前结点的左子树和右子树的高度差为2,进行调整
			//	System.out.println(getHeight(root.left) - getHeight(root.right));
				if(getHeight(root.left) - getHeight(root.right) == 2) {
					/* 
					 * root为插入节点的父节点的父节点
					 * 如果key值小于当前结点的左孩子的key值,说明插入的节点为左孩子 --> LL情况 --> LL旋转
					 * 如果key值大于当前结点的左孩子的key值,说明插入的节点为右孩子 --> LR情况 --> LR旋转
					 * */
					if(key < root.left.key) {
						root = LLRotation(root);
					}else {
						root = LRRotation(root);
					}		
				}
			}else if(key > root.key) {
				root.right = insert(root.right, key);
				if(getHeight(root.right) - getHeight(root.left) == 2) {
					if(key > root.right.key) {
						root = RRRotation(root);
					}else {
						root = RLRotation(root);
					}
				}
			}else {
				System.out.println("error");
			}
		}
		//获取节点的高度
		root.height = Math.max(getHeight(root.left), getHeight(root.right)) + 1;
		
		return root;
		
	}
	
	public void delete(int key) {
		//查找需要被删除的节点
		Node deleteNode = search(this.root, key);
		delete(this.root, deleteNode);
	}
	
	public Node delete(Node node, Node deleteNode) {
		if(node == null || deleteNode == null) {
			return null;
		}
		//根据被删除的节点key值判断在左子树上还是右子书上,然后一步步递归进行查找并删除,删除后进行调整
		if(deleteNode.key <= node.left.key) {
			node.left = delete(node.left, deleteNode);
			if(getHeight(node.right) - getHeight(node.left) == 2) {
				Node newRoot = node.right;
				if(getHeight(newRoot.left) > getHeight(newRoot.right)) {
					node = RLRotation(node);
				}else {
					node = RRRotation(node);
				}
			}
		}else if(deleteNode.key >= node.left.key) {
			node.right = delete(node.right, deleteNode);
			if(getHeight(node.right) - getHeight(node.right) == 2) {
				Node newRoot = node.left;
				if(getHeight(newRoot.right) > getHeight(newRoot.left)) {
					node = LRRotation(node);
				}else {
					node = LLRotation(node);
				}
			}
		}else {
			if((node.left != null) && (node.right != null)) {
				/*
				 * 在被删除节点有不为空的左右孩子的情况下
				 * 如果左孩子的高度大于右孩子的高度,则找出左孩子中key值最大的点,把key值赋给删除节点,然后删除最大节点
				 * 如果左孩子的高度小于右孩子的高度,则找出右孩子中key值最大的点,把key值赋给删除节点,然后删除最大节点
				 * */
				if(getHeight(node.left) > getHeight(node.right)) {
					Node max = getMax(node.left);
					node.key = max.key;
					node.left = delete(node.left, max);
				}else {
					Node min = getMin(node.right);
					node.key = min.key;
					node.right = delete(node.right, min);
				}
			}else {
				/*
				 * 如果删除节点的右孩子为空,则直接删除节点,并把删除节点的左孩子放到被删除节点的位置
				 * 如果删除节点的左孩子为空,则直接删除节点,并把删除节点的右孩子放到被删除节点的位置
				 * */
				if(node.left != null) {
					node = node.left;
				}else {
					node = node.right;
				}
			}
		}
		
		return node;
	}
	//LL旋转
	public Node LLRotation(Node node) {
		Node root = node.left;
		node.left = root.right;
		root.right = node;
		node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
		root.height = Math.max(getHeight(root.left), getHeight(root.right)) + 1;
		
		return root;
	}
	//RR旋转
	public Node RRRotation(Node node) {
		Node root = node.right;
		node.right = root.left;
		root.left = node;
		node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
		root.height = Math.max(getHeight(root.left), getHeight(root.right)) + 1;
		
		return root;
	}
	//LR旋转
	public Node LRRotation(Node node) {
		node.left = RRRotation(node.left);
		
		return LLRotation(node);
	}
	//RL旋转
	public Node RLRotation(Node node) {
		node.right = LLRotation(node.right);
		
		return RRRotation(node);
	}
	
	public int getHeight(Node node) {
		if(node != null) {
			return node.height;
		}else {
			return 0;
		}	
	}
	//根据key值查找节点
	public void search(int key) {
		search(this.root, key);
	}
	
	public Node search(Node node, int key) {
		if(node == null) {
			return node;
		}
		/* 
		 * 如果key值小于根节点的key值,说明查找的点在根节点的左子树上,进行递归
		 * 如果key值大于根节点的key值,说明查找的点在根节点的右子树上,进行递归
		 * 如果key值等于根节点的key值,直接返回
		 * */
		if(key < node.key) {
			return search(node.left, key);
		}else if(key > node.key) {
			return search(node.right, key);
		}else {
			return node;
		}
	}
	//获取该节点下拥有最小key值的节点
	public Node getMin(Node node) {
		if(node == null) {
			return node;
		}
		while(root.left != null) {
			node = node.left;
		}
		
		return node;
	}
	//获取该节点下拥有最大key值的节点
	public Node getMax(Node node) {
		if(node == null) {
			return node;
		}
		while(node.right != null) {
			node = root.right;
		}
		
		return node;
	}
	//前序遍历
	public void preOrder(Node node) {
		if(node != null) {
			System.out.print(node.key);
			preOrder(node.left);
			preOrder(node.right);
		}
	}
	//中序遍历
	public void inOrder(Node node) {
		if(node != null) {
			preOrder(node.left);
			System.out.print(node.key);
			preOrder(node.right);
		}
	}
	//后序遍历
	public void postOrder(Node node) {
		if(node != null) {
			preOrder(node.left);
			preOrder(node.right);
			System.out.print(node.key);
		}
	}
}
