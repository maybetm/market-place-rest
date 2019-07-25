package com.maybetm.mplrest.commons.services;

import com.maybetm.mplrest.commons.AEntity;
import com.maybetm.mplrest.commons.repositories.ICommonRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.MappedSuperclass;
import java.util.Optional;

/**
 * @author zebzeev-sv
 * @version 29.06.2019 18:51
 */
@MappedSuperclass
public abstract class AService<E extends AEntity, R extends ICommonRepository<E>> implements ICommonService<E>
{

  protected R repository;

  protected AService(R repository)
  {
    this.repository = repository;
  }

  @Override
  public Page<E> getEntityOnPage(Pageable pageable)
  {
    return repository.findAll(pageable);
  }

  @Override
  public Optional<E> save(E entity)
  {
    // fixme думаю стоит запретить через этот метод редактировать записи
    return Optional.of(repository.saveAndFlush(entity));
  }

  @Override
  public Optional<E> findById(Long id)
  {
    return repository.findById(id);
  }

  // fixme тут думаю можно сделать пакетное удаление
  @Override
  public void deleteById(Long id)
  {
    repository.deleteById(id);
  }

  public Optional<E> updateEntity (E fromDB, E updatable) {
    BeanUtils.copyProperties(updatable, fromDB, "id");
    return save(fromDB);
  }

}
