package algorithm;

public class BinarySearchTree<T extends Comparable<T>> {
	/*
	 * ������������
	 * ���ڵ���������ϵ����е��ֵ��С�ڸ��ڵ��ֵ
	 * ���ڵ���������ϵ����е��ֵ�����ڸ��ڵ��ֵ
	 * ��ֵ�������
	 * ������������ҲΪ����������
	 * 
	 * ����
	 * 1.������ڵ�Ϊ�� ���²���ڵ�Ϊ���ڵ�
	 * 2.������ڵ㲻Ϊ�� ��Ӹ��ڵ㿪ʼ�Ա�Ҫ����Ľڵ�ļ�ֵnew.value�͵�ǰ�ڵ��ֵcurrent.value
	 * 3.����²���Ľڵ��ֵ�ȵ�ǰ�ڵ�С
	 *   3.1 �����ǰ�ڵ��������Ϊ�� ���²���ڵ�Ϊ��ǰ�ڵ������
	 *   3.2 ����current=current.left ���ز���2
	 * 4.����²���ڵ��ֵ�ȵ�ǰ�ڵ��
	 *   4.1 �����ǰ�ڵ��������Ϊ�� ���²���ڵ�Ϊ��ǰ�ڵ��Һ���
	 *   4.2 ����current=current.right ���ز���2
	 * 
	 * ɾ��
	 * 1.���Ҫɾ���Ľڵ��û������Ҳû���Һ��� ��ֱ��ɾ��
	 * 2.���Ҫɾ���Ľڵ�������û���Һ��� ���������滻Ϊ��ɾ���Ľڵ�
	 * 3.���Ҫɾ���Ľڵ����Һ���û������ �����Һ����滻Ϊ��ɾ���Ľڵ�
	 * 3.���Ҫɾ���Ľڵ��������Ҳ���Һ��� ���ҳ����̽ڵ� �滻��ɾ���Ľڵ�
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
		//������ڵ�Ϊ��
		if(currentNode == null) {
			bstMultiset.mRoot = newNode;
		}
		//���ڵ㲻Ϊ��
		while(currentNode != null) {
			if(currentNode.key.compareTo(newNode.key) > 0) {//�ŵ�������
				if(currentNode.left == null) {
					currentNode.left = newNode;
					newNode.parent = currentNode;
					break;
				}else {
					currentNode = currentNode.left;
				}
			}else if(currentNode.key.compareTo(newNode.key) < 0){	//�ŵ�������
				if(currentNode.right == null) {
					currentNode.right = newNode;
					newNode.parent = currentNode;
					break;
				}else {
					currentNode = currentNode.right;
				}
			}else {//�����ظ��򱨴�
				System.err.println("no duplicate value");
			}
		}		
	}
	//ǰ�� ����ǰ����� ���ýڵ������������Ľڵ�
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
	//��� ���غ�̽ڵ� ���ýڵ�����������С�Ľڵ�
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
	//���ҽ�� �����ҵ��Ľڵ�
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
	//ɾ���ڵ� �ڲ�����
	private void delete(Node<T> node) {
		if(node == null) {
			return;
		}
		// ��ɾ���Ľڵ�û�����Ӻ��Һ���
		if(node.left == null && node.right == null) {
			if(node.parent == null) {
				mRoot = null;
			}else if(node.parent.left == node) {
				node.parent.left = null;
			}else {
				node.parent.right = null;
			}
			return;
		}
		//�����ɾ���Ľڵ������Ϊ�� �Һ��Ӳ�Ϊ��
		if(node.left == null && node.right != null) {
			if(node.parent == null) {
				mRoot = mRoot.right;
				mRoot.parent = null;
			}else if(node.parent.left == node) {
				node.parent.left = node.right;
				node.right.parent = node.parent;
			}else {
				node.parent.right = node.right;
				node.right.parent = node.parent;
			}
			return;
		}
		//�����ɾ���Ľڵ�����Ӳ�Ϊ�� �Һ���Ϊ��
		if(node.left != null && node.right == null) {
			if(node.parent == null) {
				mRoot = mRoot.left;
				mRoot.parent = null;
			}else if(node.parent.left == node) {
				node.parent.left = node.left;
				node.left.parent = node.parent;
			}else {
				node.parent.right = node.left;
				node.left.parent = node.parent;
			}
			return;
		}
		Node<T> temp;
		if(node.parent == null) {
			temp = predecessor(node);
			mRoot.key = temp.key;
		}else {
			temp = successor(node);
			node.key = temp.key;
		}
			
		delete(temp);
	}
	//ɾ���ڵ� �ⲿ����
	public void delete(T item) {
		// TODO Auto-generated method stub
		Node<T> node = search(mRoot, item);
		if(node == null) {
			System.err.println("no such node");
		}else {
			delete(node);
		}
	}
	//�������
	public void postOrder(Node<T> node) {
		if(node != null) {
			System.out.println(node.key);
			postOrder(node.left);
			postOrder(node.right);
		}
	}
	//�������
	public void inOrder(Node<T> node) {
		if(node != null) {
			inOrder(node.left);
			System.out.println(node.key);
			inOrder(node.right);
		}
	}
	//ǰ�����
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