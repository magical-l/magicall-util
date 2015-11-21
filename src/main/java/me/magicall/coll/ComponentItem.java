package me.magicall.coll;

/**
 * 组合模式的节点
 * 
 * @param <T>
 */
public interface ComponentItem<T extends ComponentItem<T>> extends Iterable<T> {

	boolean leaf();

}
