module.exports = {
	type: "com.epic.framework.common.Ui2.EpicScreenObject",
	widgets: [
		{
			type: "com.epic.framework.common.Ui2.VirtualWidgets.EpicBackgroundWidget",
			bitmap: "images/background"
		},
		{
			type: "com.epic.framework.common.Ui2.NativeWidgets.EpicNativeTextFieldWidget",
			x: 40,
			y: 40,
			width: 400,
			height: 50,
		},
        {
            type: "com.epic.framework.common.Ui2.NativeWidgets.EpicNativeLabelWidget",
            x: 40,
            y: 120,
            width: 400,
            height: 40,
        }
	]
}
