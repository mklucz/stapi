package com.cezarykluczynski.stapi.etl.template.comics.processor;

import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear;
import com.cezarykluczynski.stapi.etl.template.common.service.TemplateToDayMonthYearParser;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFilter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
@Slf4j
public class ComicsTemplatePartToDayMonthRangeProcessor implements ItemProcessor<Template.Part, DayMonthYear> {

	private TemplateFilter templateFilter;

	private TemplateToDayMonthYearParser templateToDayMonthYearParser;

	@Inject
	public ComicsTemplatePartToDayMonthRangeProcessor(TemplateFilter templateFilter, TemplateToDayMonthYearParser templateToDayMonthYearParser) {
		this.templateFilter = templateFilter;
		this.templateToDayMonthYearParser = templateToDayMonthYearParser;
	}

	@Override
	public DayMonthYear process(Template.Part item) throws Exception {
		List<Template> dayTemplateList = templateFilter.filterByTitle(item.getTemplates(), TemplateName.D, TemplateName.DATELINK);
		List<Template> monthTemplateList = templateFilter.filterByTitle(item.getTemplates(), TemplateName.M, TemplateName.MONTHLINK);
		List<Template> yearTemplateList = templateFilter.filterByTitle(item.getTemplates(), TemplateName.Y, TemplateName.YEARLINK);

		DayMonthYear dayMonthYearFrom = null;

		if (!dayTemplateList.isEmpty()) {
			if (dayTemplateList.size() > 1) {
				log.warn("More than one datelink template found {}, using the first one", dayTemplateList);
			}
			dayMonthYearFrom = templateToDayMonthYearParser.parseDayMonthYearCandidate(dayTemplateList.get(0));
		} else if (!monthTemplateList.isEmpty()) {
			if (monthTemplateList.size() > 1) {
				log.warn("More than one monthlink template found {}, using the first one", monthTemplateList);
			}
			dayMonthYearFrom = templateToDayMonthYearParser.parseMonthYearCandidate(monthTemplateList.get(0));
		} else if (!yearTemplateList.isEmpty()) {
			if (yearTemplateList.size() > 1) {
				log.warn("More than one yearlink template found {}, using the first one", yearTemplateList);
			}
			dayMonthYearFrom = templateToDayMonthYearParser.parseYearCandidate(yearTemplateList.get(0));
		}

		return dayMonthYearFrom;
	}

}
