package com.cezarykluczynski.stapi.model.staff.query;

import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class StaffQueryBuiler extends AbstractQueryBuilderFactory<Staff> {

	@Inject
	public StaffQueryBuiler(JpaContext jpaContext) {
		super(jpaContext, Staff.class);
	}

}
