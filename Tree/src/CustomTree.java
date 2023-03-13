

import java.io.Serializable;
import java.util.*;

public class CustomTree extends AbstractList<String> implements Cloneable, Serializable{
    Entry<String> root;
    ArrayList<Entry<String>> queue = new ArrayList<>();
    ArrayList<Entry<String>> tree = new ArrayList<>();


    public CustomTree() {
        this.root = new Entry<>("0");
        queue.add(root);
    }

    @Override
    public boolean add(String s) {

        while(!queue.isEmpty()){
            Entry<String> str = queue.get(0);
            if(str.availableToAddLeftChildren) {
                str.leftChild = new Entry<>(s);
                str.leftChild.parent = str;
                str.availableToAddLeftChildren = false;
                queue.add(str.leftChild);

                return true;
            }
            else if(str.availableToAddRightChildren){
                str.rightChild = new Entry<>(s);
                str.availableToAddRightChildren = false;
                str.rightChild.parent = str;

                queue.add(str.rightChild);

                return true;
            }
            else{
                queue.remove(str);
            }
        }
        if(activateParent(root)) {
            add(s);
            return true;
        }
        else return false;
    }
    private boolean activateParent(Entry<String> root){
        if(root!=null){
            if(root.leftChild == null && !root.availableToAddLeftChildren) {
                root.availableToAddLeftChildren = true;
                if(!queue.contains(root)) queue.add(root);
            }
            else if(root.leftChild != null) activateParent(root.leftChild);
            if(root.rightChild == null && !root.availableToAddRightChildren) {
                root.availableToAddRightChildren = true;
                if(!queue.contains(root)) queue.add(root);
            }
            else if(root.rightChild != null) activateParent(root.rightChild);
            return true;
        }
        else return false;
    }
    public String getParent(String s){
       return root.getParent(s);
    }

    @Override
    public String get(int index) {
        throw new UnsupportedOperationException();
    }
    @Override
    public String set(int index,String element) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void add(int index, String element){
        throw new UnsupportedOperationException();
    }

    @Override
    public String remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends String> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        tree.clear();
        countSize(root);
        return tree.size()-1;
    }
    private void countSize(Entry<String> entry){

        if(entry != null){
            if(!tree.contains(entry)) tree.add(entry);
            countSize(entry.leftChild);
            countSize(entry.rightChild);
        }
    }

    @Override
    public CustomTree clone() {
        try {
            return (CustomTree) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    static class Entry<T> implements Serializable{
        String elementName;
        boolean availableToAddLeftChildren;
        boolean availableToAddRightChildren;
        Entry<T> parent,leftChild,rightChild;


        public Entry(String elementName) {
            this.elementName = elementName;
            availableToAddRightChildren =true;
            availableToAddLeftChildren = true;
        }
        public Entry<T> findElem(String s){
            Entry<T> str;
            if(s.equals(elementName)) str = this;
            else{
                if(leftChild==null && rightChild == null){
                    return null;
                }
                else if(leftChild == null){
                    str = rightChild.findElem(s);
                }
                else if(rightChild==null){
                    str = leftChild.findElem(s);
                }
                else{
                    str = leftChild.findElem(s);
                    if(str == null) str = rightChild.findElem(s);
                }
            }
            return str;
        }

        public String getParent(String s){
            String str;
            if(s.equals(elementName)) str = parent.elementName;
            else{
                if(leftChild==null && rightChild == null){
                    return null;
                }
                else if(leftChild == null){
                    str = rightChild.getParent(s);
                }
                else if(rightChild==null){
                    str = leftChild.getParent(s);
                }
                else{
                    str = leftChild.getParent(s);
                    if(str == null) str = rightChild.getParent(s);
                }
            }
            return str;
        }

    }
    public boolean remove(Object o){
        if(o instanceof String str){
            Entry<String> entry;
            if((entry = root.findElem(str))!=null){
                if(entry.parent != null){
                    if(!queue.contains(entry)) {
                        clearQueue(entry);
                        setParent(entry,entry.parent);
                        queue.add(queue.size()-1,entry.parent);
                        entry.parent = null;
                    }
                    else{
                        clearQueue(entry);
                        setParent(entry,entry.parent);
                        queue.set(queue.indexOf(entry),entry.parent);
                        entry.parent = null;

                    }
                }
                else {
                    queue.clear();
                    root = null;
                }
            }
            return true;
        }
        else throw new UnsupportedOperationException();
    }
    private void clearQueue(Entry<String> entry){
        queue.remove(entry);
        if(entry.leftChild != null) clearQueue(entry.leftChild);
        if(entry.rightChild != null) clearQueue(entry.rightChild);
    }

    private void setParent(Entry<String> entry, Entry<String> parent){
        if(parent.leftChild == entry) {
            parent.leftChild = null;
        }
        else {
            parent.rightChild = null;


        }
    }
}





