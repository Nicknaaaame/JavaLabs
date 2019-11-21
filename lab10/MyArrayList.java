package com.myjavaproject.lab10;

import com.myjavaproject.lab10.exception.OutOfRangeException;

import java.util.*;
import java.util.function.UnaryOperator;

public class MyArrayList<E> implements List<E> {
    private E[] elementData;
    private static final int DEFAULT_SIZE = 10;
    private int capacity;
    private int size;

    public MyArrayList(){
        this(DEFAULT_SIZE);
    }

    public MyArrayList(int initSize){
        elementData = (E[]) new Object[initSize];
        capacity = initSize;
        size = 0;
    }

    private void resize(int newCapacity){
        E[] array = (E[]) new Object[newCapacity];
        capacity = newCapacity;
        if (size >= 0)
            for (int i = 0; i<size; ++i)
                array[i] = elementData[i];
        elementData = array.clone();
    }

    private void checkRange(int index) throws OutOfRangeException {
        if(index>=size || index<0)
            throw new OutOfRangeException();
    }

    @Override
    public void add(E e) {
        if(size==capacity)
            resize(capacity*2);
        elementData[size++] = e;
    }

    @Override
    public void add(int index, E element) throws OutOfRangeException {
        if(index==size())
            checkRange(index-1);
        else
            checkRange(index);
        if(size==capacity)
            resize(capacity*2);
        size++;
        for (int i = size-1; i>index; --i)
            elementData[i] = elementData[i-1];
        elementData[index] = element;
    }

    public int getCapacity(){
        return capacity;
    }

    @Override
    public void clear() {
        elementData = (E[]) new Object[DEFAULT_SIZE];
        capacity = DEFAULT_SIZE;
        size = 0;
    }

    @Override
    public boolean contains(E e) {
        for(E i : elementData)
            if(e==i)
                return true;
        return false;
    }

    @Override
    public boolean containsAll(List<? extends E> l) throws OutOfRangeException {
        boolean flag;
        if(l.size()==0)
            return true;
        for (int i = 0; i<l.size(); ++i){
            flag = false;
            for(int j = 0; j<size(); ++j)
                if(l.get(i)==get(j)){
                    flag = true;
                    break;
                }
            if(!flag)
                return false;
        }
        return true;
    }

    @Override
    public E get(int index) throws OutOfRangeException {
        checkRange(index);
        return elementData[index];
    }

    @Override
    public int indexOf(E e) {
        for(int i = 0; i<size; ++i)
            if(elementData[i]==e)
                return i;
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<E>{
        int cursor = 0;

        @Override
        public boolean hasNext() {
            return size!=cursor;
        }

        @Override
        public E next() {
            try {
                checkRange(++cursor);
            } catch (OutOfRangeException e) {
                e.printStackTrace();
            }
            return elementData[cursor];
        }
    }

    @Override
    public int lastIndexOf(E e) throws OutOfRangeException {
        int index = -1;
        for(int i = 0; i<size; ++i)
            if(e == get(i))
                index = i;
        return index;
    }

    private class ListItr implements ListIterator<E>{
        int cursor = 0;

        public ListItr(){}

        public ListItr(int index){
            cursor = index;
        }

        @Override
        public boolean hasNext() {
            return cursor!=size;
        }

        @Override
        public E next() {
            try {
                checkRange(++cursor);
            } catch (OutOfRangeException e) {
                e.printStackTrace();
            }
            return elementData[cursor];
        }

        @Override
        public boolean hasPrevious() {
            return cursor!=0;
        }

        @Override
        public E previous() {
            try {
                checkRange(--cursor);
            } catch (OutOfRangeException e) {
                e.printStackTrace();
            }
            return elementData[cursor];
        }

        @Override
        public int nextIndex() {
            return cursor+1;
        }

        @Override
        public int previousIndex() {
            return cursor-1;
        }

        @Override
        public void remove() {
            try {
                MyArrayList.this.remove(cursor);
            } catch (OutOfRangeException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void set(E e) {
            try {
                MyArrayList.this.set(cursor, e);
            } catch (OutOfRangeException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void add(E e) {
            try {
                MyArrayList.this.add(cursor, e);
            } catch (OutOfRangeException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public ListIterator<E> listIterator() {
        return new ListItr();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new ListItr(index);
    }

    @Override
    public boolean remove(E e) throws OutOfRangeException {
        int index = indexOf(e);
        if(index==-1)
            return false;
        else{
            remove(index);
            return true;
        }
    }

    @Override
    public E remove(int index) throws OutOfRangeException {
        checkRange(index);
        E oldValue = elementData[index];
        for (int i = index; i<size-1; ++i)
            elementData[i] = elementData[i+1];
        --size;
        if(capacity>=size*4)
            resize(capacity/2);
        return oldValue;
    }

    @Override
    public boolean removeAll(List<? extends E> l) throws OutOfRangeException {
        boolean flag = false;
        for (int i = 0; i<l.size(); ++i)
            for(int j = 0; j<size(); ++j){
                if(l.get(i)==get(j)){
                    remove(get(j));
                    flag = true;
                }
            }
        return flag;
    }

    @Override
    public void replaceAll(UnaryOperator<E> operator) {
        for(int i = 0; i<size; ++i)
            elementData[i] = operator.apply(elementData[i]);
    }

    @Override
    public boolean retainAll(List<? extends E> l) throws OutOfRangeException {
        boolean flag = false, isEqual = false;
        for (int i = size()-1; i >= 0; --i) {
            for (int j = 0; j < l.size(); ++j)
                if (l.get(j) == get(i)){
                    isEqual = true;
                    flag = true;
                    break;
                }
            if (!isEqual)
                remove(get(i));
            isEqual = false;
        }
        return flag;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public E set(int index, E element) throws OutOfRangeException {
        checkRange(index);
        E oldValue = elementData[index];
        elementData[index] = element;
        return oldValue;
    }

    @Override
    public void sort(Comparator<? super E> c) {
        Arrays.sort((E[]) elementData, 0, size, c);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) throws OutOfRangeException {
        int del = toIndex-fromIndex;
        if(del == 0)
            return null;
        checkRange(del);
        MyArrayList<E> result = new MyArrayList<>();
        for (int i = fromIndex; i<=toIndex; ++i){
            result.add(get(i));
        }
        return result;
    }

    @Override
    public E[] toArray() {
        return (E[])elementData;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return (T[]) elementData;
    }

    @Override
    public int hashCode() {
        final E[] es = elementData;
        int hashCode = 1;
        for (int i = 0; i < size; i++) {
            Object e = es[i];
            hashCode = 31 * hashCode + (e == null ? 0 : e.hashCode());
        }
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof List)) {
            return false;
        }
        boolean equal = false;
        try {
            equal = (obj.getClass() == MyArrayList.class) ? equalsMyArrayList((MyArrayList<?>) obj) : false;
                    //: equalsRange((List<?>) obj, 0, size);
        } catch (OutOfRangeException e) {
            e.printStackTrace();
        }
        return equal;
    }

    private boolean equalsMyArrayList(MyArrayList<?> other) throws OutOfRangeException {
        boolean flag = true;
        if(other.size!=size)
            return false;
        for (int i = 0; i<size; ++i)
            if(get(i)!=other.get(i)){
                flag = false;
                break;
            }
        return flag;
    }

    @Override
    protected Object clone() {
        MyArrayList<E> result = new MyArrayList<>();
        for (int i = 0; i<size; ++i)
            try {
                result.add(get(i));
            } catch (OutOfRangeException e) {
                e.printStackTrace();
            }
        return result;
    }

    @Override
    public String toString() {
        if (size!=0){
            StringBuilder result = new StringBuilder("[");
            for(int i = 0; i<size-1; ++i)
                result.append(elementData[i].toString()).append(", ");
            result.append(elementData[size - 1]).append("]");
            return result.toString();
        }
        else
            return "[]";
    }
}
