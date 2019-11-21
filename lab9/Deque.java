package com.myjavaproject.lab9;

import com.myjavaproject.lab10.exception.OutOfRangeException;
import com.myjavaproject.lab9.exception.NoSuchElementException;

public interface Deque<E> {
    void addFirst(E e);
    void addLast(E e) throws OutOfRangeException;
    E getFirst() throws NoSuchElementException;
    E getLast() throws NoSuchElementException;
    E poll() throws NoSuchElementException, OutOfRangeException;
    E pollFirst() throws NoSuchElementException, OutOfRangeException;
    E pollLast() throws NoSuchElementException, OutOfRangeException;
    E pop() throws NoSuchElementException, OutOfRangeException;
    E removeFirstOccurrence(E e) throws OutOfRangeException, NoSuchElementException;
    E removeLastOccurrence(E e) throws OutOfRangeException, NoSuchElementException;
}
