package com.cezarykluczynski.stapi.etl.template.common.processor.datetime;

import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DateRange;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFilter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.IntegerValidator;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Slf4j
public class PartToDateRangeProcessor implements ItemProcessor<Template.Part, DateRange> {

	private final DatelinkTemplateToLocalDateProcessor datelinkTemplateToLocalDateProcessor;

	private final TemplateFilter templateFilter;

	public PartToDateRangeProcessor(DatelinkTemplateToLocalDateProcessor datelinkTemplateToLocalDateProcessor,
			TemplateFilter templateFilter) {
		this.datelinkTemplateToLocalDateProcessor = datelinkTemplateToLocalDateProcessor;
		this.templateFilter = templateFilter;
	}

	@Override
	public DateRange process(Template.Part item) throws Exception {
		List<Template> templateList = item.getTemplates();

		return CollectionUtils.isEmpty(templateList) ? null : fromTemplate(templateList, item);
	}

	private DateRange fromTemplate(List<Template> templateList, Template.Part part) throws Exception {
		DateRange dateRange = new DateRange();

		List<Template> dateTemplateList = templateFilter.filterByTitle(templateList, TemplateTitle.D, TemplateTitle.DATELINK);

		Integer size = dateTemplateList.size();

		if (IntegerValidator.getInstance().isInRange(size, 1, 2)) {
			dateRange.setStartDate(datelinkTemplateToLocalDateProcessor.process(templateList.get(0)));
		}

		if (size == 2) {
			dateRange.setEndDate(datelinkTemplateToLocalDateProcessor.process(templateList.get(1)));
		}

		if (size > 2) {
			log.info("Template part {} has more than two datelink templates.", part);
		}

		return dateRange;
	}

}
