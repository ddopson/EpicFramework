module.exports = {
	name: "screens/MainMenu",
	type: "com.epic.framework.common.Ui2.EpicScreenObject",
	widgets: [
		{
			type: "com.epic.framework.common.Ui2.VirtualWidgets.EpicBackgroundWidget",
			bitmap: "images/background"
		},
		{
			type: "com.epic.framework.common.Ui2.NativeWidgets.EpicNativeButtonWidget",
			x: 40,
			y: 40,
			width: 400,
			height: 50,
			text: "Play",
			onClick: null
		}
	]
}
