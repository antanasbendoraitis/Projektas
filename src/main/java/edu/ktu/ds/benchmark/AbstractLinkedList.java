package edu.ktu.ds.benchmark;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Antanas
 */
public interface AbstractLinkedList<E> {
    
    public void add(E element);
    
    public void add(int place, E element);
    
    public void addBefore(int place, E element);
    
    public void addAfter(int place, E element);
    
    public void addFirst(E element);
    
    public void addLast(E element);
    
    public void addBefore(E elmentAfter, E element);
    
    public void addAfter(E elmentBefore, E element);
    
    public E remove(E element);
    
    public E remove(int number);
    
    public E removeFirst();
    
    public E removeLast();
    
    public void removeRange(int beginPlace, int endPlace);
    
    public void removeRange(E beginElement, E endElement);
    
    public void set(E oldElement, E newElement);
    
    public void set(int place, E element);
    
    public E get(int place);
    
    public Object[] toArray();
    
    public int Size();
    
    public void clear();
    
    public boolean contains(E element);
    
    public DoublyLinkedList<E> clone();
    
    public void Begin(boolean side);
    
    public boolean isEmpty();
    
    public void Next();
}
