



public class Solution {
    public static void main(String[] args) {
        CustomTree list = new CustomTree();

        for (int i = 1; i < 130; i++) {
            list.add(String.valueOf(i));
        }
        System.out.println(list.size());
        System.out.println(list.getParent("129"));
    }
}






























