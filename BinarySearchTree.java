package algorithm;

public class BinarySearchTree<T extends Comparable<T>> {
	
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
			//放到左子树
			if(currentNode.key.compareTo(newNode.key) > 0) {
				if(currentNode.left == null) {
					currentNode.left = newNode;
					newNode.parent = currentNode;
					break;
				}else {
					currentNode = currentNode.left;
				}
			}else {	//放到右子树
				if(currentNode.right == null) {
					currentNode.right = newNode;
					newNode.parent = currentNode;
					break;
				}else {
					currentNode = currentNode.right;
				}
			}		
		}		
	}

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
	
	public void delete(Node<T> node) {
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
		
	public void delete(T item) {
		// TODO Auto-generated method stub
		Node<T> node = search(mRoot, item);
		if(node == null) {
			System.err.println("no such node");
		}else {
			delete(node);
		}
	}
	
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
