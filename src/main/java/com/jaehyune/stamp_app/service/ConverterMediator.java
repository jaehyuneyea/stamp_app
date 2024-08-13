package com.jaehyune.stamp_app.service;

/**
 * This interface exists to eliminate circular dependency between any services that require another service's
 * conversion method by decoupling the services to instead extend to a mediator.
 * <p>
 * This interface is implemented by ConverterMediatorImpl.
 * <p>
 * TODO: The mediator structure is deprecated, since some entities may require multiple DTOs.
 *       We need to get rid of this class and refactor to a generic mediator for service usage, not converters.
 */
public interface ConverterMediator<E, D> {

    E toEntity(D dto);

    D toDto(E entity);
}
