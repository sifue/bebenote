<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
     <title>Babenote</title>
     
     <!-- Server side data inputing -->
     <script type="text/javascript">
     var BN = {};
     BN.logoutURL = '${logoutURL}';
     BN.keyAccount = '${keyAccount}';
     BN.loginCount = ${loginCount};
     BN.hasDefaultBaby = ${hasDefaultBaby};
     BN.birthday = new Date(${birthday});
     </script> 

	 <!-- Sencha Touch CSS -->
	 <link rel="stylesheet" href="css/babenote.css" type="text/css">

	 <!-- Custom CSS -->
	 <link rel="stylesheet" href="css/index.css" type="text/css">

	 <!-- Sencha Touch JS -->
	 <!-- <script type="text/javascript" src="js/sencha-touch-debug.js"></script> -->
	 <script type="text/javascript" src="js/sencha-touch.js"></script>

	 <!-- Application JS -->
	 <script type="text/javascript" src="js/log-editor.js"></script>
	 <script type="text/javascript" src="js/settings.js"></script>
	 <script type="text/javascript" src="js/index.js"></script>

<!-- Google Adsense -->
<script type="text/javascript"><!--
  // XHTML should not attempt to parse these strings, declare them CDATA.
  /* <![CDATA[ */
  window.googleAfmcRequest = {
    client: 'ca-mb-pub-7523191944058659',
    format: '320x50_mb',
    output: 'html',
    slotname: '9567749390',
  };
  /* ]]> */
//--></script>
<script type="text/javascript"    src="http://pagead2.googlesyndication.com/pagead/show_afmc_ads.js"></script>

<!-- Google Analytics -->
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-21235263-1']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>

</head>
<body>
ただいま読込中です...
</body>
</html>
