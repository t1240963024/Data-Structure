
public class BinarySearchTree {
	
	public static void main(String[] args) {
		BinarySearchTree tree = new BinarySearchTree();
		tree.insert(50);
		tree.insert(64);
		tree.insert(14);
		tree.insert(44);
		tree.insert(24);
		tree.delete(14);
	}
	
	/*
	 * 二叉查找树
	 * 左子树上任意节点的值均小于其父节点
	 * 右子数上任意节点的值均大于其父节点
	 * 没有值相等的节点
	 **/
	
	/*
	 * Node:二叉树的节点,包括左孩子,右孩子,父节点和值
	 * 
	 * */
	class Node {
		private Node left;
		private Node right;
		private Node parent;
		private int key;
		
		public Node(int key,Node left,Node right,Node parent) {
			this.key = key;
			this.left = left;
			this.right = right;
			this.parent = parent;
		}
		
		public int getKey() {
			return key;
		}
		
		public void setKey(int key) {
			this.key = key;
		}
	}
	
	private Node node;
	
	public BinarySearchTree() {
		node = null;
	}
	
	public void insert(int key) {
		Node newNode = new Node(key, null, null, null);
		insert(this,newNode);
	}
	
	public void insert(BinarySearchTree tree, Node node) {
		Node currentNode = tree.node;
		
		while(currentNode != null) {
			if(currentNode.getKey() > node.getKey()) {
				if(currentNode.left == null) {
					currentNode.left = node;
					break;
				}else {
					currentNode = currentNode.left;
				}
			}
			if(currentNode.getKey() < node.getKey()) {
				if(currentNode.right == null) {
					currentNode.right = node;
					break;
				}else {
					currentNode = currentNode.right;
				}
			}

		}
	}
	//返回以node为根的二叉树的最小节点
	public Node minNode(Node node) {
		if(node == null) {
			return null;
		}
		while(node.left != null) {
			node = node.left;
		}
		
		return node;
	}
	//返回以node为根的二叉树的最大节点
	public Node maxNode(Node node) {
		
		if(node == null) {
			return null;
		}
		while(node.right != null) {
			node = node.right;
		}
		
		return node;
	}
	
	public Node predecessor(Node node) {
		/* 查找node节点的前驱节点,即小于该节点的最大节点
		 * 如果node有左子树,则node的前驱结点为其左子树的最大节点
		 * 如果node没有左子树
		 * 	  如果node为一个右孩子,则node的前驱结点为其父节点
		 * 	 如果node为一个左孩子,则node的前驱结点为其拥有右孩子的最低的父节点
		 * */
		if(node.left != null) {
			return maxNode(node.left);
		}
		Node parent = node.parent;
		if(node.getKey() > parent.getKey()) {
			return parent;
		}else {
			while(parent.right == null) {
				parent = parent.parent;
			}
			return parent;
		}
	}

	public Node successor(Node node) {
		/* 查找node节点的后继节点,即大于该节点的最小节点
		 * 如果node有右子树,则node的后继结点为其右子树的最小节点
		 * 如果node没有右子树
		 * 	  如果node为一个左孩子,则node的后继结点为其父节点
		 * 	  如果node为一个右孩子,则node的后继结点为其拥有左孩子的最低的父节点
		 * */
		if(node.right != null) {
			return minNode(node.right);
		}
		Node parent = node.parent;
		if(node.getKey() < parent.getKey()) {
			return parent;
		}else {
			while(parent.left == null) {
				parent = parent.parent;
			}
			return parent;
		}
	}
	
	public Node search(int key) {
		
		return search(this.node, key);
	}
	
	public Node search(Node node, int key) {
		if(node == null) {
			return node;
		}
		if(node.getKey() == key) {
			return node;
		}else if(node.getKey() > key) {
			return search(node.left, key);
		}else {
			return search(node.right, key);
		}
	}
	
	public void delete(int key) {
		Node node = search(this.node, key);
		if(node != null) {
			delete(this, node);
		}
	}
	//node:需要被删除的节点
	public void delete(BinarySearchTree tree, Node node) {
		
		Node temp;
		// 被删除的节点没有子节点
		if(node.left == null && node.right == null) {
			node = null;
		}else {
			temp = successor(node);
		}
		//被删除的节点只有一个子节点
		if(node.left == null || node.right == null) {
			if(node.left == null) {
				node = node.right;
			}else {
				node = node.left;
			}
		}
		//被删除的节点有两个子节点
		temp = successor(node);
		node.setKey(temp.getKey());
		if(temp.right == null) {
			temp = null;
		}else {
			temp = temp.right;
			
		}
		
		
	}
}

