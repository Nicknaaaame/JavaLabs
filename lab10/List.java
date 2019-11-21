package com.myjavaproject.lab10;

import com.myjavaproject.lab10.exception.OutOfRangeException;
import com.myjavaproject.lab9.exception.NoSuchElementException;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.function.UnaryOperator;

public interface List<E> {
    void add(E e) throws OutOfRangeException;
    void add(int index, E element) throws OutOfRangeException;
    default boolean addAll(List<? extends E> l) throws OutOfRangeException{
        if(l.size()==0)
            return false;
        for(int i = 0; i<l.size(); ++i)
            add(l.get(i));
        return true;
    }
    default boolean addAll(int index, List<? extends E> l) throws OutOfRangeException{
        if(l.size()==0)
            return false;
        for(int i = 0; i<l.size(); ++i)
            add(index+i, l.get(i));
        return true;
    }
    void clear();
    boolean contains(E e);
    boolean containsAll(List<? extends E> l) throws OutOfRangeException;
    boolean equals(Object o);
    E get(int index) throws OutOfRangeException;
    int hashCode();
    int indexOf(E e) throws NoSuchElementException;
    boolean isEmpty();
    Iterator<E> iterator();
    int lastIndexOf(E e) throws OutOfRangeException, NoSuchElementException;
    ListIterator<E> listIterator();
    ListIterator<E>	listIterator(int index) throws OutOfRangeException;
    boolean remove(E e) throws OutOfRangeException, NoSuchElementException;
    E remove(int index) throws OutOfRangeException;
    boolean removeAll(List<? extends E> l) throws OutOfRangeException;
    void replaceAll(UnaryOperator<E> operator);
    boolean	retainAll(List<? extends E> l) throws OutOfRangeException;
    int size();
    E set(int index, E element) throws OutOfRangeException;
    void sort(Comparator<? super E> c);
    List<E>	subList(int fromIndex, int toIndex) throws OutOfRangeException;
    E[] toArray();
    <T> T[] toArray(T[] a);
}
