package com.kylin.script.util;

@FunctionalInterface
public interface BusinessHandler<E> {

	E handle(String[] items);

}
