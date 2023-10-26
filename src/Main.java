import java.util.*;

public class Main {

  public static class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {}

    TreeNode(int val) {
      this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
      this.val = val;
      this.left = left;
      this.right = right;
    }
  }
  public static TreeNode invertTree(TreeNode root) {
    TreeNode newHead = new TreeNode(root.val);
    Queue<TreeNode> nodesToTraverse = new ArrayDeque<>();
    nodesToTraverse.add(newHead);
    nodesToTraverse.add(root.right);
    nodesToTraverse.add(root.left);


    while (!nodesToTraverse.isEmpty()) {
      TreeNode currentNode = nodesToTraverse.poll();
      TreeNode nodeToAdd = nodesToTraverse.poll();
      currentNode.left = nodeToAdd;
      if (nodeToAdd.right != null ) {
        
      }
      nodeToAdd = nodesToTraverse.poll();
      currentNode.right = nodeToAdd;
    }


    return null;
  }

  public static TreeNode invertTree2(TreeNode root, TreeNode newRoot) {


    newRoot.right = invertTree2(root.left, new TreeNode(root.left.val));
    newRoot.left = invertTree2(root.right, new TreeNode(root.right.val));

    return newRoot;
  }

  public static TreeNode constructTree(int array [], int i) {

    if (i >= array.length) {
      return null;
    }

    return new TreeNode(array[i], constructTree(array, 2 * i + 1), constructTree(array, 2 * i + 2));

  }

  public static void main(String[] args) {
    final TreeNode treeNode = constructTree(new int[]{4, 2, 7, 1, 3, 6, 9}, 0);
    final TreeNode treeNode1 = invertTree2(treeNode, new TreeNode(treeNode.val));
    printTree(treeNode1);
  }

  private static void printTree(final TreeNode treeNode1) {
    Queue<TreeNode> nodesToPrint = new ArrayDeque<>();
    nodesToPrint.add(treeNode1);

    while(!nodesToPrint.isEmpty()) {
      final TreeNode currentNode = nodesToPrint.poll();
      System.out.println(currentNode.val);
      if (currentNode.left != null) {
        nodesToPrint.add(currentNode.left);
      }
      if (currentNode.right != null) {
        nodesToPrint.add(currentNode.right);
      }
    }
  }

}
