/**
 * Create SettingsPanel
 * @returns {Ext.Panel} settingsPanel
 */
var createSettingsPanel = function(){
	BN.mask.show();
	
	/**
	 * Back to Log editor
	 */
	var backToLogEditor = function(){
		var mainPanel = Ext.getCmp('mainPanel');
		mainPanel.remove('settingsPanel', true);
		mainPanel.insert(0, createLogEditorCarousel());
	};
	
	var buttonsGroupSettings = [
	{
		id : 'buttonBackSettings',
		text : '戻る',
		ui: 'back',
		disabled : true,
		handler: function(button, event) {
			backToLogEditor();
    	}
	}, {
		xtype: 'spacer'
	}, {
		text : 'ログアウト',
		ui: 'action',
		handler: function(button, event) {
    		window.location.href = BN.logoutURL;
    	}
	}];
	
	var dockedItemsSettings = [{
		xtype: 'toolbar',
		title: '設定',
		ui: 'light',
		items: buttonsGroupSettings,
		dock: 'top'
	}
	];
	
	/**
	 * Save settings
	 */
	var saveSettings = function(){
		BN.mask.show();
		var name =  Ext.getCmp('textfieldName').getValue();
		var birthday = Ext.getCmp('datepickerfieldBirthday').getValue();
		var timezoneOffset =  Ext.getCmp('spinnerfieldTimezoneOffset').getValue();
		
		if(name === null 
				|| name === ''
					|| birthday === null
					|| timezoneOffset === null){
			BN.mask.hide();
			Ext.Msg.alert(null,
					'未記入項目があります。',
					Ext.emptyFn);
			return;
		}
		
		var strMinTimezoneOffset = String(parseInt(timezoneOffset, 10) * 60);
		var strBirthday = Ext.util.Format.date(birthday, 'Ymd');
		
		Ext.Ajax.request({
			url: 'baby/update',
			params : {
				keyAccount : BN.keyAccount,
				name : name,
				timezoneOffset : strMinTimezoneOffset,
				birthday : strBirthday 
			},
			method : 'POST',
			success: function(response, opts) {
				console.log('baby/update is success. updated to follow object.')
				console.dir(response);
				BN.birthday = birthday;
				BN.hasDefaultBaby = true;
				
				backToLogEditor();
				
				var jsonDatastore = Ext.getCmp('listHourLog').store;
				jsonDatastore.load({params: {date: Ext.util.Format.date(BN.selectedDate, 'Ymd')}});
				
				BN.mask.hide();
			},
			failure: function(response, opts) {
				BN.mask.hide();
				var message = 'server-side failure with status code ' + response.status;
				console.log(message);
				alert(message);
			}
		});
	};
	
	var itemsSettings = [{
		xtype: 'fieldset',
		title: 'あかちゃんの情報',
		instructions: '入力して送信ボタンを押してください',
		defaults: {
			labelWidth: '35%'
		},
		items: [{
			id: 'textfieldName',
			xtype: 'textfield',
			name: 'name',
			label: 'お名前',
			placeHolder: 'あかちゃんのお名前',
			autoCapitalize : true,
			required: true,
			useClearIcon: true
		},  {
			id: 'datepickerfieldBirthday',
			xtype: 'datepickerfield',
			name: 'birthday',
			label: 'お誕生日',
			required: true,
			picker: { 
				slotOrder : ['year', 'month', 'day'],
				yearFrom: new Date().getFullYear() - 1} // from last year
		}, {
			id : 'spinnerfieldTimezoneOffset',
			xtype: 'spinnerfield',
			name : 'timezoneOffset',
			label: 'GMT時差',
			required: true,
			value: - (new Date().getTimezoneOffset() / 60)
		},{
			layout: 'vbox',
			defaults: {xtype: 'button', flex: 1, style: 'margin: .5em;'},
			items: [{
				id: 'buttonEnter',
				text: '送信',
				scope: this,
				hasDisabled: false,
				disabled : true,
				handler: function(btn){
					saveSettings();
				}
			}]
		}]
	},{
		xtype : 'panel',
		html : '<div align="center">Babenote v0.5.0</div>'
			+ '<div align="center"><a href="http://babenote.tumblr.com/" target="_blank">サービスの説明</a></div>'
	}];
	
	var settingsPanel = {
			id: 'settingsPanel',
			title: 'SettingsTab',
			xtype: 'panel',
			fullscreen: true,
			scroll: 'vertical',
			dockedItems: dockedItemsSettings,
			items : itemsSettings
	};
	
	/**
     * Load settings
     */
	var loadSettings = function(){
		Ext.Ajax.request({
			url: 'baby',
			params : {
				keyAccount : BN.keyAccount
			},
			method : 'GET',
			success: function(response, opts) {
				if(response.responseText != null 
						&& response.responseText.length > 0 ){
					var objResponse = Ext.decode(response.responseText);
					settingsPanel.baby = objResponse;
					
					Ext.getCmp('textfieldName').setValue(objResponse.name);
					var birthday = new Date(parseInt(objResponse.birthday, 10));
					BN.birthday = birthday;
					Ext.getCmp('datepickerfieldBirthday').setValue(birthday);
					
					var hourTimezoneOffset = parseInt(objResponse.timezoneOffset, 10) / 60;
					Ext.getCmp('spinnerfieldTimezoneOffset').setValue(hourTimezoneOffset);
				}

				if(BN.hasDefaultBaby){
					Ext.getCmp('buttonBackSettings').setDisabled(false);
				}
				Ext.getCmp('buttonEnter').setDisabled(false);
				BN.mask.hide();
			},
			failure: function(response, opts) {
				BN.mask.hide();
				var message = 'server-side failure with status code ' + response.status;
				console.log(message);
				alert(message);
			}
		});
	};
	loadSettings();
	
	return settingsPanel;
};