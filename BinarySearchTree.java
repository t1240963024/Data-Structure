package algorithm;

public class BinarySearchTree<T extends Comparable<T>> {
	/*
	 * 二叉搜索树：
	 * 父节点的左子树上的所有点的值都小于父节点的值
	 * 父节点的右子树上的所有点的值都大于父节点的值
	 * 键值均不相等
	 * 任意左右子树也为二叉搜索树
	 * 
	 * 插入
	 * 1.如果根节点为空 则新插入节点为根节点
	 * 2.如果根节点不为空 则从根节点开始对比要插入的节点的键值new.value和当前节点键值current.value
	 * 3.如果新插入的节点键值比当前节点小
	 *   3.1 如果当前节点的左子树为空 则新插入节点为当前节点的左孩子
	 *   3.2 否则current=current.left 返回步骤2
	 * 4.如果新插入节点键值比当前节点大
	 *   4.1 如果当前节点的右子树为空 则新插入节点为当前节点右孩子
	 *   4.2 否则current=current.right 返回步骤2
	 * 
	 * 删除
	 * 1.如果要删除的节点既没有左孩子也没有右孩子 则直接删除
	 * 2.如果要删除的节点有左孩子没有右孩子 则其左孩子替换为被删除的节点
	 * 3.如果要删除的节点有右孩子没有左孩子 则其右孩子替换为被删除的节点
	 * 3.如果要删除的节点既有左孩子也有右孩子 则找出其后继节点 替换被删除的节点
	 * */
	@SuppressWarnings("hiding")
	class Node<T> {
		private Node<T> left;
		private Node<T> right;
		private Node<T> parent;
		private T key;
		
		public Node(T key, Node<T> left, Node<T> right, Node<T> parent) {
			this.key = key;
			this.left = left;
			this.right = right;
			this.parent = parent;
		}
	}
	
	private Node<T> mRoot;
	public BinarySearchTree() {
		
	}
	
	public void add(T item) {
		// TODO Auto-generated method stub
		Node<T> newNode = new Node<T>(item, null, null, null);
		add(this,newNode);
	}
	
	public void add(BinarySearchTree<T> bstMultiset, Node<T> newNode) {
		Node<T> currentNode = bstMultiset.mRoot;
		//如果根节点为空
		if(currentNode == null) {
			bstMultiset.mRoot = newNode;
		}
		//根节点不为空
		while(currentNode != null) {
			if(currentNode.key.compareTo(newNode.key) > 0) {//放到左子树
				if(currentNode.left == null) {
					currentNode.left = newNode;
					newNode.parent = currentNode;
					break;
				}else {
					currentNode = currentNode.left;
				}
			}else if(currentNode.key.compareTo(newNode.key) < 0){	//放到右子树
				if(currentNode.right == null) {
					currentNode.right = newNode;
					newNode.parent = currentNode;
					break;
				}else {
					currentNode = currentNode.right;
				}
			}else {//数据重复则报错
				System.err.println("no duplicate value");
			}
		}		
	}
	//前驱 返回前驱结点 即该节点左子书中最大的节点
	public Node<T> predecessor(Node<T> node) {
		if(node.left != null) {
			Node<T> temp = node.left;
			while(temp.right != null) {
				temp = temp.right;
			}
			return temp;
		}
		
		Node<T> parent = node.parent;
		while((parent != null) && (node == parent.left)) {
			node = parent;
			parent = parent.parent;
		}
		
		return parent;
	}
	//后继 返回后继节点 即该节点右子树中最小的节点
	public Node<T> successor(Node<T> node) {
		if(node.right != null) {
			Node<T> temp = node.right;
			while(temp.left != null) {
				temp = temp.left;
			}
			return temp;
		}
		
		Node<T> parent = node.parent;
		while((parent != null) && (node == parent.right)) {
			node = parent;
			parent = parent.parent;
		}
		
		return parent;
		
	}
	//查找结点 返回找到的节点
	public Node<T> search(Node<T> node, T value) {
		if(node == null) {
			return node;
		}
		if(node.key.compareTo(value) == 0) {
			return node;
		}else if(node.key.compareTo(value) > 0) {
			return search(node.left, value);
		}else {
			return search(node.right, value);
		}
		
	}
	//删除节点 内部调用
	private void delete(Node<T> node) {
		if(node == null) {
			return;
		}
		// 被删除的节点没有左孩子和右孩子
		if(node.left == null && node.right == null) {
			System.out.println(node.parent.key);
			if(node.parent.left == node) {
				node.parent.left = null;
			}else {
				node.parent.right = null;
			}
			return;
		}
		//如果被删除的节点的左孩子为空 右孩子不为空
		if(node.left == null && node.right != null) {		
			if(node.parent.left == node) {
				node.parent.left = node.right;
				node.right.parent = node.parent;
			}else {//如果右孩子为空
				node.parent.right = node.right;
				node.right.parent = node.parent;
			}
			return;
		}
		//如果被删除的节点的左孩子不为空 右孩子为空
		if(node.left != null && node.right == null) {
			if(node.parent.left == node) {
				node.parent.left = node.left;
				node.left.parent = node.parent;
			}else {
				node.parent.right = node.left;
				node.left.parent = node.parent;
			}
			return;
		}
		
		Node<T> succNode = successor(node);
		delete(succNode);
		node.key = succNode.key;
	}
	//删除节点 外部调用
	public void delete(T item) {
		// TODO Auto-generated method stub
		Node<T> node = search(mRoot, item);
		if(node == null) {
			System.err.println("no such node");
		}else {
			delete(node);
		}
	}
	//后序遍历
	public void postOrder(Node<T> node) {
		if(node != null) {
			System.out.println(node.key);
			postOrder(node.left);
			postOrder(node.right);
		}
	}
	//中序遍历
	public void inOrder(Node<T> node) {
		if(node != null) {
			inOrder(node.left);
			System.out.println(node.key);
			inOrder(node.right);
		}
	}
	//前序遍历
	public void preOrder(Node<T> node) {
		if(node != null) {
			System.out.println(node.key);
			preOrder(node.left);
			preOrder(node.right);
		}
	}
	
	public void print() {
		// TODO Auto-generated method stub
		preOrder(mRoot);
	}
}