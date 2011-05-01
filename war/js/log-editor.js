/**
 * Create logEditorCarousel
 * @returns {Ext.Carousel} logEditorCarousel
 */
var createLogEditorCarousel = function(){
	/**
	 * Handle event of buttonDatePicker
	 * @param button
	 * @param event
	 * @returns
	 */
	var handlerButtonDatePicker = function(button, event) {
		var datePicker = new Ext.DatePicker({
			useTitles: true,
			slotOrder : ['year', 'month', 'day'],
			value: {
				day: BN.selectedDate.getDate(),
				month: BN.selectedDate.getMonth() + 1,
				year: BN.selectedDate.getFullYear()
			},
			listeners : {
				change : function(picker) {
					BN.selectedDate = picker.getValue();
					var store = Ext.getCmp('listHourLog').store;
					store.load({params: {date: Ext.util.Format.date(BN.selectedDate, 'Ymd')}});
				}
			}
		});
		datePicker.show();
	};
	
	var buttonsGroupDate = [{
		id : 'buttonDatePicker',
		text: 'Now loading...',
		handler: handlerButtonDatePicker
	},{
		xtype: 'spacer'
	},{
		ui: 'action',
		html: '<a href="http://mobile.twitter.com/searches?q=babenote" target="_blank"><img id="twitter-icon" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAYAAADgdz34AAAABGdBTUEAANbY1E9YMgAAABl0RVh0U29mdHdhcmUAQWRvYmUgSW1hZ2VSZWFkeXHJZTwAAAFTSURBVHjaYvj//z8DDENBBBDfA+KPQLwJiM0YSAQoZqJZ4AiSR8PvgVieXAuY0OQysKgXAGJzBjIBugVqONT9pJYFuMA/almAyyBGalnwldo+YERKngyMjIy7gZQLFnWXgPglEDNj8dkvIH4ExBuAeBcQ/0E2Ez2ZbsKSTEnBswklU0pBJBBLk5OKiAVsQMxOSwtARcxzZAEWNAU8uHI/AYP/APEVaEnwHZ8Ff3AYUAjE24GYC4flX4D4MTRFMeCz4D0OC+4C8S1qZLRnVI4TDAuu4FAnSK4F6EF0B4e6KiA+D8QPgZgVT0L4BMR/8RUVEkDqBhDzYzEAlDq+4Sj4GKH4MhDHAs18iKuoAIElFBYX3YSKikUUxivBogJUIi6kwIJvuFsAqJE/hcwg8kY2Ez2S0V1jAS0hbYFYGIi5kSL5L7Su/g7NwSC6D4iXI5sJEGAAg+TVC+h6d+4AAAAASUVORK5CYII=" /></a>'
	},{
		iconMask: true,
		iconCls: 'settings',
		ui: 'action',
		handler: function(btn, event) {
			var mainPanel = Ext.getCmp('mainPanel');
			mainPanel.remove('logEditorCarousel', true);
			mainPanel.insert(0, createSettingsPanel());
		}
	}];
	
	// logEditorCarousel HourLog button
	var buttonsGroupHourLog = [
	                           {
	                        	   id: 'buttonsGroupHourLog',
	                        	   xtype: 'segmentedbutton',
	                        	   allowMultiple: true,
	                        	   items: [{
	                        		   id: 'buttonNurse',
	                        		   iconMask: true,
	                        		   iconCls: 'nurse'
	                        	   }, {
	                        		   id: 'buttonMilk',
	                        		   iconMask: true,
	                        		   iconCls: 'milk'
	                        	   }, {
	                        		   id: 'buttonCrap',
	                        		   iconMask: true,
	                        		   iconCls: 'crap'
	                        	   }, {
	                        		   id: 'buttonPiss',
	                        		   iconMask: true,
	                        		   iconCls: 'piss'
	                        	   }, {
	                        		   id: 'buttonSleep',
	                        		   iconMask: true,
	                        		   iconCls: 'sleep'
	                        	   }],
	                        	   listeners : {
	                        		   toggle : function(container, button, active){
	                        			   
	                        			   var listHourLog = Ext.getCmp('listHourLog');
	                        			   var selections = listHourLog.getSelectedRecords();
	                        			   if(selections.length === 0) return;
	                        			   var hourLogRecord = selections[0];
	                        			   var hour = hourLogRecord.get('hour');
	                        			   var keyDayLog = hourLogRecord.get('keyDayLog');
	                        			   
	                        			   var isNurse = Ext.getCmp('buttonNurse').pressed;
	                        			   var isMilk = Ext.getCmp('buttonMilk').pressed;
	                        			   var isCrap = Ext.getCmp('buttonCrap').pressed;
	                        			   var isPiss = Ext.getCmp('buttonPiss').pressed;
	                        			   var isSleep = Ext.getCmp('buttonSleep').pressed;
	                        			   
	                        			   // For response, update ui before POST.
	                        			   // toggle button must be speedy.
	                        			   hourLogRecord.set('isNurse', isNurse);
	                        			   hourLogRecord.set('isMilk', isMilk);
	                        			   hourLogRecord.set('isCrap', isCrap);
	                        			   hourLogRecord.set('isPiss', isPiss);
	                        			   hourLogRecord.set('isSleep', isSleep);
	                        			   BN.createDisplayString(hourLogRecord);
	                        			   hourLogRecord.commit();
	                        			   
	                        			   listHourLog.refreshNode(hour);
	                        			   
	                        			   updateStatusCount(Ext.getCmp('listHourLog').store.getRange());
	                        			   
	                        			   // update hour log of datastore
	                        			   Ext.Ajax.request({
	                        				   url: 'hourlog/update',
	                        				   params : {
	                        					   keyDayLog : keyDayLog,
	                        					   hour : hour,
	                        					   isNurse : isNurse,
	                        					   isMilk : isMilk,
	                        					   isCrap : isCrap,
	                        					   isPiss : isPiss,
	                        					   isSleep : isSleep
	                        				   },
	                        				   method : 'POST',
	                        				   success: function(response, opts) {
	                        					   console.log('hourlog/update is success. updated to follow object.')
	                        					   console.dir(response);
	                        				   },
	                        				   failure: function(response, opts) {
	                        					   var message = 'server-side failure with status code ' + response.status;
	                        					   console.log(message);
	                        					   alert(message);
	                        				   }
	                        			   });
	                        		   }
	                        	   }
	                           },{
	                        	   xtype: 'spacer'
	                           }];
	
   	/**
     * Create Google Adsense Element
     * @returns {HTTPElement} dom
     */
    var createGoogleAdsEl = function(){
    	var el = Ext.getDom('google_ads_frame');
    	if(el){
    		return el.cloneNode(true);
    	}else{
    		return null;
    	}
    };
	
	var dockedItemsLogEditor = [{
		xtype: 'toolbar',
		ui: 'light',
		items: buttonsGroupDate,
		dock: 'top'
	}, {
		xtype: 'toolbar',
		ui: 'light',
		items: buttonsGroupHourLog,
		dock: 'top'
	}, { // Google Adsense
		xtype: 'toolbar',
		ui: 'light',
		defaultType: 'panel',
		items: [{
			xtype: 'spacer'
		}, {
			id:'googleAdsPanel',
			contentEl: createGoogleAdsEl()
		}, {
			xtype: 'spacer'
		}],
		dock: 'bottom'
	}
	];
	
	// Prepare List data
	Ext.regModel('HourLog', {
		fields: ['keyDayLog',
		         {name:'date', type: 'integer'},
		         'keyHourLog',
		         {name:'hour', type: 'integer'},
		         {name:'isNurse', type: 'boolean'},
		         {name:'isMilk', type: 'boolean'},
		         {name:'isCrap', type: 'boolean'},
		         {name:'isPiss', type: 'boolean'},
		         {name:'isSleep', type: 'boolean'},
		         'memo',
		         'strHour',
		         'title',
		         'shortenMemo',
		         'strHourClass'
		         ]
	});
	
	var httpProxy = new Ext.data.HttpProxy({
		url : 'daylog',
		extraParams : {
			keyAccount : BN.keyAccount,
			date : Ext.util.Format.date(BN.selectedDate, 'Ymd') 
		},
		method : 'GET',
		reader: {
			type: 'json',
			root: 'hourLogListRef'
		}
	});
	
	/**
     * Create strHour and title and shorten memo
     * @param {Ext.data.Record} record
     * @returns {Ext.data.Record} record
     */
    BN.createDisplayString = function(record){
    	var title = '';
    	title = record.get('isNurse') ? title + '<img hspace="2px" vspace="0px" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAYAAADgdz34AAAABGdBTUEAANbY1E9YMgAAABl0RVh0U29mdHdhcmUAQWRvYmUgSW1hZ2VSZWFkeXHJZTwAAAF3SURBVHja3NW9S8NAGMfxa4OCUoTi61Is6NBFUHBQEUddHBwEEV0c/Auki7i5Ceqkf4GLuIi4OYgdRUEUxJdBELRoa6mTHXz7Hl4gPS5NggmIhU8hz+XyI08uOcsSQsQjZMm/KH//K6ADIxjFIFL4wMvvEoToxiYK+NJUcIhJ6+dcKY0h1DlqtnpkMIVeSwXMGy5sso0xPKvjI7SqCyewjHvH+U9oloNxbPkM+dSO11RADClkcavG8naA1IRznyFOx4Y2JVSLeuwW2WThNWDAuiGgil6YCXDxVTRq89vRVVXT3oMLJDHgYwGeYE+rNWAfGVyjLFyWWs7HHTyqZ6fPz6rxEhbcepdWq8ArpN8wN4miGr9z+1SUcYlpxGq0SbbjRqtVUEQbVoTHKljyuIOJoKvIZLdGwHAYAS2Ot9Op5PhUuPLztZb9nMWbVj9AIaz94EEFjavjd8whH+aGc4pO9GERO1HsaDlcYSOqLVOu8bM/tSd/CzAAi4afQO4WYnEAAAAASUVORK5CYII=" />' 
    			: title;
    	title = record.get('isMilk') ? title + '<img hspace="2px" vspace="0px" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAYAAADgdz34AAAABGdBTUEAANbY1E9YMgAAABl0RVh0U29mdHdhcmUAQWRvYmUgSW1hZ2VSZWFkeXHJZTwAAAEMSURBVHja7JUxCsIwFIZT7S4iKjiJ4CIouIvgrIdw8QA9hoObs+BVXBUUTyBUQSp10ULB1vgjCUhNJS8OLj74aCkv/1deUso45yxJljEVPXAALuiqelRZNtOvMqiI+5LuIopgCW4gBivtVZojaoMt4IINaOmMSEfQBN5LuOQIGt8IbFADa0W4ZAWqopckcMAOBB/CJVfR66iyrKclufOW5eFSZLQ6RZy/na5MSvMU7AnhrlhDOkUTjfFIxtQ9GBHCJUNVVtqIBoxefdXDNEHBQJCjCM4GggtF4BsIfIogNBCEFEFkIIgogthAEP8FP9+DO0UQGAgCyk9/BuqgA/IfXuQuvvoFmKsaHgIMAOMeMmi+VjczAAAAAElFTkSuQmCC" />'
    			: title;
    	title = record.get('isCrap') ? title + '<img hspace="2px" vspace="0px" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAYAAADgdz34AAAABGdBTUEAANbY1E9YMgAAABl0RVh0U29mdHdhcmUAQWRvYmUgSW1hZ2VSZWFkeXHJZTwAAAFVSURBVHjatNa7SgNBFMbxiVt4AUUsRDQIXpqAINZ2WipaSMDXEHwESxs7C5/ALgpqIZJOsElhIVqoJAG1iBoRFBHH/8EZCMsGZuLMwo8sZHO+zF7O2ZzWWsXcconjcZjCI959Arocj5NlfmAfBa8lJO5mofGMFdffuRbvx4UJED9YDBHQi01ctxS3HrCB7k4DxlL/up1zjPgGyN117FDcOvANKODbI+AL0y4BE9hB3aO4VcU28u0CFtDooHDaE+azAs4CFLdKtm7rk7yL1wDtp4G9rFZxiJMAAacop1vFEm4DnqIa1hLTTZf5LHk0Pp9tXQIu2ZmJNA5uJOCFncFIAU05LUcRB1pZLvCwaVg6sIo81fYu6sOWGSb/Ldw0LWMgyZjJoyhiFXMe1+YNFfMsyVituQz9cTN/J5UsVakh9JjvPtXfzVHHHa5wn/m2EPu15VeAAQDGAcYG2V5w8wAAAABJRU5ErkJggg==" />'
    			: title;
    	title = record.get('isPiss') ? title + '<img hspace="2px" vspace="0px" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAYAAADgdz34AAAABGdBTUEAANbY1E9YMgAAABl0RVh0U29mdHdhcmUAQWRvYmUgSW1hZ2VSZWFkeXHJZTwAAAE9SURBVHjatNYxS8NAFMDxJinYDq4GlKKDg0OHgouLCood69Rv0W/gkr0IOqiLg5sfwFUcFVyKiODmZEtVxKFWkEr8H33Bo2K9NHcPfuSSDI++9+5SL47jnMvwgp/1NNawjWsc20owz3UVVWxgDk9YwUPmDCSo4QrxiPVg+D4T/aaCCC0coCDPi1icNIHeg79iCvtYxhkupEefpiUyEeJRK98lZtKW6D+bGKTukW8+D2qi1KYp4xyHJmUy6cFozKIjyfIojRvnSRLooQqwJ+sIr7967GdLoH5FH0eo4w238jzVFI2Tl72TND/S3/sWjpsBTrX7JZslSuIeC9LsHbzYarLRFNiOUEbZWQJ1/J+g4CpBD1to5mQnWv9K4gsN3LhocgUtWT+7KFEXbVnfBQ4yvMuZ9IFdz/Xflm8BBgDAh0PNitomAQAAAABJRU5ErkJggg==" />'
    			: title;
    	title = record.get('isSleep') ? title + '<img hspace="2px" vspace="0px" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAYAAADgdz34AAAABGdBTUEAANbY1E9YMgAAABl0RVh0U29mdHdhcmUAQWRvYmUgSW1hZ2VSZWFkeXHJZTwAAAGrSURBVHja7NXPK8NhHMDxzXchv2IILZFwWFMsJ5cVDooDiYv4D5A4OSsnxR+gReKEi1Lk5CIpIj/jIDMWrRSy09f7y2fr6RsbX9vNU6/t2T5Pn+fHns++dl3XbalsabYUt5RPYNf+nsOBIlSgFm7pjyHosJg0H8OoQzWqkGsa4zcmsBk7sKAYuuIZh4jIZ+O9QZPtWWlh2cEbTnABHxYlPov9j56WHA4cyeof4IrGfnOLnHHGD8Ej/SncxiIJVlaAbizhOnquJmUIyerPkKPGv0vcjDmETT+m74ux00q8xxz/bgI/7jGJAbzhAOmmcR68SvI17bOuCtGIPvTGO5o86Y9LghHTGA2ryup3sY1H5bu7RJWchWOUSJUGlFgGzqVq1RbBjVzdPXMdZMrdjrYWVMq9NpI3oAsLkmBDJj6QerjEFUKxPHKundjElHIExu6WZasr2JH+C9qVY4p7E42XRWwhiBkl6MSTknQd/SiVyX9UhMZLDbJxjg4lmIYJDKLcapVHO14EUI9RuJP0FxIr/Sa4MA8vXpP9wGlDq9yW06Q+0f4f+onauwADAOgxZ7+c1KQNAAAAAElFTkSuQmCC" />'
    			: title;
    	var memo = record.get('memo').replace(/\n/g, '');
    	var maxLength = 20;
    	var shortenMemo = memo.length > maxLength ? memo.substr(0, maxLength) + '...' : memo;
    	record.set('title', title);
    	record.set('shortenMemo', shortenMemo);
    	var strHour = String(record.get('hour')) + '時';
    	record.set('strHour', strHour);
    	var isDirty = record.get('memo').length > 0 
    					|| record.get('isNurse')
    					|| record.get('isMilk')
    					|| record.get('isCrap')
    					|| record.get('isPiss')
    					|| record.get('isSleep');
    	record.set('strHourClass', isDirty ? 'hourLog-hour-isDirty' : 'hourLog-hour');
    	return record;
    }
    
    /**
     * Update toggle button status count
     * @param records
     * @returns
     */
    var updateStatusCount = function(records){
		var countNurse = 0;
		var countMilk = 0;
		var countCrap = 0;
		var countPiss = 0;
		var countSleep = 0;
		
		for(var i = 0 ; i < records.length ; i++){
			if(records[i].get('isNurse')) countNurse++;
			if(records[i].get('isMilk')) countMilk++;
			if(records[i].get('isCrap')) countCrap++;
			if(records[i].get('isPiss')) countPiss++;
			if(records[i].get('isSleep')) countSleep++;
		}
		
		Ext.getCmp('buttonNurse').setText(String(countNurse));
		Ext.getCmp('buttonMilk').setText(String(countMilk));
		Ext.getCmp('buttonCrap').setText(String(countCrap));
		Ext.getCmp('buttonPiss').setText(String(countPiss));
		Ext.getCmp('buttonSleep').setText(String(countSleep));
    };
    
	var storeHourLog =   new Ext.data.JsonStore({
		id: 'storeHourLog',
		returnJson: true, 
		writeAllFields: true,
		model: 'HourLog',
		proxy: httpProxy,
		listeners : {
			beforeload : function(store, options){
				// store last selected hourLog
				var listHourLog = Ext.getCmp('listHourLog');
				if(listHourLog == null){
					BN.selectedHour = new Date().getHours();
					return;
				}
				var selectedRecords = listHourLog.getSelectedRecords();
				if(selectedRecords.length > 0){
					BN.selectedHour = selectedRecords[0].get('hour');
					return;
				}else{
					BN.selectedHour = new Date().getHours();
					return;
				}
			},
			load : function(store, records, options){
				if(records.length == 0) return;
				
				// update date button
				var selectedDay = new Date(records[0].get('date'));
				updateButtonDatePickerText(selectedDay);
				
				// create strHour and title and shorten memo
				for (var i = 0, record; record = records[i]; i++) {
					BN.createDisplayString(record);
				}
				
				// reselect
				var listHourLog = Ext.getCmp('listHourLog');
				var selectedRecord = records[BN.selectedHour];
				listHourLog.getSelectionModel().select(selectedRecord, false, false);
				
				// set count text
				updateStatusCount(records);
				
				// update toggle buttons
				var buttonGroup = Ext.getCmp('buttonsGroupHourLog');
				buttonGroup.setPressed(Ext.getCmp('buttonNurse'), selectedRecord.get('isNurse'), true);
				buttonGroup.setPressed(Ext.getCmp('buttonMilk'), selectedRecord.get('isMilk'), true);
				buttonGroup.setPressed(Ext.getCmp('buttonCrap'), selectedRecord.get('isCrap'), true);
				buttonGroup.setPressed(Ext.getCmp('buttonPiss'), selectedRecord.get('isPiss'), true);
				buttonGroup.setPressed(Ext.getCmp('buttonSleep'), selectedRecord.get('isSleep'), true);
				
				if(BN.isFirstLoad){
					// create ads
					Ext.getCmp('googleAdsPanel')
					// show selection
					var heightOfRow = 50; // If you have a time, please calc.
					var hour = selectedRecord.get('hour');
					var limitHeight = (heightOfRow * 23) - 200; // please fix constant, it is optimize for iphone
					var movePx = heightOfRow * hour - 100 ;
					var movePx = movePx > limitHeight ? limitHeight : movePx;
					Ext.getCmp('listHourLog').scroller.moveTo(0, -1 * movePx);
					BN.isFirstLoad = false;
				}
			}
		}
	});
	storeHourLog.load({params: {date: Ext.util.Format.date(BN.selectedDate, 'Ymd')}});
	
	/**
     * Update text of buttonDatePicker for selectedDay
     * @param {Date} selectedDay
     */
	var updateButtonDatePickerText = function(selectedDay){
		var milsecDiff =  selectedDay.getTime() - BN.birthday.getTime();
		var strDateDiff = String(Math.ceil(milsecDiff / BN.oneDayMsec));
		
		var textButtonDatePicker = Ext.util.Format.date(selectedDay, 'Y/m/d') + ' 生後' + strDateDiff + '日目';
		Ext.getCmp('buttonDatePicker').setText(textButtonDatePicker);
	};
	
	var listHourLog = {
			id : 'listHourLog',
			width: Ext.is.Phone ? undefined : 300,
					height: 500,
					xtype: 'list',
					singleSelect : true,
					allowDeselect : false,
					onItemDisclosure: function(record, btn, index) {
						Ext.Msg.prompt(null,
								record.get('hour') + '時のメモ',
								function(buttonText, value) {
							if(buttonText == 'cancel') return;
							
							// For response, update local before POST.
							// update local store
							record.set('memo', value);
							BN.createDisplayString(record);
							record.commit();
							// update local ui
							Ext.getCmp('listHourLog').refreshNode(record.get('hour'));
							
							Ext.Ajax.request({
								url: 'hourlog/update',
								params : {
									keyDayLog : record.get('keyDayLog'),
									hour : record.get('hour'),
									memo : value
								},
								method : 'POST',
								success: function(response, opts) {
									console.log('hourlog/update is success. updated to follow object.')
									console.dir(response);
								},
								failure: function(response, opts) {
									var message = 'server-side failure with status code ' + response.status;
									console.log(message);
									alert(message);
								}
							});
						},
						null,
						true,
						record.get('memo'),
						{	
							focus : true,
							autocapitalize : false,
							maxlength : 500
						});
					},
					listeners : {
						selectionchange : function(selectionModel, records){
							// update toggle buttons
							if(records.length < 1){
								Ext.getCmp('buttonNurse').enable(false);
								Ext.getCmp('buttonMilk').enable(false);
								Ext.getCmp('buttonCrap').enable(false);
								Ext.getCmp('buttonPiss').enable(false);
								Ext.getCmp('buttonSleep').enable(false);
								return;
							}
							Ext.getCmp('buttonNurse').enable(true);
							Ext.getCmp('buttonMilk').enable(true);
							Ext.getCmp('buttonCrap').enable(true);
							Ext.getCmp('buttonPiss').enable(true);
							Ext.getCmp('buttonSleep').enable(true);
							
							var selectedRecord = records[0];
							var buttonGroup = Ext.getCmp('buttonsGroupHourLog');
							buttonGroup.setPressed(Ext.getCmp('buttonNurse'), selectedRecord.get('isNurse'), true);
							buttonGroup.setPressed(Ext.getCmp('buttonMilk'), selectedRecord.get('isMilk'), true);
							buttonGroup.setPressed(Ext.getCmp('buttonCrap'), selectedRecord.get('isCrap'), true);
							buttonGroup.setPressed(Ext.getCmp('buttonPiss'), selectedRecord.get('isPiss'), true);
							buttonGroup.setPressed(Ext.getCmp('buttonSleep'), selectedRecord.get('isSleep'), true);
						}
					},
					store: storeHourLog,
					itemTpl:  '<div class="hourLog"><span class="{strHourClass}">{strHour}</span><span class="hourLog-log">{title}</span><span class="hourLog-memo">{shortenMemo}</span></div>'
	};
	var itemsLogEditor =  [
	                       {
	                    	   id: 'leftBlankPanel',
	                    	   xtype: 'panel'
	                       },
	                       listHourLog,
	                       {
	                    	   id: 'rightBlankPanel',
	                    	   xtype: 'panel'
	                       }
	                       ];
	
	var logEditorCarousel = new Ext.Carousel({
		id: 'logEditorCarousel',
		direction: 'horizontal',
		indicator: false,
		activeItem : 'listHourLog',
		layout: Ext.is.Phone ? 'fit' : {
			type: 'vbox',
			align: 'center',
			pack: 'center'
		},
		fullscreen: true,
		dockedItems: dockedItemsLogEditor,
		items: itemsLogEditor,
		listeners:{
			cardswitch: function(thisContainar, newCard, oldCard, index, animated){
				if(oldCard.id === 'leftBlankPanel'
					|| oldCard.id === 'rightBlankPanel') return;
				
				thisContainar.setActiveItem(oldCard, false);
				if(newCard.id === 'leftBlankPanel'){
					loadOtherDate(-1);
				} else if(newCard.id === 'rightBlankPanel'){
					loadOtherDate(1);
				}
			}
		}
	});
	
	/**
     * Load other day data to the list.
     * @param {Number} dateDiff
     */
	var loadOtherDate = function(dateDiff){
		BN.selectedDate = new Date(BN.selectedDate.getTime() + (dateDiff * BN.oneDayMsec) );
		
		// update date button before load, for usability
		updateButtonDatePickerText(BN.selectedDate);
		
		var store = Ext.getCmp('listHourLog').store;
		store.load({params: {date: Ext.util.Format.date(BN.selectedDate, 'Ymd')}});
	}
	return logEditorCarousel;
};