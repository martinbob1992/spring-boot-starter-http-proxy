package com.marsh.proxy.actuator;

/**
 * @author marsh
 * @date 2021年12月06日 11:05
 */
public interface RequestActuator {

    Object execute(Object[] params);
}
