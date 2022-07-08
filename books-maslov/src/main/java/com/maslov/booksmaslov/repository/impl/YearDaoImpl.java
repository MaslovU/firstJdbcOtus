package com.maslov.booksmaslov.repository.impl;

import com.maslov.booksmaslov.domain.Year;
import com.maslov.booksmaslov.exception.NoGenreException;
import com.maslov.booksmaslov.exception.NoYearException;
import com.maslov.booksmaslov.repository.YearDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

import static com.maslov.booksmaslov.sql.SQLConstants.GET_ALL_YEARS;
import static com.maslov.booksmaslov.sql.SQLConstants.GET_YEAR_BY_DATE;
import static java.util.Objects.isNull;

@Repository
@Slf4j
public class YearDaoImpl implements YearDao {
    @PersistenceContext
    private final EntityManager em;

    public YearDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Year> getAllYears() {
        var query = em.createQuery(GET_ALL_YEARS, Year.class);
        return query.getResultList();
    }

    @Override
    public Year getYearByDate(String year) {
        var query = em.createQuery(GET_YEAR_BY_DATE, Year.class);
        query.setParameter("year", year);
        return checkResult(query, year);
    }

    @Override
    public Optional<Year> getYearById(long id) {
        return Optional.ofNullable(em.find(Year.class, id));
    }

    @Override
    @Transactional
    public Year createYear(Year year) {
        log.info("Created new Year");
        Long yearId = null;
        try {
            yearId = Optional.ofNullable(getYearByDate(year.getYearOfPublish()).getId()).get();
        } catch (NoYearException e) {
            if (isNull(year.getId())) {
                em.persist(year);
                return year;
            }
        }
        year.setId(yearId);
        return em.merge(year);
    }

    private Year checkResult(TypedQuery<Year> query, String year) {
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            log.warn("Has not year with name: {}", year);
            throw new NoYearException(String.format("Has not year with name %s", year));
        }
    }
}
