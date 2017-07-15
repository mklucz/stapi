package com.cezarykluczynski.stapi.etl.trading_card.creation.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.trading_card.creation.dto.CardSizeDTO;
import com.cezarykluczynski.stapi.etl.trading_card.creation.dto.ProductionRunDTO;
import com.cezarykluczynski.stapi.etl.trading_card.creation.dto.TradingCarSetHeaderValuePair;
import com.cezarykluczynski.stapi.etl.trading_card.creation.dto.TradingCardSetTableHeader;
import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
@Slf4j
public class TradingCardSetTableValuesEnrichingProcessor
		implements ItemEnrichingProcessor<EnrichablePair<TradingCarSetHeaderValuePair, TradingCardSet>> {

	private final ProductionRunProcessor productionRunProcessor;

	private final CardSizeProcessor cardSizeProcessor;

	private final TradingCardSetCountiesProcessor tradingCardSetCountiesProcessor;

	@Inject
	public TradingCardSetTableValuesEnrichingProcessor(ProductionRunProcessor productionRunProcessor, CardSizeProcessor cardSizeProcessor,
			TradingCardSetCountiesProcessor tradingCardSetCountiesProcessor) {
		this.productionRunProcessor = productionRunProcessor;
		this.cardSizeProcessor = cardSizeProcessor;
		this.tradingCardSetCountiesProcessor = tradingCardSetCountiesProcessor;
	}

	@Override
	public void enrich(EnrichablePair<TradingCarSetHeaderValuePair, TradingCardSet> enrichablePair) throws Exception {
		String key = enrichablePair.getInput().getHeaderText();
		String value = enrichablePair.getInput().getValueText();
		TradingCardSet tradingCardSet = enrichablePair.getOutput();

		switch(key) {
			case TradingCardSetTableHeader.RELEASED:
				// TODO
				break;
			case TradingCardSetTableHeader.CARDS_PER_PACK:
				// TODO:
				break;
			case TradingCardSetTableHeader.PACKS_PER_BOX:
				// TODO:
				break;
			case TradingCardSetTableHeader.BOXES_PER_CASE:
				// TODO:
				break;
			case TradingCardSetTableHeader.PRODUCTION_RUN:
				ProductionRunDTO productionRunDTO = productionRunProcessor.process(value);
				if (productionRunDTO != null) {
					tradingCardSet.setProductionRun(productionRunDTO.getProductionRun());
					tradingCardSet.setProductionRunUnit(productionRunDTO.getProductionRunUnit());
				}
				break;
			case TradingCardSetTableHeader.CARDS_SIZE:
				CardSizeDTO cardSizeDTO = cardSizeProcessor.process(value);
				if (cardSizeDTO != null) {
					tradingCardSet.setCardWidth(cardSizeDTO.getWidth());
					tradingCardSet.setCardHeight(cardSizeDTO.getHeight());
				}
				break;
			case TradingCardSetTableHeader.MANUFACTURER:
				// TODO:
				break;
			case TradingCardSetTableHeader.COUNTRY:
				tradingCardSet.getCountriesOfOrigin().addAll(tradingCardSetCountiesProcessor.process(value));
				break;
			default:
				if (!TradingCardSetTableHeader.SPARSE_HEADERS.contains(key)) {
					log.info("Unknown trading card set key: {}", key);
				}
				break;
		}

	}

}