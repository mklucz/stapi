package com.cezarykluczynski.stapi.model.astronomicalObject.query;


import com.cezarykluczynski.stapi.model.astronomicalObject.entity.AstronomicalObject;
import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class AstronomicalObjectQueryBuilderFactory extends AbstractQueryBuilderFactory<AstronomicalObject> {

	@Inject
	public AstronomicalObjectQueryBuilderFactory(JpaContext jpaContext) {
		super(jpaContext, AstronomicalObject.class);
	}

}
