package com.maybetm.mplrest.entities.payments;

import com.maybetm.mplrest.commons.repositories.ICommonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author zebzeev-sv
 * @version 12.07.2019 10:56
 */
public interface IDBPayment extends ICommonRepository<Payment>
{
  Page<Payment> findByAccountId(Long clientId, Pageable pageable);
}
