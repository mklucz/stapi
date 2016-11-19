package com.cezarykluczynski.stapi.server.series.mapper

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl
import spock.lang.Specification

import javax.xml.datatype.DatatypeConstants
import javax.xml.datatype.XMLGregorianCalendar
import java.time.LocalDate

abstract class AbstractSeriesMapperTest extends Specification {

	protected static final Long ID = 1L
	protected static final String TITLE = 'TITLE'
	protected static final String ABBREVIATION = 'ABBREVIATION'
	protected static final Integer PRODUCTION_START_YEAR = 1998
	protected static final Integer PRODUCTION_END_YEAR = 2002
	protected static final Integer PRODUCTION_START_YEAR_FROM = 1990
	protected static final Integer PRODUCTION_START_YEAR_TO = 1992
	protected static final Integer PRODUCTION_END_YEAR_FROM = 1997
	protected static final Integer PRODUCTION_END_YEAR_TO = 1999
	protected static final LocalDate ORIGINAL_RUN_START_DATE = LocalDate.of(1999, 5, 3)
	protected static final LocalDate ORIGINAL_RUN_END_DATE = LocalDate.of(2001, 2, 18)
	protected static final LocalDate ORIGINAL_RUN_START_DATE_FROM_DB = LocalDate.of(1991, 1, 2)
	protected static final LocalDate ORIGINAL_RUN_START_DATE_TO_DB = LocalDate.of(1993, 3, 4)
	protected static final LocalDate ORIGINAL_RUN_END_DATE_FROM_DB = LocalDate.of(1996, 5, 6)
	protected static final LocalDate ORIGINAL_RUN_END_DATE_TO_DB = LocalDate.of(1998, 7, 8)
	protected static final XMLGregorianCalendar ORIGINAL_RUN_START_DATE_FROM_XML = XMLGregorianCalendarImpl
			.createDate(1991, 1, 2, DatatypeConstants.FIELD_UNDEFINED)
	protected static final XMLGregorianCalendar ORIGINAL_RUN_START_DATE_TO_XML = XMLGregorianCalendarImpl
			.createDate(1993, 3, 4, DatatypeConstants.FIELD_UNDEFINED)
	protected static final XMLGregorianCalendar ORIGINAL_RUN_END_DATE_FROM_XML = XMLGregorianCalendarImpl
			.createDate(1996, 5, 6, DatatypeConstants.FIELD_UNDEFINED)
	protected static final XMLGregorianCalendar ORIGINAL_RUN_END_DATE_TO_XML = XMLGregorianCalendarImpl
			.createDate(1998, 7, 8, DatatypeConstants.FIELD_UNDEFINED)
	protected static final XMLGregorianCalendar ORIGINAL_RUN_START_DATE_XML = XMLGregorianCalendarImpl
			.createDate(1999, 5, 3, DatatypeConstants.FIELD_UNDEFINED)
	protected static final XMLGregorianCalendar ORIGINAL_RUN_END_DATE_XML = XMLGregorianCalendarImpl
			.createDate(2001, 2, 18, DatatypeConstants.FIELD_UNDEFINED)

}