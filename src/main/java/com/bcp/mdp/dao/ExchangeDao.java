package com.bcp.mdp.dao;

import com.bcp.mdp.model.Currency;
import com.bcp.mdp.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeDao extends JpaRepository<ExchangeRate, Long> {
	
	@Query("select e.instantCourse from ExchangeRate e where e.sellCurrency.code=:fromC and e.buyCurrency.code=:toC")
	double getInstantCourseRate(@Param("fromC") String fromC, @Param("toC") String toC);

	@Query("select e.batchNegociatedCourse from ExchangeRate e where e.sellCurrency.code=:fromC and e.buyCurrency.code=:toC")
	double getBatchNegociatedCourseRate(@Param("fromC") String fromC, @Param("toC") String toC);

	@Query("select e.individualNegociatedCourse from ExchangeRate e where e.sellCurrency.code=:fromC and e.buyCurrency.code=:toC")
	double getIndividualNegociatedCourseRate(@Param("fromC") String fromC, @Param("toC") String toC);

	@Query("select e.preferentialCourse from ExchangeRate e where e.sellCurrency.code=:fromC and e.buyCurrency.code=:toC")
	double getPreferentialCourseRate(@Param("fromC") String fromC, @Param("toC") String toC);
	/*Currency findByCode(String code);*/
}
