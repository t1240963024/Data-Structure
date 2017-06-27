import java.util.ArrayList;
import java.util.List;

public class MaxBinaryTree<T extends Comparable<T>> {

    private List<T> mHeap;    // 队列(实际上是动态数组ArrayList的实例)

    public MaxBinaryTree() {
        this.mHeap = new ArrayList<T>();
    }
    // 最大堆的向下调整算法
    protected void filterdown(int start, int end) {
        int delete = start;          // 被删除数据的位置
        int left = 2*delete + 1;     // 需要删除节点的左孩子的位置
        T lastData = mHeap.get(delete);    // 填充进来的元素大小(即数组最后一位的大小)
        int clr;
        while(left <= end) {//整个while()循环用来保证调整完以后该二叉堆仍然是最大堆
        	clr = mHeap.get(left).compareTo(mHeap.get(left+1)); //被删除数据的左孩子和右孩子大小比较  如果l>l+1 返回1 如果小于 返回-1
            if(left < end && clr<0) //如果左孩子不是数组最后一位 &&cmp等于-1即被删除数据的左孩子比右孩子小
            	left++;        // 左右两孩子中选择较大者
            clr = lastData.compareTo(mHeap.get(left)); //比较填充进来的元素大小和其左孩子和右孩子中较大的一个 如果填充进来的元素大或等于 返回1 否则返回-1
            if(clr >= 0) //如果填充进来的元素的大于等于左孩子右孩子中较大的一个
                break;        //调整结束
            else {//如果填充进来的元素大小比左孩子右孩子中较大的小
                mHeap.set(delete, mHeap.get(left));//把被删除数据的位置的大小调整为左右孩子中较大的一个
                delete = left;	// 被删除数据的位置调整为左孩子与右孩子中较大节点的位置
                left = 2*left + 1;   //l调整为它的左孩子的位置
            }       
        }   
        mHeap.set(delete, lastData); //调整被删除数据位置的大小为填充进来的元素大小(如果运行while(),即将调整完后当前节点的大小设置为填充进来的元素大小)
    }
    //删除数据
    public int remove(T data) {
        // 如果堆为空，则返回-1
        if(mHeap.isEmpty())
            return -1;

        // 获取data在数组中的索引
        int index = mHeap.indexOf(data);
        if (index==-1){	//如果需要删除的数据不存在 返回-1
            return -1;
        }
        if(index == (mHeap.size()-1)){ //如果删除的数据正好是最后一位 删除后return 0
        	mHeap.remove(index);
        	return 0;
        }
        int size = mHeap.size();
        mHeap.set(index, mHeap.get(size-1));// 将最后一位元素填到被删除元素的位置
        mHeap.remove(size - 1);// 删除最后的元素

        if (mHeap.size() > 1){
            filterdown(index, mHeap.size()-1);    // 从index号位置开始自上向下调整为最小堆
        }
        return 0;
    }

    //最大堆的向上调整
    protected void filterup() {
        int current = mHeap.size()-1;            // 当前节点的位置(即整个数组的最后一位)
        int parent = (current-1)/2;        // 父(parent)结点的位置 
        T cData = mHeap.get(current);        // 当前节点(current)的大小
        int cPC;
        while(current > 0) {//整个while()循环用来保证调整完以后该二叉堆仍然是最大堆
        	cPC = mHeap.get(parent).compareTo(cData); //如果父节点大于等于当前结点 返回1 如果父节点小于当前节点 返回-1
            if(cPC >= 0)//如果父节点的值大于等于当前结点的值
                break;
            else {//如果父节点的值小于当前结点的值
                mHeap.set(current, mHeap.get(parent)); //把当前结点的值换成父节点的值
                current = parent;//当前结点的位置设置为父节点的位置
                parent = (parent-1)/2;  //父节点的位置设置为当前父节点的父节点的位置
            }       
        }
        mHeap.set(current, cData);//把当前结点的值换成当前结点的大小(如果运行wihie() 即把当前结点的大小换成插入的数据)
    }
      
    //插入数据
    public void insert(T data) {
        mHeap.add(data);    //添加数据
        filterup();     // 向上调整堆
    }
 
    public static void main(String[] args) {
        int a[] = {32,56,75,15,34,73,89,67,83,25};
        MaxBinaryTree<Integer> tree=new MaxBinaryTree<Integer>();

        for(int i=0; i<a.length; i++) {
            tree.insert(a[i]);
        }
        for(int i=0; i<a.length; i++) {
            System.out.print(tree.mHeap.get(i)+" ");
        }
        System.out.println("\n");
        tree.remove(75);
        for(int i=0; i<tree.mHeap.size(); i++) {
            System.out.print(tree.mHeap.get(i)+" ");
        }
    }
}
