import { Component, OnInit, ViewEncapsulation } from '@angular/core';

import { RestApiService } from '../rest-api/rest-api.service';
import { WindowReferenceService } from '../window-reference/window-reference.service';
import { PanelApiKeysComponent } from './api-keys/panel-api-keys.component';

enum PanelView {
	API_KEYS,
	ACCOUNT_SETTINGS,
	ADMINISTRATION
}

enum ApplicationPermission {
	API_KEY_MANAGEMENT,
	ADMIN_MANAGEMENT
}

@Component({
	selector: 'panel',
	templateUrl: './panel.component.html',
	styleUrls: ['./panel.component.sass']
})
export class PanelComponent implements OnInit {

	private restApiService: RestApiService;
	private windowReferenceService: WindowReferenceService;
	private name: string;
	private permissions: Set<ApplicationPermission>;
	private authenticated: boolean = false;
	private redirecting: boolean = false;
	private authenticationRequired: boolean = false;
	private activeView: PanelView = PanelView.API_KEYS;

	constructor(restApiService: RestApiService, windowReferenceService: WindowReferenceService) {
		this.restApiService = restApiService;
		this.windowReferenceService = windowReferenceService;
	}

	ngOnInit() {
		this.restApiService.getMe().then((response) => {
			this.authenticated = true;
			this.name = response.name;
			this.permissions = new Set();
			response.permissions.forEach((permission) => {
				let applicationPermission: ApplicationPermission = ApplicationPermission[<string>permission];
				this.permissions.add(applicationPermission);
			});
		}).catch((error) => {
			if (error.status === 403) {
				this.authenticationRequired = true;
			}
		});
	}

	getName() {
		return this.name;
	}

	getButtonLabel() {
		return this.isRedirecting() ? 'Redirecting to GitHub...' : 'Authenticate with GitHub';
	}

	isRedirecting() {
		return this.redirecting;
	}

	isAuthenticated() {
		return this.authenticated;
	}

	isAuthenticationRequired() {
		return this.authenticationRequired;
	}

	showApiKeys() {
		this.activeView = PanelView.API_KEYS;
	}

	showAccountSettings() {
		this.activeView = PanelView.ACCOUNT_SETTINGS;
	}

	showAdminManagement() {
		this.activeView = PanelView.ADMINISTRATION;
	}

	apiKeysIsVisible() {
		return this.activeView === PanelView.API_KEYS;
	}

	accountSettingsIsVisible() {
		return this.activeView === PanelView.ACCOUNT_SETTINGS;
	}

	adminManagementIsVisible() {
		return this.activeView === PanelView.ADMINISTRATION;
	}

	hasAdminManagementPermission() {
		return this.permissions.has(ApplicationPermission.ADMIN_MANAGEMENT);
	}

	redirectToOAuth() {
		this.redirecting = true;
		this.restApiService.getOAuthAuthorizeUrl().then((response) => {
			this.windowReferenceService.getWindow().location.href = response.url;
		});
	}

}