package com.jaehyune.stamp_app.service;

import com.jaehyune.stamp_app.dto.CommentDTO;
import com.jaehyune.stamp_app.dto.StampDTO;
import com.jaehyune.stamp_app.entity.Comment;
import com.jaehyune.stamp_app.entity.Stamp;

/**
 * This interface exists to eliminate circular dependency between any services that require another service's
 * conversion method by decoupling the services to instead extend to a mediator.
 * <p>
 * This interface is implemented by ConverterMediatorImpl.
 * <p>
 * If any new Service is added, add its DTO conversions here.
 */
public interface ConverterMediator<E, D> {

    E toEntity(D dto);

    D toDto(E entity);
}
