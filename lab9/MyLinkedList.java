package com.myjavaproject.lab9;

import com.myjavaproject.lab10.List;
import com.myjavaproject.lab10.exception.OutOfRangeException;
import com.myjavaproject.lab9.exception.NoSuchElementException;

import java.util.*;
import java.util.function.UnaryOperator;

public class MyLinkedList<E> implements List<E>, Deque<E>, Iterable<E> {
    private static class Node<E>{
        Node<E> next;
        Node<E> prev;
        E item;

        public Node(Node<E> next, Node<E> prev, E item) {
            this.next = next;
            this.prev = prev;
            this.item = item;
        }
    }

    private Node<E> first = null;
    private Node<E> last = null;
    private int size;

    private void checkRange(int index) throws OutOfRangeException {
        if(index>=size || index<0)
            throw new OutOfRangeException();
    }

    public MyLinkedList(){
    }

    @Override
    public void addFirst(E e) {
        if(size==0){
            first = new Node<>(last, null, e);
            last = first;
        }
        else {
            first.prev = new Node<>(first, null, e);
            first = first.prev;

        }
        size++;
    }

    @Override
    public void addLast(E e) {
        if(size==0){
            addFirst(e);
            return;
        }
        last.next = new Node<>(null, last, e);
        last = last.next;
        size++;
    }

    @Override
    public E getFirst() throws NoSuchElementException {
        if(first==null)
            throw new NoSuchElementException();
        return first.item;
    }

    @Override
    public E getLast() throws NoSuchElementException {
        if(last==null)
            throw new NoSuchElementException();
        return last.item;
    }

    @Override
    public E poll() throws NoSuchElementException, OutOfRangeException {
        return pollFirst();
    }

    @Override
    public E pollFirst() throws NoSuchElementException, OutOfRangeException {
        return remove(0);
    }

    @Override
    public E pollLast() throws NoSuchElementException, OutOfRangeException {
        return remove(size-1);
    }

    @Override
    public E pop() throws NoSuchElementException, OutOfRangeException {
        return pollLast();
    }

    @Override
    public E removeFirstOccurrence(E e) throws OutOfRangeException, NoSuchElementException {
        return removeAndReturn(e);
    }

    @Override
    public E removeLastOccurrence(E e) throws OutOfRangeException, NoSuchElementException {
        ListItr itr = new ListItr(size-1);
        E tmp = itr.node.item;
        while (itr.node!=null){
            if(tmp.equals(e)){
                itr.remove();
                return tmp;
            }
            tmp = itr.next();
        }
        throw new NoSuchElementException();
    }

    @Override
    public void add(E e) {
        addLast(e);
    }

    @Override
    public void add(int index, E element) throws OutOfRangeException {
        new ListItr(index).add(element);
    }

    @Override
    public void clear() {
        for (Node<E> node = first; node != null; ) {
            Node<E> next = node.next;
            node.item = null;
            node.next = null;
            node.prev = null;
            node = next;
        }
        first = last = null;
        size = 0;
    }

    @Override
    public boolean contains(E e) {
        try {
            ListItr itr = new ListItr(e);
            return true;
        }
        catch (NoSuchElementException ex){
            return false;
        }

    }

    @Override
    public boolean containsAll(List<? extends E> l) {
        E[] arr = l.toArray();
        ListItr itr = new ListItr();
        E item = first.item;
        for(int i = 0; i<size; i++){
            for (int j = 0; i<arr.length; ++i){
                if (item!=arr[j])
                    return false;
            }
            item = itr.next();
        }
        return true;
    }

    @Override
    public E get(int index) throws OutOfRangeException {
        checkRange(index);
        return new ListItr(index).node.item;
    }

    @Override
    public int indexOf(E e) throws NoSuchElementException {
        return new ListItr(e).index;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public Iterator<E> iterator() {
        return listIterator();
    }

    @Override
    public int lastIndexOf(E e) throws NoSuchElementException, OutOfRangeException {
        ListItr itr = new ListItr(size);
        /*itr.node = last;
        itr.index = size-1;*/
        while (itr.hasPrevious()){
            if(e.equals(itr.previous()))
                return itr.index;
        }
        throw new NoSuchElementException();
    }

    private class ListItr implements ListIterator<E>{
        Node<E> node;
        int index;

        ListItr(){
            node = first;
            index = -1;
        }

        ListItr(int index) throws OutOfRangeException {
            this.index = index;
            if(index==size-1 || index==size){
                node=last;
                return;
            }
            /*if(index==size){
                node = last.next;
                return;
            }*/
            if(index==0 || index==-1){
                node = first;
                return;
            }
            checkRange(index);
            node = first;
            for(int i = 0; i<index; ++i)
                node = node.next;
        }

        ListItr(E e) throws NoSuchElementException {
            this();
            if (e == null)
                throw new NoSuchElementException();
            while (hasNext()) {
                if (e.equals(next()))
                    return;
            }
            throw new NoSuchElementException();
        }

        @Override
        public boolean hasNext() {
            return node.next!=null;
        }

        @Override
        public E next() {
            if (hasNext()){
                if(index==-1){
                    index++;
                    return node.item;
                }
                node = node.next;
                index++;
                return node.item;
            }
            return null;
        }

        @Override
        public boolean hasPrevious() {
            return node.prev!=null;
        }

        @Override
        public E previous() {
            if (hasPrevious()){
                if(index==size){
                    index--;
                    return node.item;
                }
                node = node.prev;
                index--;
                return node.item;
            }
            return null;
        }

        @Override
        public int nextIndex() {
            return index+1;
        }

        @Override
        public int previousIndex() {
            return index-1;
        }

        @Override
        public void remove() {
            Node<E> tmp;
            if(!hasPrevious()){
                tmp = node.next;
                node.next.prev = null;
                node.next = null;
                first = tmp;
            }
            else {
                if (!hasNext()){
                    tmp = node.prev;
                    node.prev.next = null;
                    node.prev = null;
                    last = tmp;
                }
                else {
                    tmp = node.next;
                    node.prev.next = node.next;
                    node.next.prev = node.prev;
                }
            }
            node = tmp;
            size--;
        }

        @Override
        public void set(E e) {
            node.item = e;
        }

        @Override
        public void add(E e) {
            Node<E> tmp;
            if(index<=0){
                addFirst(e);
                return;
            }
            if (index==size){
                addLast(e);
                return;
            }
            tmp = new Node<>(node, node.prev, e);
            node.prev.next = tmp;
            node.prev = tmp;
            node = tmp;
            size++;
        }
    }

    @Override
    public ListIterator<E> listIterator() {
        return new ListItr();
    }

    @Override
    public ListIterator<E> listIterator(int index) throws OutOfRangeException {
        return new ListItr(index);
    }

    private E removeAndReturn(E e) throws OutOfRangeException, NoSuchElementException {
        ListItr itr = new ListItr(e);
        E item = itr.node.item;
        itr.remove();
        return item;
    }

    @Override
    public boolean remove(E e){
        try {
            removeAndReturn(e);
            return true;
        }
        catch (NoSuchElementException | OutOfRangeException ex){
            return false;
        }
    }

    @Override
    public E remove(int index) throws OutOfRangeException {
        checkRange(index);
        ListItr itr = new ListItr(index);
        E item = itr.node.item;
        itr.remove();
        return item;
    }

    @Override
    public boolean removeAll(List<? extends E> l){
        ListItr itr = new ListItr();
        itr.node = last;
        itr.index = size-1;
        E[] arr = l.toArray();
        E tmp = itr.node.item;
        for (int i = size-1; i>=0; --i) {
            for (E e : arr)
                if (tmp.equals(e)){
                    itr.remove();
                    break;
                }
            tmp = itr.previous();
        }
        return true;
    }

    @Override
    public void replaceAll(UnaryOperator<E> operator) {
        ListItr itr = new ListItr();
        for (int i = 0; i<size; ++i){
            itr.set(operator.apply(itr.next()));
        }
    }

    @Override
    public boolean retainAll(List<? extends E> l) {
        ListItr itr = new ListItr();
        E[] arr = l.toArray();
        E tmp = itr.next();
        boolean flag = false;
        int tmpSize = size;
        for (int i = 0; i < tmpSize; ++i) {
            for (E e : arr) {
                if (tmp.equals(e)) {
                    flag = true;
                    break;
                }
            }
            if (!flag){
                itr.remove();
                tmp = itr.node.item;
            }
            else
                tmp = itr.next();
            flag = false;
        }
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public E set(int index, E element) throws OutOfRangeException {
        ListItr itr = new ListItr(index);
        E item = itr.node.item;
        itr.set(element);
        return item;
    }

    @Override
    public void sort(Comparator<? super E> c) {
        E[] arr = toArray();
        Arrays.sort(arr, c);
        ListItr itr = new ListItr();
        for (E e : arr) {
            itr.next();
            itr.set(e);
        }
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) throws OutOfRangeException {
        MyLinkedList<E> result = new MyLinkedList<>();
        checkRange(toIndex-fromIndex);
        ListItr itr = new ListItr(fromIndex-1);
        for (int i = fromIndex; i<=toIndex; ++i)
            result.addLast(itr.next());
        return result;
    }

    @Override
    public E[] toArray() {
        E[] array = (E[]) new Object[size];
        ListItr itr = new ListItr();
        for (int i = 0; i<size; ++i)
            array[i] = itr.next();
        return array;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size)
            a = (T[])java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
        int i = 0;
        T[] result = a;
        for (Node<E> x = first; x != null; x = x.next)
            result[i++] = (T) x.item;

        if (a.length > size)
            a[size] = null;

        return a;
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        Node<E> tmp = first;
        while (tmp!=null){
            hashCode = 31*hashCode + (tmp.item==null ? 0 : tmp.item.hashCode());
            tmp = tmp.next;
        }
        return hashCode;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof List))
            return false;

        ListIterator<E> e1 = listIterator();
        ListIterator<?> e2 = ((List<?>) o).listIterator();
        while (e1.hasNext() && e2.hasNext()) {
            E o1 = e1.next();
            Object o2 = e2.next();
            if (!(Objects.equals(o1, o2)))
                return false;
        }
        return !(e1.hasNext() || e2.hasNext());
    }

    @Override
    protected Object clone() {
        MyLinkedList<E> result = null;
        if(size==0)
            return result;
        try {
            result = (MyLinkedList<E>) subList(0, size-1);
        } catch (OutOfRangeException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String toString() {
        if (size!=0){
            E[] array = toArray();
            StringBuilder result = new StringBuilder("[");
            for(int i = 0; i<size-1; ++i)
                result.append(array[i].toString()).append(", ");
            result.append(array[size - 1]).append("]");
            return result.toString();
        }
        else
            return "[]";
    }
}
