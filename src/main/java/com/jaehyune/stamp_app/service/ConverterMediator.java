package com.jaehyune.stamp_app.service;

/**
 * This interface exists to eliminate circular dependency between any services that require another service's
 * conversion method by decoupling the services to instead extend to a mediator.
 * <p>
 * This interface is implemented by ConverterMediatorImpl.
 * <p>
 *
 */
public interface ConverterMediator<E, D> {

    E toEntity(D dto);

    D toDto(E entity);
}
