Ext.setup({
	tabletStartupScreen: 'images/tablet_startup.png',
	phoneStartupScreen: 'images/phone_startup.png',
	icon: 'images/icon.png',
	glossOnIcon: false,
    onReady: function() {
    	// Create common objects //////////////////////////////////////
        // common mask
        BN.mask = new Ext.LoadMask(Ext.getBody(), {msg:"Loading..."});
        
    	// first load flag for log
    	BN.isFirstLoad = true;
    	
		// date of displayed log
    	BN.selectedDate = new Date();
    	
        // msec of one day
        BN.oneDayMsec = (1000 * 60 * 60 * 24);
	      
        // mainPanel //////////////////////////////////////////
		var mainPanel = new Ext.Panel({
			id: 'mainPanel',
			fullscreen: true
		});
		
		if(BN.hasDefaultBaby){
			mainPanel.insert(0, createLogEditorCarousel());
		}else{
			mainPanel.insert(0, createSettingsPanel());
		}
		// Thank you message /////////////////////////////////////////////////
		if(BN.loginCount % 100 == 0){
			Ext.Msg.alert(null,
					'おめでとうございます！通算' + String(BN.loginCount) + '回目のアクセスです。これからも育児がんばってください！',
					Ext.emptyFn);
			
		}else if(BN.loginCount == 1){
			Ext.Msg.alert(null,
					'ベイブノートへようこそ！まずは赤ちゃんの名前と誕生日を入力してEnterを押してください。その後は記録をつけるだけです。',
					Ext.emptyFn);
		}
    }
});
