import { TestBed, inject } from '@angular/core/testing';

import { InitializerService } from './initializer.service';

import { ApiBrowserApi } from '../api-browser/api-browser-api.service';
import { StatisticsApi } from '../statistics/statistics-api.service';
import { ApiDocumentationApi } from '../api-documentation/api-documentation-api.service';

class ApiBrowserApiMock {
	public loadDetails() {}
}

class StatisticsApiMock {
	public loadStatistics() {}
}

class ApiDocumentationApiMock {
	public loadDocumentation() {}
}

describe('InitializerService', () => {
	let apiBrowserApiMock: ApiBrowserApiMock;
	let statisticsApiMock: StatisticsApiMock;
	let apiDocumentationApiMock: ApiDocumentationApiMock;

	beforeEach(() => {
		apiBrowserApiMock = new ApiBrowserApiMock();
		statisticsApiMock = new StatisticsApiMock();
		apiDocumentationApiMock = new ApiDocumentationApiMock();

		TestBed.configureTestingModule({
			providers: [
				InitializerService,
				{
					provide: ApiBrowserApi,
					useValue: apiBrowserApiMock
				},
				{
					provide: StatisticsApi,
					useValue: statisticsApiMock
				},
				{
					provide: ApiDocumentationApi,
					useValue: apiDocumentationApiMock
				}
			]
		});
	});

	it('is created', inject([InitializerService], (initializerService: InitializerService) => {
		expect(initializerService).toBeTruthy();
	}));

	it('initializes', inject([InitializerService], (initializerService: InitializerService) => {
		spyOn(apiBrowserApiMock, 'loadDetails').and.returnValue(true);
		spyOn(statisticsApiMock, 'loadStatistics').and.returnValue(true);
		spyOn(apiDocumentationApiMock, 'loadDocumentation').and.returnValue(true);

		initializerService.init().then(() => {
			expect(apiBrowserApiMock.loadDetails).toHaveBeenCalled();
			expect(statisticsApiMock.loadStatistics).toHaveBeenCalled();
			expect(apiDocumentationApiMock.loadDocumentation).toHaveBeenCalled();
		});
	}));
});
