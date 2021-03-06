import { TestBed, inject, async } from '@angular/core/testing';
import RestClient from 'another-rest-client';

import { RestApiService } from '../rest-api/rest-api.service';
import { PanelApi } from './panel-api.service';

class RestClientMock {
	public res: any;
	public common: any;
	public oauth: any;
}

class RestApiServiceMock {
	public getApi() {}
}

describe('PanelApi', () => {
	let restClientMock: RestClientMock;
	let restApiServiceMock: RestApiServiceMock;
	let res;

	beforeEach(() => {
		restClientMock = new RestClientMock();
		restApiServiceMock = new RestApiServiceMock();
		spyOn(restApiServiceMock, 'getApi').and.returnValue(restClientMock);
		res = jasmine.createSpy('res').and.callFake(() => {
			return { res };
		});
		restClientMock.res = res;

		TestBed.configureTestingModule({
			providers: [
				{
					provide: PanelApi,
					useClass: PanelApi
				},
				{
					provide: RestApiService,
					useValue: restApiServiceMock
				}
			]
		});
	});

	it('is created', inject([PanelApi], (panelApi: PanelApi) => {
		expect(panelApi).toBeTruthy();

		expect(res.calls.count()).toBe(6);
		expect(res.calls.argsFor(0)).toEqual(['common']);
		expect(res.calls.argsFor(1)).toEqual(['panel']);
		expect(res.calls.argsFor(2)).toEqual(['me']);
		expect(res.calls.argsFor(3)).toEqual(['oauth']);
		expect(res.calls.argsFor(4)).toEqual(['github']);
		expect(res.calls.argsFor(5)).toEqual(['oAuthAuthorizeUrl']);
	}));

	describe('after initialization', () => {
		const ME = {
			name: 'Name'
		};
		const OAUTH_AUTHORIZE_URL = {
			url: 'url'
		};

		beforeEach(() => {
			restClientMock.common = {
				panel: {
					me: {
						get: () => {
							return Promise.resolve(ME);
						}

					}
				}
			};

			restClientMock.oauth = {
				github: {
					oAuthAuthorizeUrl: {
						get: () => {
							return Promise.resolve(OAUTH_AUTHORIZE_URL);
						}
					}
				}
			};
		});

		it('gets me', inject([PanelApi], (panelApi: PanelApi) => {
			panelApi.getMe().then((response) => {
				expect(response).toBe(ME);
			});
		}));

		it('gets OAuth authorize URL', inject([PanelApi], (panelApi: PanelApi) => {
			panelApi.getOAuthAuthorizeUrl().then((response) => {
				expect(response).toBe(OAUTH_AUTHORIZE_URL);
			});
		}));
	});
});
